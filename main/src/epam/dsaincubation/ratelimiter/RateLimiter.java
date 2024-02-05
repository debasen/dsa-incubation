package epam.dsaincubation.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    public static final int SECOND_FACTOR = 1000;
    private final Integer windowSize;
    private final Integer requestLimit;
    private Map<Integer, Customer> customers = new HashMap<>();

    public RateLimiter(Integer windowSize, Integer requestLimit) {
        this.windowSize = windowSize;
        this.requestLimit = requestLimit;
    }

    boolean rateLimit(int customerId) {
        int currentWindow = (int) (System.currentTimeMillis() / (windowSize * SECOND_FACTOR));

        Customer customer = customers.getOrDefault(customerId, new Customer(currentWindow, 0));
        customers.put(customerId, customer);
        if (customer.getWindow() == currentWindow) {
            if (customer.getRequestCount() >= requestLimit) {
                return true;
            } else {
                customer.increment();
            }
        } else {
            customer.reset(currentWindow);
        }
        return false;
    }
}
