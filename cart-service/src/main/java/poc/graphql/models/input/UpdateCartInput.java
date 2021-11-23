package poc.graphql.models.input;

import lombok.Data;
import poc.graphql.models.Item;
import poc.graphql.models.Purpose;

import java.util.List;

@Data
public class UpdateCartInput extends CartInput {

    private String id;

    public UpdateCartInput(String id, List<Item> items, String name, Purpose purpose) {
        super(items, name, purpose);
        this.id = id;
    }

    public UpdateCartInput() {
    }
}
