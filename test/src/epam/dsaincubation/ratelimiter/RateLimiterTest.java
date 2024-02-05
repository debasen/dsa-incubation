package epam.dsaincubation.ratelimiter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateLimiterTest {

    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        rateLimiter = new RateLimiter(5, 100);
    }

    @Test
    void rateLimitTrue() {
        for(int i=0;i<1000;i++){
            rateLimiter.rateLimit(10);
        }
        Assertions.assertTrue(rateLimiter.rateLimit(10));
    }

    @Test
    void rateLimitFalse() {
        for(int i=0;i<10;i++){
            rateLimiter.rateLimit(10);
        }
        Assertions.assertFalse(rateLimiter.rateLimit(10));
    }
}