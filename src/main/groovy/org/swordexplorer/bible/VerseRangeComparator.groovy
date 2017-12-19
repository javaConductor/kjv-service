package org.swordexplorer.bible

/**
 * Created by lcollins on 11/28/2014.
 */
class VerseRangeComparator implements Comparator<VerseRange> {
    @Override
    int compare(VerseRange vr1, VerseRange vr2) {

        def bk1 = vr1.verses.head.book
        def bk2 = vr2.verses.head.book

        if (bk1 != bk2) {
            return bk2 - bk1;
        }

        def ch1 = vr1.verses.head.chapter
        def ch2 = vr2.verses.head.chapter

        if (ch1 != ch2) {
            return ch2 - ch1;
        }

        def startVerse1 = vr1.verses.head.verse
        def startVerse2 = vr2.verses.head.verse

        if (startVerse1 != startVerse2) {
            return startVerse2 - startVerse1;
        }

        return vr2.verses.size() - vr1.verses.size()
    }
}
