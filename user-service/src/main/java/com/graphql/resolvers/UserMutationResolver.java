package com.graphql.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;
import com.graphql.models.User;
import com.graphql.service.UserRepository;

public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserRepository userRepository;

    public UserMutationResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser() {
        User user = new User("1", "rebecca");
        return user;
    }

    public User updateUser() {
        return new User("1", "rebecca");
    }

    public boolean deleteUser(String userId) {
        return true;
    }

}
