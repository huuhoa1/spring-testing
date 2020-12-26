package com.hbh.springtesting.example;

import com.hbh.springtesting.example.BaseTestClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends BaseTestClass {

	@Test
	public void validate_shouldReturnPanWhenRequestParamPeter() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();


		// when:
			ResponseOptions response = given().spec(request)
					.get("/hello/Pan");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);

		// and:
			String responseBody = response.getBody().asString();
			assertThat(responseBody).isEqualTo("Who is this 'Pan' you're talking about?");
	}

}
