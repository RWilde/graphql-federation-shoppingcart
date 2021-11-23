package poc.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class User {

    String id;
    String username;
    List<Cart> carts;
}
