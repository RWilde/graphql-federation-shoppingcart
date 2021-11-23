package poc.graphql.repository;

import com.google.gson.Gson;
import jakarta.inject.Singleton;
import poc.graphql.models.Cart;
import poc.graphql.models.Item;
import poc.graphql.models.Purpose;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class DummyCartRepository implements Repository {
    List<Item> items = List.of(new Item("SKU1", 1), new Item("SKU2", 2));
    private final HashMap<String, Cart> carts = new HashMap<String, Cart>();
    private final Gson gson = new Gson();

    public DummyCartRepository() {
        carts.put("1", new Cart("cart", Purpose.CART, items));
        carts.put("2", new Cart("wish list", Purpose.WISHLIST, items));
    }

    public Cart findById(String id) {
        return carts.values().stream().filter(cart -> cart.getId().equals(id)).findFirst().get();
    }

    public Cart save(Cart cart) {
        var returnedCard = carts.put(cart.getId(), cart);
        return returnedCard == null ? cart : returnedCard;
    }

    public Boolean deleteCart(String cartId) {
        return carts.values().removeIf(cart -> cart.getId().equals(cartId));
    }

    @Override
    public List<Cart> findAll() {
        return carts.values().stream().collect(Collectors.toList());
    }

    @Override
    public Cart update(Cart cart) {
        return null;
    }

    @Override
    public Cart mergeCarts(String mainCartId, String toMergeCartId) {
        return null;
    }

    @Override
    public Cart splitCart(String newCartId, String oldCartId, List<Item> itemSubList) {
        return null;
    }

    @Override
    public String serialise(Cart cart) {
        return null;
    }

    @Override
    public Cart deserialise(String cart) {
        return null;
    }

    @Override
    public Cart copy(String cartId) {
        return null;
    }
}
