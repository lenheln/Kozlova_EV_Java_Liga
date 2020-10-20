package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import lesson7.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.criteria.JoinType.LEFT;
import static javax.persistence.criteria.JoinType.RIGHT;

public class UserDao {

    public User saveOrUpdate(User user){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (user.getId() == null) {
                entityManager.persist(user);
            } else
                user = entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
        return user;
    }

    public List<User> findUsersByNameAndSurname(String name, String surname){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root) // select * from User
                .where(cb.equal(root.get("name"),name), cb.equal(root.get("surname"),surname)); //where User.id = id
        return entityManager.createQuery(query).getResultList();
    }

    public void delete(User user){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        } else
            user = entityManager.merge(user);
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public Set<User> findDialogsByUser(User user){
        Long userId = user.getId();
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        /**
         *  Select all msgs with userID
         */
//        CriteriaQuery<Message> query = cb.createQuery(Message.class);
//        Root<Message> messageRoot = query.from(Message.class);
//        query.select(messageRoot);
//        Predicate author = cb.equal(messageRoot.get("authorId"), userId);
//        Predicate reciever = cb.equal(messageRoot.get("recieverId"), userId);
//        query.where(cb.or(author,reciever));
//        entityManager.createQuery(query).getResultList().forEach(System.out::println);


         CriteriaQuery<User> query = cb.createQuery(User.class);
         Root<Message> messageRoot = query.from(Message.class); //From User u

        //Сет в котором будут храниться искомые юзеры
         Set<User> userSet = new HashSet<>();

         //select authorId from message where recieverId In ('userId');
         query.select(messageRoot.get("authorId")).where(cb.equal(messageRoot.get("recieverId"),userId)).distinct(true);
        userSet.addAll(entityManager.createQuery(query).getResultList());

         //select recieverId from message where authorId In ('userId');
         query.select(messageRoot.get("recieverId")).where(cb.equal(messageRoot.get("authorId"),userId)).distinct(true);
        userSet.addAll(entityManager.createQuery(query).getResultList());

         return userSet;
    }
}

