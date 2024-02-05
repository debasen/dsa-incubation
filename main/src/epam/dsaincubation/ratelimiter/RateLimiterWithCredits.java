package epam.dsaincubation.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterWithCredits {
    public static final int SECOND_FACTOR = 1000;
    private final Integer windowSize;
    private final Integer requestLimit;
    private final Integer creditCap;
    private Map<Integer, Customer> customers = new HashMap<>();

    public RateLimiterWithCredits(Integer windowSize, Integer requestLimit, Integer creditCap) {
        this.windowSize = windowSize;
        this.requestLimit = requestLimit;
        this.creditCap = creditCap;
    }

    boolean rateLimit(int customerId) {
        int currentWindow = (int) (System.currentTimeMillis() / (windowSize * SECOND_FACTOR));

        Customer customer = customers.computeIfAbsent(customerId, id -> new Customer(currentWindow, 0));

        if (customer.getWindow() != currentWindow) {
            setCredits(customer, currentWindow);
            customer.reset(currentWindow);
            return false;
        }

        if (customer.getRequestCount() >= requestLimit && !customer.useCredit()) {
            return true;
        }

        customer.increment();
        return false;
    }

    /**
     * First request in new window should set the credits for the customer.
     * currentWindow - lastWindow give the number of unused window.
     * Multiplied by requestLimit gives the amount of credit
     *
     * @param customer
     * @param currentWindow
     */
    public void setCredits(Customer customer, Integer currentWindow){
        Integer credits = Math.min(creditCap, currentWindow - customer.getWindow()) * this.requestLimit;
        customer.setCredits(credits);
    }
}
