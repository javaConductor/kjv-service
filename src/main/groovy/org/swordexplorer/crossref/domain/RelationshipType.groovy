package org.swordexplorer.crossref.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by lcollins on 8/16/2014.
 */
@Document
class RelationshipType {
    @Id
    String id
    String name
    String description
}
