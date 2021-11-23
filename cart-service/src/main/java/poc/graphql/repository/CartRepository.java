package poc.graphql.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import poc.graphql.models.Cart;
import poc.graphql.models.Item;
import poc.graphql.models.Purpose;
import poc.graphql.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class CartRepository implements Repository {

    private final MongoCollection<Document> carts;

    public CartRepository(MongoCollection<Document> carts) {
        this.carts = carts;
    }

    public List<Cart> findAll() {
        var cartDoc = carts.find();
        List<Cart> returned = new ArrayList<>();
        for (var doc : cartDoc) {
            returned.add(cart(doc));
        }
        return returned;
    }

    @Override
    public Cart update(Cart cart) {
        carts.replaceOne(eq("_id", new ObjectId(cart.getId())), cartDoc(cart));
        return cart;
    }

    @Override
    public Cart mergeCarts(String mainCartId, String toMergeCartId) {
        Cart main = cart(carts.find(eqId(mainCartId)).first());
        Cart toBeDeleted = cart(carts.findOneAndDelete(eqId(toMergeCartId)));

        main.getItems().addAll(toBeDeleted.getItems());

        return update(main);
    }

    @Override
    public Cart splitCart(String newCartId, String oldCartId, List<Item> itemSubList) {
        Cart newCart = cart(carts.find(eqId(newCartId)).first());
        Cart oldCart = cart(carts.findOneAndDelete(eqId(oldCartId)));

        newCart.getItems().addAll(itemSubList);
        oldCart.getItems().removeAll(itemSubList);

        update(oldCart);
        return update(newCart);
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
        Cart cart = cart(carts.find(eqId(cartId)).first());
        cart.setId(null);

        return save(cart);
    }

    public Cart findById(String id) {
        Cart cart = cart(carts.find(eqId(id)).first());
        return cart;
    }

    public Cart save(Cart cart) {
        carts.insertOne(cartDoc(cart));
        return cart;
    }

    public Boolean deleteCart(String cartId) {
        return carts.findOneAndDelete(eqId(cartId)) != null;
    }

    private Bson eqId(String id){
        return eq("_id", new ObjectId(id));
    }

    private Document cartDoc(Cart cart) {
        return new Document()
                .append("name", cart.getName())
                .append("items", cart.getItems())
                .append("purpose", cart.getPurpose().toString())
                .append("creationTime", cart.getCreateTime())
                .append("updateTime", cart.getUpdateTime())
//                .append("owner", cart.getOwner())
                .append("shareCode", cart.getShareCode());
    }

    private Cart cart(Document doc) {
        if (doc == null) {
            return null;
        }
        return new Cart(
                doc.get("_id").toString(),
                doc.getString("name"),
                items(doc.getList("items", Document.class)),
                Purpose.valueOf(doc.getString("purpose")),
                doc.getDate("creationDate"),
                doc.getDate("updateDate"),
//new User("id", "rebecca"),
                doc.getInteger("shareCode"));
    }

    private List<Item> items(List<Document> items) {
        return items.stream().map(i -> new Item(i.getString("sku"), i.getInteger("quantity"))).collect(Collectors.toList());
    }
}
