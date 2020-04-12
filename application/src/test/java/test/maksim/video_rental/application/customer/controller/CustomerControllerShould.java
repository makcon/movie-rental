package test.maksim.video_rental.application.customer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class CustomerControllerShould {

    private static final String GET_CUSTOMER_URL_TEMPLATE = "/v1/customers/%d";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void return_all_stored_customers() throws Exception {
        // given
        var expectedCustomersCount = 2;

        // when
        var resultActions = performGetRequest("/v1/customers");

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedCustomersCount)));
    }

    @Test
    void return_404_if_requested_customer_not_found() throws Exception {
        // given
        var givenCustomerId = 100;

        // when
        var resultActions = performGetRequest(String.format(GET_CUSTOMER_URL_TEMPLATE, givenCustomerId));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void return_customer_by_id() throws Exception {
        // given
        var givenCustomerId = 1;

        // when
        var resultActions = performGetRequest(String.format(GET_CUSTOMER_URL_TEMPLATE, givenCustomerId));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(givenCustomerId));
    }

    private ResultActions performGetRequest(String uri) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }
}