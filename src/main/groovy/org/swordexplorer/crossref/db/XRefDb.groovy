package org.swordexplorer.crossref.db

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import groovyjarjarantlr.collections.List
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.swordexplorer.bible.BibleService
import org.swordexplorer.crossref.domain.VerseRelationship

/**
 * Created by lcollins on 10/17/2014.
 */
@Service("xrefDb")
@Repository
class XRefDb {//implements PagingAndSortingRepository<VerseRelationship, String>{
    BibleService bibleService
    MongoTemplate mongo

    @Autowired
    XRefDb(BibleService bibleService, MongoTemplate mongo) {
        super()
        this.mongo = mongo
        this.bibleService = bibleService
    }

    VerseRelationship createRelationship(VerseRelationship verseRelationship) {
        this.save(verseRelationship)
    }/// createRelationship

    boolean removeRelationship(relationshipId) {
        try {
            this.delete(relationshipId)
            true
        } catch (Exception e) {
            false
        }
    }/// removeRelationship

    VerseRelationship updateRelationship(VerseRelationship verseRelationship) {
        verseRelationship.comments = verseRelationship.comments ?: ""
        save(verseRelationship)
    }/// updateRelationship

    VerseRelationship getRelationship(ObjectId id) {
        findOne(id)
    }/// updateRelationship

    List allRelationships() {
        findAll().toList()
    }/// allRelationships

    DBObject dbObject(VerseRelationship verseRelationship) {
        def tmp = [
                "relationshipType": verseRelationship.relationshipType,
                "comments"        : verseRelationship.comments,
                "verseRange"      : verseRelationship.verseRange,
                "relatedRange"    : verseRelationship.relatedRange
        ]
        if (verseRelationship.id)
            tmp.id = new ObjectId(verseRelationship.id);
        tmp as BasicDBObject;
    }

}
