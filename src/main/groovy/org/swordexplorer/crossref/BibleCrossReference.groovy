package org.swordexplorer.crossref

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.swordexplorer.bible.BibleService
import org.swordexplorer.crossref.db.XRefDb
import org.swordexplorer.crossref.domain.RelationshipType
import org.swordexplorer.crossref.domain.VerseRelationship

/**
 * This class represents a Bible Verse Cross-Reference
 *
 * Created by lcollins on 7/31/2014.
 */
@Service("bibleCrossReference")
class BibleCrossReference {

    BibleService service
    XRefDb xrefDb

    @Autowired
    BibleCrossReference(BibleService service, XRefDb xrefDb) {
        this.service = service
        this.xrefDb = xrefDb
    }

    def _relationshipTypes = [:];
    List<VerseRelationship> xref = []
    def sample = [
            [subjectVerses   : [verseSpec: 'Gen 1:1', verseIds: ['01001001']],
             relatedVerses   : [verseSpec: 'Gen 9:9', verseIds: ['02002002']],
             relationshipType: 'Same As', notes: 'This verse refers to the same time as Gen 1:1'
            ], [subjectVerses   : [verseSpec: 'Gen 1:1', verseIds: ['01001001']],
                relatedVerses   : [verseSpec: 'Gen 9:9', verseIds: ['02002002']],
                relationshipType: 'Same As', notes: 'This verse refers to the same time as Gen 1:1'
            ]];


    def removeRelationship(id) {
        def obj = xrefDb.removeRelationship(id)
    }

    VerseRelationship updateVerseRelationship(id, verseSpec, relationshipType, relatedVerseSpec, comments) {

        if (!service.isVerseSpec(verseSpec)) {
            throw new IllegalArgumentException("Bad verseSpec: $verseSpec")
        }
        if (!service.isVerseSpec(relatedVerseSpec)) {
            throw new IllegalArgumentException("Bad related verseSpec: $relatedVerseSpec")
        }
        def verses = service.verseSpecToVerses(verseSpec)
        def relatedVerses = service.verseSpecToVerses(relatedVerseSpec)
        def obj = new VerseRelationship(id, verses, relationshipType, relatedVerses, comments)
        obj = updateVerseRelationship(obj)
        obj
    }

    VerseRelationship updateVerseRelationship(VerseRelationship verseRelationship) {
        def obj = xrefDb.updateRelationship(verseRelationship)
        obj
    }

    VerseRelationship getVerseRelationship(ObjectId id) {
        def obj = xrefDb.getRelationship(id)
        obj
    }

    VerseRelationship addVerseRelationship(verseSpec, relationshipType, relatedVerseSpec, comments = "") {
        def verses = service.verseSpecToVerses(verseSpec)
        def relatedVerses = service.verseSpecToVerses(relatedVerseSpec)
        def obj = new VerseRelationship(verses, relationshipType, relatedVerses, comments)
        obj = addVerseRelationship(obj)
        obj
    }

    VerseRelationship addVerseRelationship(VerseRelationship verseRelationship) {
        def obj = xrefDb.createRelationship(verseRelationship)
        xref.add(obj)
        obj
    }

    List<VerseRelationship> findVerseRelationshipsBySubjectVerse(List subjectVerses) {
        xref.findAll { vr ->
            matchVerses(subjectVerses, vr.subjectVerses)
        }
    }

    List<VerseRelationship> findVerseRelationshipsByRelatedVerse(List relatedVerses) {
        xref.findAll { vr ->
            matchVerses(relatedVerses, vr.relatedVerses)
        }
    }

    def matchVerses(List verseIdList1, List verseIdList2) {
        verseIdList1.intersect(verseIdList2).size() == verseIdList1.size() ||
                verseIdList2.intersect(verseIdList1).size() == verseIdList2.size()
    }

    List<VerseRelationship> verseRelationships() {
        xrefDb.allRelationships().sort { vr1, vr2 ->

            def verseId1 = vr1?.verseRange.verses[0].verseId
            def verseId2 = vr2?.verseRange.verses[0].verseId

            if (verseId1 != verseId2) {
                return verseId1.compareTo(verseId2)
            }
            return vr2.verseRange.verses.size() - vr1.verseRange.verses.size()
        }
    }

    List<RelationshipType> relationshipTypes() {
        _relationshipTypes
    }

    RelationshipType addRelationshipType(relationshipType, description) {
        def obj = new RelationshipType(name: relationshipType, description: description)
        _relationshipTypes[relationshipType] = obj
        obj
    }

}
