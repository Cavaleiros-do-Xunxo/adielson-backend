package calaveirosdoxunxo.adielson.models;

import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private DeliveryType delivery;
    private Status status;
    private List<OrderItemRequest> items;

}
