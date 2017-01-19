package com.kotlang.minion.repositories

import com.kotlang.minion.models.UserToken
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * Created by sainageswar on 08/01/17.
 */
interface UserTokenRepository : ElasticsearchRepository<UserToken, String> {
}