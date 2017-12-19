package org.swordexplorer.bible

import groovy.transform.ToString
import org.springframework.data.mongodb.core.mapping.Document

/**
 * A VerseSpec and its verses
 */
@ToString
@Document
class VerseRange implements Serializable {
    String verseSpec
    List<Verse> verses

    VerseRange(String verseSpec, List<Verse> verses) {
        this.verseSpec = verseSpec
        this.verses = verses
    }

    static VerseSet asVerseSet(VerseRange vr) {
        new VerseSet(vr.verseSpec, vr.verses.collect { it.verseId })
    }

}
