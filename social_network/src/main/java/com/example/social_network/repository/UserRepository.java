package com.example.social_network.repository;

import com.example.social_network.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью User (пользователь)
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Добавление друга пользователю с userId
     *
     * @param idUser идентификатор пользователя
     * @param idFriend идентификатор друга
     * @return количество измененных в БД строк
     */
    @Modifying
    @Query(value = "INSERT INTO friendship (iduser, idfriend) VALUES (:user, :friend)", nativeQuery = true)
    int addFriend(@Param("user") Long idUser,
                  @Param("friend") Long idFriend);

    /**
     * Удаление друга из списка друзей
     *
     * @param idUser идентификатор пользователя, который совершает действие
     * @param idFriend идентификатор другя
     * @return количество измененных в БД строк
     */
    @Modifying
    @Query(value = "DELETE FROM friendship WHERE (iduser = :user) AND (idfriend = :friend)", nativeQuery = true)
    int deleteFriend(@Param("user") Long idUser,
                     @Param("friend") Long idFriend);
}
