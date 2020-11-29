package spring.aws.service.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

/**
 * @author Yuriy Tumakha
 */
@TestConfiguration
public class TestConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${sqs.orderQueue}")
    private String orderQueue;

    @Value("${s3.orderBucket}")
    private String orderBucket;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public LocalStackContainer localStackContainer() {
        LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                .withServices(S3, SQS)
                .withEnv("DEFAULT_REGION", region);
        return localStack;
    }

    @Primary
    @Bean
    public AmazonSQSAsync amazonSQS(LocalStackContainer localStack) throws IOException, InterruptedException {
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", orderQueue);
        localStack.execInContainer("awslocal", "s3", "mb", "s3://" + orderBucket);

        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(localStack.getDefaultCredentialsProvider())
                .withEndpointConfiguration(localStack.getEndpointConfiguration(SQS))
                .build();
    }

    @Primary
    @Bean
    public AmazonS3 amazonS3(LocalStackContainer localStack) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(localStack.getDefaultCredentialsProvider())
                .withEndpointConfiguration(localStack.getEndpointConfiguration(S3))
                .enablePathStyleAccess()
                .build();
    }

}
