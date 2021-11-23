package com.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import com.graphql.models.User;
import com.graphql.service.UserRepository;

public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserRepository userRepository;

    public UserQueryResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String userId) {
        return userRepository.findById(userId);
    }

}
