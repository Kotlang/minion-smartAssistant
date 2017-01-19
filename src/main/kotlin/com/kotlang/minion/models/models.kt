package com.kotlang.minion.models

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

/**
 * Created by sainageswar on 08/01/17.
 */
@Document(indexName = "minion", type = "UserToken")
class UserToken(
        @Id var userId: String = "",
        var token: String = ""
)

@Document(indexName = "minion", type = "Cache")
class Cache(
        @Id var cacheId: String,
        var question: String = "",
        var answer: String = ""
)