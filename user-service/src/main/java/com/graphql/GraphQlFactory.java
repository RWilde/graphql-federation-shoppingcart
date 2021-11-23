package com.graphql;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import com.graphql.resolvers.UserMutationResolver;
import com.graphql.resolvers.UserQueryResolver;
import com.graphql.service.UserRepository;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.inject.Singleton;

@Factory
public class GraphQlFactory {

    private static final UserRepository cartRepository;

    static {
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongo = new MongoClient().getDatabase("CheckoutMicroservice").withCodecRegistry(pojoCodecRegistry);
        cartRepository = new UserRepository(mongo.getCollection("shopping-carts"));
    }


    @Singleton
    @Bean
    public GraphQL graphQL() {

        var builder = SchemaParser.newParser().file("schema.graphqls").resolvers(new UserQueryResolver(cartRepository)
                , new UserMutationResolver(cartRepository)
        );

        var graphQlSchema = builder.build().makeExecutableSchema();

        return GraphQL.newGraphQL(graphQlSchema).build();
    }
}
