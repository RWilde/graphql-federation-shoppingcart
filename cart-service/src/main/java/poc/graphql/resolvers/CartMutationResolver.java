package poc.graphql.resolvers;


import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import poc.graphql.models.Cart;
import poc.graphql.models.Purpose;
import poc.graphql.models.input.CartInput;
import poc.graphql.models.input.UpdateCartInput;
import poc.graphql.repository.Repository;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class CartMutationResolver implements GraphQLMutationResolver {

    private final Repository cartRepository;


    public CartMutationResolver(Repository cartsRepository) {
        this.cartRepository = cartsRepository;
    }

    public Cart createCart(CartInput cart, DataFetchingEnvironment dataFetchingEnvironment) {
        var context = dataFetchingEnvironment.getGraphQlContext();
        if (cart != null) {
            return cartRepository.save(new Cart(cart.getName(), cart.getPurpose(), cart.getItems()));
        } else {
            return cartRepository.save(new Cart("default", Purpose.CART));
        }
    }

    public Boolean deleteCart(String cartId) {
        return cartRepository.deleteCart(cartId);
    }

    public Cart updateCart(
            UpdateCartInput cart) {
        var cartToUpdate = cartRepository.findById(cart.getId());

        if (cartToUpdate == null) return null;

        if (cart.getName() != null)
            cartToUpdate.setName(cart.getName());

        if (cart.getItems() != null)
            cartToUpdate.setItems(cart.getItems());

        if (cart.getPurpose() != null)
            cartToUpdate.setPurpose(cart.getPurpose());

        cartToUpdate.setUpdateTime(new Date());
        return cartRepository.update(cartToUpdate);
    }
}
