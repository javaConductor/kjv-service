package org.swordexplorer.bible

import groovy.transform.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by lcollins on 5/10/2014.
 */
@ToString
@Document
class Verse implements Serializable {
    @Id
    String verseId //BBCCCVVV
    int verse
    int book
    int chapter
    String verseSpec
    String verseText

    String toString() {
        "$verseSpec $verseText"
    }
}
