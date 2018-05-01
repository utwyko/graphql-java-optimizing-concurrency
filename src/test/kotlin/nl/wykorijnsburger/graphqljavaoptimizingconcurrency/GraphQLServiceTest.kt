package nl.wykorijnsburger.graphqljavaoptimizingconcurrency

import graphql.ExecutionInput
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GraphQLServiceTest {

    @Test
    fun `Should call product and offer concurrently`() {
        val graphQLService = GraphQLService()

        val result = graphQLService.graphQL.executeAsync(
            ExecutionInput.newExecutionInput()
                .query(
                    """
{
  product(id : "123") {
    id
    title
    offer {
      price
    }
    summaries {
      small
	}
  }
}
            """
                )
                .build()
        )
            .get()

        assertThat(result).isNotNull
    }
}