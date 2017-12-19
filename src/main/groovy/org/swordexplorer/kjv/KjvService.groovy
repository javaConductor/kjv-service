package org.swordexplorer.kjv

import org.springframework.stereotype.Service
import org.swordexplorer.bible.AbstractService

/**
 * Created by lcollins on 5/11/2014.
 *
 * Reads a JSON file containing the KJV bible.
 *
 */
@Service
class KjvService extends AbstractService {
    def bibleData

    KjvService() {
        super("/kjv.json")
    }
}
