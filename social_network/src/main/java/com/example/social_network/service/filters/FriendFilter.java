package com.example.social_network.service.filters;
import com.example.social_network.domain.User;
import com.example.social_network.service.Specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
public class FriendFilter extends UserFilter{

    public Specification<User> getSpecification(User user) {
        return Specification.where(BaseSpecification.isFriend(user)).and(super.toSpecification());
    }
}
