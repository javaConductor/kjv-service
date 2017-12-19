package org.swordexplorer.bible.tests.crossref

import org.springframework.test.context.ContextConfiguration
import org.swordexplorer.bible.BibleService
import org.swordexplorer.crossref.BibleCrossReference
import org.swordexplorer.kjv.KjvService
import spock.lang.Specification

/**
 * Created by lcollins on 8/2/2014.
 */
@ContextConfiguration(locations = "classpath:spring-mongo.xml")
class xrefSpec extends Specification {

    BibleCrossReference bibleCrossReference
    def testXRef = [
            [
                    subjectVerses   : ['01001001', '01001002'],
                    relatedVerses   : ['43001001', '43001002', '43001003'],
                    relationshipType: 'Same As',
                    comments        : 'Theses are similar!!!!'
            ], [
                    subjectVerses   : ['01001001', '01001002'],
                    relatedVerses   : ['01002001', '01002002', '01002003'],
                    relationshipType: 'Same As'
            ]
    ];

    def testXRef2 = [
            [
                    subjectVerses   : ['02001001', '02001002'],
                    relatedVerses   : ['43001001', '43001002', '43001003'],
                    relationshipType: 'Same As',
                    comments        : 'Theses are similar!!!!'
            ],
            [
                    subjectVerses   : ['01001001', '01001002'],
                    relatedVerses   : ['01002001', '01002002', '01002003'],
                    relationshipType: 'Same As'
            ]
    ];

    def testXRef3 = [
            [
                    subjectVerses   : ['01001001', '01001002'],
                    relatedVerses   : ['43001001', '43001002', '43001003'],
                    relationshipType: 'Same As',
                    comments        : 'Theses are similar!!!!'
            ], [
                    subjectVerses   : ['01001001', '01001002'],
                    relatedVerses   : ['01002001', '01002002', '01002003'],
                    relationshipType: 'Same As'
            ]
    ];

    BibleService bibleService = new KjvService()
    def xrefService = new BibleCrossReference(bibleService)

    @spock.lang.Ignore
    def "should add a verseRelationship to the crossReference"() {
        when:
        xrefService.addRelationshipType("testRel", "This is only a test.")

        then:
        xrefService.relationshipTypes().size() == 1
    }

    @spock.lang.Ignore
    def "should add a relationship to the crossReference"() {
        when:
        xrefService.addVerseRelationship(['01001001'], "testRel", ['43001001', '43001002', '43001003'])

        then:
        xrefService.verseRelationships().size() == 1

    }

    @spock.lang.Ignore
    def "should find a verseRelationship for one "() {
        when:
        xrefService = new BibleCrossReference(bibleService, testXRef)
        def xrefs = xrefService.findVerseRelationshipsBySubjectVerse(['01001001'])

        then:
        xrefs.size() == 2

    }

    @spock.lang.Ignore
    def "should find a verseRelationship for two "() {
        when:
        xrefService = new BibleCrossReference(bibleService, testXRef)
        def xrefs = xrefService.findVerseRelationshipsBySubjectVerse(['01001001', '01001002'])
        then:
        xrefs.size() == 2

    }

}
