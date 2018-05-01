package nl.wykorijnsburger.graphqljavaoptimizingconcurrency

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GraphQLServiceTest {

    @Test(timeout = 1000)
    fun `Should call product and offer concurrently`() {
        val graphQLService = GraphQLService()

        val result = graphQLService.graphQL.execute(
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

        assertThat(result).isNotNull
    }
}