package calaveirosdoxunxo.adielson.models;

import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import lombok.Data;

@Data
public class OrderRequest {
    private long user;
    private DeliveryType delivery;
    private Status status;
    private Double total;
    private Long orderTime;
    private Long deliveryTime;
}
