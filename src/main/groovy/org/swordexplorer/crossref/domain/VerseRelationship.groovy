package org.swordexplorer.crossref.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.swordexplorer.bible.VerseRange

/**
 * Created by lcollins on 8/15/2014.
 */
@Document
class VerseRelationship implements Serializable {
    @Id
    String id
    VerseRange verseRange
    VerseRange relatedRange
    String relationshipType
    String comments = ""

    VerseRelationship() {
    }

    VerseRelationship(Map initialValues) {
        this(initialValues.id,
                initialValues.verseRange,
                initialValues.relationshipType,
                initialValues.relatedRange,
                initialValues.comments)
    }

    VerseRelationship(VerseRange verseRange, String relationshipType, VerseRange relatedRange, String comments) {
        this("", verseRange, relationshipType, relatedRange, comments)
    }

    VerseRelationship(String id, VerseRange verseRange, String relationshipType, VerseRange relatedRange, String comments) {
        this.verseRange = verseRange
        this.relationshipType = relationshipType
        this.relatedRange = relatedRange
        this.comments = comments
        if (id)
            this.id = id
    }

    VerseRelationship(Object id, VerseRange verseRange, String relationshipType, VerseRange relatedRange, String comments) {
        this(id?.toString(), verseRange, relationshipType, relatedRange, comments)
    }
}
