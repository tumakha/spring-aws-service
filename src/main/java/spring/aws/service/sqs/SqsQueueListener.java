package spring.aws.service.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import spring.aws.service.domain.OrderEvent;
import spring.aws.service.s3.S3Writer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

/**
 * @author Yuriy Tumakha
 */
@Component
@Slf4j
@AllArgsConstructor
public class SqsQueueListener {

    private final S3Writer s3Writer;

    @SqsListener("${sqs.orderQueue}")
    public void processMessage(OrderEvent orderEvent) throws JsonProcessingException {
        log.info("Incoming order: " + orderEvent);
        s3Writer.saveOrder(orderEvent);
    }

}
