package org.swordexplorer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.swordexplorer.bible.BibleService
import org.swordexplorer.bible.SearchResult
import org.swordexplorer.bible.Verse
import org.swordexplorer.bible.VerseRange

/**
 * Created by lee on 6/11/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/verses")
class VerseController {
    BibleService bibleService

    @Autowired
    VerseController(BibleService bibleService) {
        this.bibleService = bibleService
    }

    @RequestMapping("/{id}")
    Response<Verse> byId(@PathVariable("id") String id) {
        new Response<Verse>(success: true, data: bibleService.getVerse(id))
    }

    @RequestMapping(path = "/fromSpec/{verseSpec}", method = RequestMethod.GET)
    Response<VerseRange> fromVerseSpec(@PathVariable("verseSpec") String verseSpec) {
        println("/fromSpec/${verseSpec}")
        new Response<VerseRange>(success: true, data: bibleService.verseSpecToVerses(verseSpec))
    }

    @CrossOrigin
    @RequestMapping(path = "/withText/{searchText}/{searchType}", method = RequestMethod.GET)
    Response<List<SearchResult>> withText(@PathVariable("searchText") String searchText,
                                          @PathVariable("searchType") String searchType) {
        println("/withText/${searchText}/${searchType}")
        new Response<List<SearchResult>>(success: true, data: bibleService.searchVerses(searchText, searchType))

    }
}
