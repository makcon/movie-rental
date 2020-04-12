package test.maksim.video_rental.application.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.maksim.video_rental.domain.order.request.RentingRequestParams;
import test.maksim.video_rental.domain.order.request.ReturningRequestParams;
import test.maksim.video_rental.domain.order.result.RentingResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class OrderControllerTest {

    private static final String RENTING_URL_TEMPLATE = "/v1/orders/customers/%d/renting";
    private static final String RETURNING_URL_TEMPLATE = "/v1/orders/customers/%d/returning";

    private static final Integer GIVEN_CUSTOMER_ID = 1;
    private static final Integer GIVEN_UNKNOWN_CUSTOMER_ID = 100;
    private static final int GIVEN_MOVIE_ID = 1;

    private static final RentingRequestParams GIVEN_RENTING_REQUEST_PARAMS = RentingRequestParams.builder()
            .movieIds(List.of(GIVEN_MOVIE_ID))
            .rentingDays(1)
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void return_404_if_requested_customer_not_found_to_rent_movie() throws Exception {
        // when
        var resultActions = performRequest(
                HttpMethod.POST,
                String.format(RENTING_URL_TEMPLATE, GIVEN_UNKNOWN_CUSTOMER_ID),
                GIVEN_RENTING_REQUEST_PARAMS
        );

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void rent_movie_and_return_result() throws Exception {
        // when
        var resultActions = performRequest(
                HttpMethod.POST,
                String.format(RENTING_URL_TEMPLATE, GIVEN_CUSTOMER_ID),
                GIVEN_RENTING_REQUEST_PARAMS
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.createdOrders", hasSize(1)))
                .andExpect(jsonPath("$.createdOrders[0].movieId").value(GIVEN_MOVIE_ID));
    }

    @Test
    void return_404_if_requested_customer_not_found_to_return_movie() throws Exception {
        // given
        var orderId = rentMovie();

        // when
        var resultActions = performRequest(
                HttpMethod.PUT,
                String.format(RETURNING_URL_TEMPLATE, GIVEN_UNKNOWN_CUSTOMER_ID),
                createReturningParams(orderId)
        );

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void return_rented_movie_and_return_result() throws Exception {
        // given
        var orderId = rentMovie();

        // when
        var resultActions = performRequest(
                HttpMethod.PUT,
                String.format(RETURNING_URL_TEMPLATE, GIVEN_CUSTOMER_ID),
                createReturningParams(orderId)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.closedOrders", hasSize(1)))
                .andExpect(jsonPath("$.closedOrders[0].orderId").value(orderId));
    }

    private ResultActions performRequest(HttpMethod method, String uri, Object body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.request(method, uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        );
    }

    private Integer rentMovie() throws Exception {
        var resultActions = performRequest(
                HttpMethod.POST,
                String.format(RENTING_URL_TEMPLATE, GIVEN_CUSTOMER_ID),
                GIVEN_RENTING_REQUEST_PARAMS
        );

        var json = resultActions
                .andReturn()
                .getResponse().getContentAsString();

        return objectMapper.readValue(json, RentingResult.class)
                .getCreatedOrders()
                .get(0)
                .getOrderId();
    }

    private ReturningRequestParams createReturningParams(Integer orderId) {
        return ReturningRequestParams.builder()
                .orderIds(List.of(orderId))
                .build();
    }
}