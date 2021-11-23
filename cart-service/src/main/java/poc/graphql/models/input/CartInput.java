package poc.graphql.models.input;

import lombok.Data;
import poc.graphql.models.Item;
import poc.graphql.models.Purpose;

import java.util.List;

@Data
public class CartInput {

    private List<Item> items;

    private String name;

    private Purpose purpose;

    public CartInput() {
    }

    public CartInput(List<Item> items, String name, Purpose purpose) {
        this.items = items;
        this.name = name;
        this.purpose = purpose;
    }

}
