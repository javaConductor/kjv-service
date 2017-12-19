package org.swordexplorer.bible
/**
 *
 */
class Pair {
    Integer start, end;
}

abstract class AbstractService implements BibleService {

    def bibleData, bookList, chapters, verses

    AbstractService(jsonBibleTextFilename) {
        init(jsonBibleTextFilename)
    }

    def init(jsonBibleTextFilename) {
        bibleData = new BibleData(jsonBibleTextFilename).data()
        bookList = bibleData.bible.books
        chapters = bibleData.bible.chapters
        verses = bibleData.bible.verses
    }

    @Override
    def chapterVerseCount(bkId, chapter) {
        def chpt = chapters.find { chptInfo ->
            chptInfo.book == bkId &&
                    chptInfo.chapter == chapter
        }
        chpt ? chpt.verses : 0
    }

    @Override
    boolean isVerseSpec(String verseSpec) {
        def regexBkChpt = ~/(\d*\s*\w+)\s+(\d{1,3})/
        def regexVerse = ~/(([\d]{1,3})\-*((\d{1,3}+)[,]*\s*)*)/
        if (!verseSpec.contains(':'))
            return false;

        def both = verseSpec.split(':') as List
        if (!both[0] =~ regexBkChpt)
            return false;
        if (both.size() == 1)
            return name;

        both[1] = both[1].replace(" ", "");
        List verseSets = both[1].split(',') as List;
        // find all the sets that do NOT match the pattern
        return !verseSets.findAll { set ->
            return !(set.trim() ==~ regexVerse)
        }.size()

    }

    String optimizeVerseSpecVerses(versesStr, maxVerse) {

        def parts = versesStr.split(",").toList()
        def vlist = parts.collect { v ->
            /// is there a '-'
            def verseNums = v.split('-').toList()
            if (verseNums.size() == 1) {
                [verseNums.head().asType(Integer)]
            } else {
                [verseNums.head().asType(Integer)..verseNums[1].asType(Integer)]
            }
        }.flatten().findAll { it <= maxVerse }


        vlist.inject([]) { List acc, n ->
            if (acc.isEmpty()) {
                return [new Pair(start: n, end: n)]
            } else {
                Pair last = acc.last()
                def start = last.start
                def end = last.end
                if (n != end + 1) {
                    return acc + new Pair(start: n, end: n)
                } else {
                    def nuPair = new Pair(start: start, end: n)
                    return acc.reverse().tail().reverse() + nuPair
                }
            }
        }.collect { pair ->
            if (pair.start == pair.end)
                pair.start.toString()
            else
                "${pair.start}-${pair.end}"
        }.join(", ")
    }

    @Override
    def parseVerseSpec(String verseSpec) {

        if (!isVerseSpec(verseSpec))
            return null

        def parts = verseSpec.split(':')
        def bkChpt = parts[0].split(' ') as List;
        def chpt = (bkChpt.pop() as int);
        def bkName = bkChpt.join(' ').trim();
        def verses
        // if bad book name
        def book = bookNameToBook(bkName)
        if (!book)
            return null;
        if (parts.length == 1) {
            verses = "1-${chapterVerseCount(book.id, chpt)}"
        } else {
            verses = parts[1]
        }

        // optimize verses
        return [
                book   : book,
                chapter: chpt,
                verses : optimizeVerseSpecVerses(verses, chapterVerseCount(book.id, chpt))
        ]
    }

    @Override
    VerseRange verseSpecToVerses(String verseSpec) {
        /// break the string into parts starting with Bookname
        ///  Gen 5:2-6, 8-12
        def parsedVerseSpec = parseVerseSpec(verseSpec);
        if (!parsedVerseSpec) {
            return null
        }
        def book = parsedVerseSpec.book
        def bkName = book.title
        def chapter = parsedVerseSpec.chapter
        def sets = parsedVerseSpec.verses.split(',')
        def verseList = []
        sets.each { vlist ->
            def start, end
            if (vlist.contains('-')) {
                def l = vlist.split('-')
                start = l[0] as int
                end = l[1] as int

            } else {
                end = start = (vlist as int)
            }
            (start..end).each { v ->
                verseList << v
            }
        }

        List<Verse> verses = []
        verseList.each { v ->
            String vid = String.format("%02d%03d%03d", book.id, chapter, v)
            def verse = getVerse(vid)
            if (verse)
                verses << verse
        }
        new VerseRange("$bkName $chapter:${parsedVerseSpec.verses}", verses)
    }

