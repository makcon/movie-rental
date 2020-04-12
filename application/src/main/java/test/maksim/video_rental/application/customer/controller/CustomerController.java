package test.maksim.video_rental.application.customer.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.domain.customer.service.CustomerService;

import java.util.List;

@Api
@Slf4j
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping()
    public List<Customer> fetchAll() {
        log.info("Fetching all customers");
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Integer id) {
        log.info("Fetching customer by id: {}", id);
        return service.fetchById(id);
    }
}
