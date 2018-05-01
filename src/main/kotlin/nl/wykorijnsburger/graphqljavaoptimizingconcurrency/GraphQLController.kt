package nl.wykorijnsburger.graphqljavaoptimizingconcurrency

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class GraphQLController {

    val graphQLService = GraphQLService()

    @PostMapping("/graphql")
    fun graphql(@RequestBody graphQLQuery: String): Any {
        return graphQLService.graphQL.execute(graphQLQuery).getData()

    }
}