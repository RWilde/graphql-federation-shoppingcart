package main.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import main.models.User;
import main.service.UserRepository;

public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserRepository userRepository;

    public UserQueryResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String userId){
        return userRepository.findById(userId);
    }

}
