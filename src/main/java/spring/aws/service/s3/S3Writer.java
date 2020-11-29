package spring.aws.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spring.aws.service.domain.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Yuriy Tumakha
 */
@Component
@Slf4j
public class S3Writer {

    private final AmazonS3 amazonS3;
    private final ObjectMapper objectMapper;

    @Value("${s3.orderBucket}")
    private String orderBucket;

    public S3Writer(AmazonS3 amazonS3, ObjectMapper objectMapper) {
        this.amazonS3 = amazonS3;
        this.objectMapper = objectMapper;
    }

    public void saveOrder(OrderEvent orderEvent) throws JsonProcessingException {
        amazonS3.putObject(orderBucket, orderEvent.getId(), objectMapper.writeValueAsString(orderEvent));
        log.info("Successfully uploaded order to S3");
    }

}
