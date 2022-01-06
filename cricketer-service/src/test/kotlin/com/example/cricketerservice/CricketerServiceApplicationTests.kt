package com.example.cricketerservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = arrayOf("spring.cloud.discovery.enabled = false"))
class CricketerServiceApplicationTests(
        @Autowired
        val webTestClient: WebTestClient,
        @Autowired
        val cricketerRepository: CricketerRepository
) {


	@Test
	fun contextLoads() {
	}

	@Test
	fun testAddCricketer() {
		val cricketer = Cricketer("1", "s","in",12, "1")
		webTestClient.post().uri("/api/cricketers")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(cricketer), Cricketer::class.java)
						.exchange()
						.expectStatus().isCreated()
						.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
						.expectBody()
						.jsonPath("$.id").isNotEmpty()
						.jsonPath("$.name").isEqualTo("s")

	}

    @Test
    fun testGetAllCricketers() {
        webTestClient.get().uri("/api/cricketers")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Cricketer::class.java)
    }

    @Test
    fun testDeleteCricketer() {
        val cricketer = cricketerRepository.save(
                Cricketer(
                "someid",
                        "sachin",
                        "ind",
                        150,
                        "1"
                )
        ).block()

        webTestClient.delete()
                .uri("api/cricketers/{id}", Collections.singletonMap("id", cricketer?.id ?: "someid"))
                .exchange()
                .expectStatus().isOk()
    }
}