    @Override
    def bookNameToBook(bkName) {
        def ret
        /// compare the upper
        bkName = bkName.toUpperCase()
        // find the exact match
        ret = bookList.find { bk ->
            bk.title.toUpperCase() == bkName
        }

        if (!ret) {
            def list = bookList.findAll { bk ->
                bk.title.toUpperCase().startsWith(bkName)
            }
            if (list.size() == 1)
                ret = list[0]
        }
        ret
    }

    private Verse addVerseSpec(Verse v) {
        v?.verseSpec = verseSpecFromVerseId(v?.verseId)
        v
    }

    @Override
    Verse getVerse(String verseId) {
        verses[verseId]
    }

    @Override
    List<Verse> getVerses(List<String> verseIds) {
        verseIds.collect { vid ->
            def v = getVerse(vid)
            v?.verseSpec = verseSpecFromVerseId(vid)
            v
        }
    }

    @Override
    List<Verse> getVerses(String verseSpec) {
        verseSpecToVerses(verseSpec)?.verses.collect { v ->
            v?.verseSpec = verseSpecFromVerseId(v.verseId)
            v
        }
    }

    @Override
    VerseRange getChapter(int book, int chapter) {
        def bk = getBook(book)
        def lastVerse = chapterVerseCount(book, chapter)
        def verseSpec = "${bk.title} $chapter:1-$lastVerse"
        verseSpecToVerses(verseSpec)
    }

    @Override
    List<Book> getBooks() {
        bookList
    }

    @Override
    Book getBook(int bookId) {
        bookList[bookId - 1]
    }

    private def verseToSearchResult = { verse ->
        new SearchResult(
                verseId: verse.verseId,
                verseSpec: verseSpecFromVerseId(verse.verseId),
                verseText: verse.verseText
        )
    }

    @Override
    String verseSpecFromVerseId(vid) {
        def bkId = vid.substring(0, 2) as int
        if (bkId > 66 || bkId < 1)
            return null;
        def chapter = vid.toString().substring(2, 5) as int
        def verse = vid.substring(5) as int
        def bkName = getBook(bkId)?.title
        "$bkName $chapter:$verse"
    }

    /**
     *
     * @param phrase
     * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
     */
    @Override
    List getVersesWithPhrase(String phrase) {
        verses.values().findAll { v ->
            v.verseText.toUpperCase().contains(phrase.toUpperCase())
        }.collect(verseToSearchResult)
    }//phrase

/**
 *
 * @param words
 * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
 */
    @Override
    List getVersesWithAllWords(List words) {
        def wlist = words
        verses.values().findAll { v ->
            def verseText = v.verseText.toUpperCase()
            wlist.every { w ->
                def regexStart = ~/^.*[^\w]+(${w.toUpperCase()})[ ,.:;\$]+.*/
                regexStart.matcher(verseText).matches()
//        v.verseText.toUpperCase().contains(w.toUpperCase())
            }
        }.collect(verseToSearchResult)
    }//all

    /**
     *
     * @param words
     * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
     */
    @Override
    List getVersesWithAnyWords(List words) {
        def wlist = words
        verses.values().findAll { v ->
            def verseText = v.verseText.toUpperCase()
            wlist.any { String w ->
                def regexStart = ~/^.*[^\w]+(${w.toUpperCase()})[ ,.:;\$]+.*/
                regexStart.matcher(verseText).matches()
            }
        }.collect(verseToSearchResult)
    }//any

    /**
     *
     * @param searchText
     * @param searchType
     * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
     */
    @Override
    List<Verse> searchVerses(String searchText, String searchType) {
        def wordList = searchText.split(' ').toList().findAll { w -> w.trim().length() > 0 }
        switch (searchType.toUpperCase()) {
            case "ANY": getVersesWithAnyWords(wordList); break;
            case "ALL": getVersesWithAllWords(wordList); break;
            case "PHRASE": getVersesWithPhrase(searchText); break;
            default: throw new IllegalArgumentException("searchType must be one of: ALL, NAY, PHRASE")

        }
    }
}
