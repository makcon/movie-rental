package test.maksim.video_rental.domain.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Integer customerId) {
        super("Customer not found, id: " + customerId);
    }
}
