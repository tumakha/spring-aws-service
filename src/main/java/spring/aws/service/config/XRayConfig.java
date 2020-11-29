package spring.aws.service.config;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.plugins.ECSPlugin;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.net.URL;

/**
 * @author Yuriy Tumakha
 */
@Configuration
public class XRayConfig {

    /*
     * Configuration https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-configuration.html
     */
    static {
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new ECSPlugin());

        URL ruleFile = XRayConfig.class.getResource("/sampling-rules.json");
        builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));

        AWSXRay.setGlobalRecorder(builder.build());
    }

    /**
     * Incoming requests https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-filters.html
     */
    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter("Sample Service Incoming HTTP");
    }

}
