package org.swordexplorer.crossref.db

import org.springframework.data.repository.PagingAndSortingRepository
import org.swordexplorer.crossref.domain.VerseRelationship

/**
 * Created by lee on 6/11/17.
 */
interface XRefRepository extends PagingAndSortingRepository<VerseRelationship, String> {

}