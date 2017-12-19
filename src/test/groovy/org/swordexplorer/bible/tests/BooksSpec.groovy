package org.swordexplorer.bible.tests

import org.swordexplorer.kjv.KjvService
import spock.lang.Specification


/**
 * Created by lcollins on 5/11/2014.
 */
class BooksSpec extends Specification {

    def bookService = new KjvService()

    void setup() {
    }

    def "getBooks should return 66 books"() {
        when:
        def bks = bookService.getBooks()

        then:
        bks.size() == 66
    }

}