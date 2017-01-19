package com.kotlang.minion.repositories

import com.kotlang.minion.models.Cache
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * Created by sainageswar on 19/01/17.
 */

interface CacheRepository : ElasticsearchRepository<Cache, String> {

}