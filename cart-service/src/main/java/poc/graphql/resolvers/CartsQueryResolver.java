package poc.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import poc.graphql.models.Cart;
import poc.graphql.repository.Repository;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CartsQueryResolver implements GraphQLQueryResolver {

    private final Repository cartsRepository;

    public CartsQueryResolver(Repository cartsRepository) {
        this.cartsRepository = cartsRepository;
    }

    public Cart cart(String cartId) {
        return cartsRepository.findById(cartId);
    }

    public List<Cart> carts() {
        return cartsRepository.findAll();
    }
}
