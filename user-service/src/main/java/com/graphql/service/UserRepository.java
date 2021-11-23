package com.graphql.service;

import com.mongodb.client.MongoCollection;
import com.graphql.models.User;
import org.bson.Document;

import java.util.List;

public class UserRepository {
    //    private final MongoCollection<Document> users;
    List<User> users = List.of(new User("1", "rebecca"));

    public UserRepository(MongoCollection<Document> carts) {
//        this.users= carts;
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(String userId) {
        return users.get(0);
    }
}
