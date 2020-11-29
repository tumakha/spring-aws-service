package spring.aws.service.sqs;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import spring.aws.service.test.TestConfig;
import spring.aws.service.domain.OrderEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Yuriy Tumakha
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class SqsQueueListenerTest {

    @Value("${sqs.orderQueue}")
    private String orderQueue;

    @Value("${s3.orderBucket}")
    private String orderBucket;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AmazonSQSAsync amazonSQSAsync;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Test
    void messageUploadedToS3OnceConsumedFromSQS() {
        String orderId = UUID.randomUUID().toString();
        OrderEvent orderEvent = new OrderEvent(orderId, "MacBook Pro 16\"", false, LocalDateTime.now());

        amazonSQSAsync.listQueues().getQueueUrls().forEach(System.out::println);
        amazonS3.listBuckets().forEach(System.out::println);

        queueMessagingTemplate.convertAndSend(orderQueue, orderEvent);

        given()
                .ignoreException(AmazonS3Exception.class)
                .await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> {
                    System.out.println("Content = " + amazonS3.getObjectAsString(orderBucket, orderId));
                    assertNotNull(amazonS3.getObject(orderBucket, orderId));
                });
    }

}
