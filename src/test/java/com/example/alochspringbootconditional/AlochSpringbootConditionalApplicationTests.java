package com.example.alochspringbootconditional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlochSpringbootConditionalApplicationTests {

	private long testStartTime;

	@Container
	private static GenericContainer<?> devapp = new GenericContainer<>("devapp:1").withExposedPorts(8080);
    @Container
    private static GenericContainer<?> prodapp = new GenericContainer<>("prodapp:1").withExposedPorts(8081);

    @Autowired
	TestRestTemplate restTemplate;

	@BeforeEach
	void initTest() { System.out.println("Starting new Test");
		testStartTime = System.nanoTime();
	}
	@AfterEach
	void finalizeTest() {
		System.out.println("Test completed:" + (System.nanoTime() - testStartTime));
	}

	@Test
	void devTest() {
		ResponseEntity<String> devEntity = restTemplate.getForEntity("http://localhost:" +
				devapp.getMappedPort(8080) + "/profile", String.class);
		String actualDevMessage = devEntity.getBody();
		String expectedDevMessage = "Current profile is dev";
		Assertions.assertEquals(expectedDevMessage, actualDevMessage, "Dev test failed: ER is not equal AR");
		System.out.println("yes");
	}

	@Test
	void prodTest() {
		ResponseEntity<String> prodEntity = restTemplate.getForEntity("http://localhost:" +
				prodapp.getMappedPort(8081) + "/profile", String.class);
		String actualProdMessage = prodEntity.getBody();
		String expectedProdMessage = "Current profile is production";
		Assertions.assertEquals(expectedProdMessage, actualProdMessage, "Prod test failed: ER is not equal AR");
	}


}