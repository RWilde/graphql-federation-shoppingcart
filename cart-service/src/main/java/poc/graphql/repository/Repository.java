package poc.graphql.repository;

import poc.graphql.models.Cart;
import poc.graphql.models.Item;

import java.util.List;

public interface Repository {

    Cart findById(String id);

    Cart save(Cart cart);

    Boolean deleteCart(String cartId);

    List<Cart> findAll();

    Cart update(Cart cart);

    Cart mergeCarts(String mainCartId, String toMergeCartId);

    Cart splitCart(String newCartId, String oldCartId, List<Item> itemSubList);

    String serialise(Cart cart);

    Cart deserialise(String cart);

    Cart copy(String cartId);

}
