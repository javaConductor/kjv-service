package org.swordexplorer.bible

/**
 *
 */
interface BibleService {
    def chapterVerseCount(bkId, chapter)

    def parseVerseSpec(String verseSpec)

    boolean isVerseSpec(String verseSpec)

    org.swordexplorer.bible.VerseRange verseSpecToVerses(String verseSpec)

    def bookNameToBook(bkName)

    Verse getVerse(String verseId)

    List<Verse> getVerses(List<String> verseIds)

    List<Verse> getVerses(String verseSpec)

    VerseRange getChapter(int book, int chapter)

    List getVersesWithPhrase(String phrase)

    List getVersesWithAnyWords(List words)

    List getVersesWithAllWords(List words)

    List<Book> getBooks()

    Book getBook(int bookId)

    String verseSpecFromVerseId(vid)

    List<Verse> searchVerses(String searchText, String searchType)
}
