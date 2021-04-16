package calaveirosdoxunxo.adielson.models;

import lombok.Data;

@Data
public class MenuItemRequest {
    private String name;
    private double price;
    private String image;
    private String description;
    private boolean inMenu;

    private long category;
}
