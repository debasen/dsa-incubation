package epam.dsaincubation.ratelimiter;

public class Customer {
    private Integer window;
    private Integer requestCount;
    private Integer credits;

    public Customer(Integer window, Integer requestCount) {
        this.window = window;
        this.requestCount = requestCount;
    }

    public Integer getWindow() {
        return window;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public void reset(int currentWindow) {
        this.window = currentWindow;
        this.requestCount = 0;
    }

    public void increment() {
        this.requestCount++;
    }


    public boolean useCredit(){
        if(credits < 0)
            return false;
        credits--;
        return true;
    }
}
