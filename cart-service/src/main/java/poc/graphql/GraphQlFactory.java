package poc.graphql;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import poc.graphql.repository.CartRepository;
import poc.graphql.resolvers.CartMutationResolver;
import poc.graphql.resolvers.CartsQueryResolver;

import javax.inject.Singleton;

@Factory
public class GraphQlFactory {

    private static final CartRepository cartRepository;

    static {
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongo = new MongoClient().getDatabase("CheckoutMicroservice").withCodecRegistry(pojoCodecRegistry);
        cartRepository = new CartRepository(mongo.getCollection("shopping-carts"));
    }


    @Singleton
    @Bean
    public GraphQL graphQL() {

        var builder = SchemaParser.newParser().file("schema.graphqls").resolvers(new CartsQueryResolver(cartRepository)
                , new CartMutationResolver(cartRepository)
        );

        var graphQlSchema = builder.build().makeExecutableSchema();

        return GraphQL.newGraphQL(graphQlSchema).build();
    }
}
