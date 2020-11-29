package spring.aws.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Yuriy Tumakha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private String id;
    private String product;
    private boolean expressDelivery;
    private LocalDateTime orderedAt;

}
