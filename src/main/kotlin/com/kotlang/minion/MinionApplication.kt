package com.kotlang.minion

import org.elasticsearch.node.NodeBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@SpringBootApplication
class MinionApplication {
    @Configuration
    @EnableElasticsearchRepositories(basePackages = arrayOf("com/kotlang/minion/repositories"))
    internal class Config {

        @Bean
        fun elasticsearchTemplate(): ElasticsearchOperations {
            return ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node().client())
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MinionApplication::class.java, *args)
}
