package com.github.manerajona.reactive.ports.input.rs

import com.github.manerajona.reactive.ports.input.rs.handler.JediHandlerV1
import com.github.manerajona.reactive.ports.input.rs.request.JediRequest
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@WebFluxTest(JediRouter::class)
internal class JediRouterTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var handler: JediHandlerV1

    @Test
    fun jediRoutes_shouldCreateNewJedi_success() {
        val id = UUID.randomUUID()
        val createRequest = JediRequest(
            "Obi-Wan Kenobi",
            "male",
            "57BBY",
            "Tatooine",
            "www.swapi.tech/api/people/10"
        )

        val location = UriComponentsBuilder.fromPath(JediHandlerV1.JEDIS_ID_URI)
            .buildAndExpand(id.toString())
            .toUri()

        BDDMockito.given(handler.createJedis(any()))
            .willReturn(ServerResponse.created(location).build())

        webTestClient.post()
            .uri(JediHandlerV1.JEDIS_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(createRequest))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location(location.toString())
    }

    @Test
    fun jediRoutes_shouldGetExistingJedi_success() {
        val id = UUID.randomUUID()

        val jedi = JediResponse(
            id,
            "Luke Skywalker",
            "male",
            "19BBY",
            "Stewjon",
            "www.swapi.tech/api/people/1"
        )

        BDDMockito.given(handler.getJedi(any()))
            .willReturn(ServerResponse.ok().bodyValue(jedi))

        val response = webTestClient.get()
            .uri(JediHandlerV1.JEDIS_ID_URI, id)
            .exchange()
            .expectStatus().isOk
            .expectBody(JediResponse::class.java)
            .returnResult()
            .responseBody

        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response?.name).isEqualTo(jedi.name)
        Assertions.assertThat(response?.gender).isEqualTo(jedi.gender)
        Assertions.assertThat(response?.birthYear).isEqualTo(jedi.birthYear)
        Assertions.assertThat(response?.planet).isEqualTo(jedi.planet)
        Assertions.assertThat(response?.url).isEqualTo(jedi.url)

    }

    @Test
    fun jedis_shouldGetListOfJedis_success() {
        val id = UUID.randomUUID()
        val jedi = JediResponse(
            id,
            "Luke Skywalker",
            "male",
            "19BBY",
            "www.swapi.tech/api/planets/1",
            "www.swapi.tech/api/people/1"
        )

        val jedis: List<JediResponse> = listOf(jedi, jedi, jedi)

        BDDMockito.given(handler.getJedis(any()))
            .willReturn(ServerResponse.ok().bodyValue(jedis))

        webTestClient.get()
            .uri(JediHandlerV1.JEDIS_URI)
            .exchange()
            .expectStatus().isOk
            .expectBody(MutableList::class.java)
            .value({ list: List<*> -> list.size.toLong() }, Matchers.equalTo(3L))
    }

    @Test
    fun updateJedi_shouldUpdateExistingJedi_success() {
        val id = UUID.randomUUID()
        val updateRequest = JediRequest(
            "Obi-Wan Kenobi",
            "male",
            "57BBY",
            "Tatooine",
            "www.swapi.tech/api/people/10"
        )

        BDDMockito.given(handler.updateJedi(any()))
            .willReturn(ServerResponse.noContent().build())

        webTestClient.put()
            .uri(JediHandlerV1.JEDIS_ID_URI, id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(updateRequest))
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun deleteJedi_shouldDeleteJedi_success() {
        val id = UUID.randomUUID()

        BDDMockito.given(handler.deleteJedi(any()))
            .willReturn(ServerResponse.noContent().build())

        webTestClient.delete()
            .uri(JediHandlerV1.JEDIS_ID_URI, id)
            .exchange()
            .expectStatus().isNoContent
    }
}

