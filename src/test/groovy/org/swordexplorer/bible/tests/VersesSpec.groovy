package org.swordexplorer.bible.tests

import org.swordexplorer.kjv.KjvService
import spock.lang.Specification


/**
 * Created by lcollins on 5/11/2014.
 */
class VersesSpec extends Specification {

    def bookService = new KjvService()

    void setup() {
    }

    def "verseSpecToVerse 'Gen 6:1-10' should return a VerseRange with 10 verses"() {
        when:
        def verseRange = bookService.verseSpecToVerses("Gen 6:1-10")

        then:
        verseRange.verses.size() == 10
    }

    def "verseSpecToVerse 'Rev 16:1-5, 9, 11-14' should return a VerseRange with 10 verses"() {
        when:
        def verseRange = bookService.verseSpecToVerses("Rev 16:1-5, 9, 11-15")

        then:
        verseRange.verses.size() == 11
        verseRange.verses[5].verse == 9
    }

    def "verseSpecToVerse '1 Chronicles 16:1-5, 9, 11-14' should return a VerseRange with 10 verses"() {
        when:
        def verseRange = bookService.verseSpecToVerses("1 Chronicles  16:1-5, 9, 11-15")

        then:
        verseRange.verses.size() == 11
        verseRange.verses[5].verse == 9
    }

}