package com.example.social_network.service.filters;
import com.example.social_network.domain.User;
import com.example.social_network.service.Specification.BaseSpecification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Фильтр друзей конкретного пользователя по полям-критериям
 */

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendFilter extends UserFilter {

    private Long id;

    @Override
    public Specification<User> toSpecification() {
        return Specification.where(BaseSpecification.isFriend(id)).and(super.toSpecification());
    }
}
