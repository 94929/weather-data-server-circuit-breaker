package ic.doc.be;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class WeatherDataCircuitBreaker extends HystrixCommand<String> {
    private final String uri;

    public WeatherDataCircuitBreaker(String uri) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.uri = uri;
    }

    @Override
    protected String run() {
        try {
            return Request.Get(uri).execute().returnContent().asString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getFallback() {
        return "The system is currently unavailable.";
    }
}
