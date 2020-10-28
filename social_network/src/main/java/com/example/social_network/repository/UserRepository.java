package com.example.social_network.repository;
import com.example.social_network.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User (пользователь)
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);

    /*
        TODO
                select people.pers_id    as person,
               people.pers_name  as person_name,
               peoplef.pers_id   as friend_id,
               peoplef.pers_name as friend_name
          from people
          join friendships
            on people.pers_id = friendships.pers_id
            or people.pers_id = friendships.friend_id
          join people peoplef
            on (peoplef.pers_id = friendships.pers_id and
               peoplef.pers_id <> people.pers_id)
            or (peoplef.pers_id = friendships.friend_id and
               peoplef.pers_id <> people.pers_id)
         order by 2, 4
     */
}
