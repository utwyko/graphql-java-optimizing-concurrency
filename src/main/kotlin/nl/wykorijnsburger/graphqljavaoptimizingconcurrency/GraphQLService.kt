package nl.wykorijnsburger.graphqljavaoptimizingconcurrency

import graphql.GraphQL
import graphql.schema.DataFetcher
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import org.springframework.core.io.ClassPathResource
import java.util.concurrent.CompletableFuture

class GraphQLService {

    val graphQL: GraphQL

    init {
        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()

        val productSchemaFile = ClassPathResource("product.graphqls").file

        val typeRegistry = schemaParser.parse(productSchemaFile)
        val wiring = buildRuntimeWiring()
        val schema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring)

        graphQL = GraphQL.newGraphQL(schema)
            .build()
    }

    private fun buildRuntimeWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
            .type("QueryType", { it.dataFetcher("product", productDataFetcher) })
            .type("Product", {
                it.dataFetcher("offer", offerDataFetcher)
                    .dataFetcher("summaries", summariesDataFetcher)
            })
            .build()
    }
}

private val productDataFetcher: DataFetcher<CompletableFuture<Product>> = DataFetcher {
    CompletableFuture.supplyAsync {
        println("Fetching product ${System.currentTimeMillis()}")
        Thread.sleep(1000)
        Product("id", null)
    }
}

private val offerDataFetcher: DataFetcher<CompletableFuture<Offer>> = DataFetcher {
    CompletableFuture.supplyAsync {
        println("Fetching offer ${System.currentTimeMillis()}")
        Offer("$4")
    }
}

private val summariesDataFetcher: DataFetcher<CompletableFuture<Summaries>> = DataFetcher {
    CompletableFuture.supplyAsync {
        println("Fetching summaries ${System.currentTimeMillis()}")
        Thread.sleep(1000)
        Summaries("small summary")
    }
}

data class Product(val id: String, val title: String?)

data class Offer(val price: String)

data class Summaries(val small: String)