package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import lesson7.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    public User get(User user) throws Exception {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if(user.getId() == null){
            throw new Exception("Couldn't find user in DB");
        } else {
            entityManager.merge(user);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.equal(root,user));
        return entityManager.createQuery(query).getSingleResult();
    }

    public void delete(User user) throws Exception {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (user.getId() == null) {
            throw new Exception("Couldn't find user in DB");
        } else {
            user = entityManager.merge(user);
        }
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    /**
     * Поиск пользователя по имени и фамилии
     * @param name
     * @param surname
     * @return пользователя
     */
    public List<User> findUsersByNameAndSurname(String name, String surname){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root) // select * from User
                .where(cb.equal(root.get("name"),name), cb.equal(root.get("surname"),surname)); //where User.id = id
        List<User> users = entityManager.createQuery(query).getResultList();
        users.forEach(System.out::println);
        return users;
    }

    /**
     * Список пользователей у которых есть диалог с текущим пользователем
     * @param user
     * @return список пользователей
     */
    public Set<User> findDialogsByUser(User user){
        Long userId = user.getId();
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<Message> messageRoot = query.from(Message.class); //From User u

        //Сет в котором будут храниться искомые юзеры
        Set<User> userSet = new HashSet<>();

        //select authorId from message where recieverId In ('userId');
        query.select(messageRoot.get("authorId")).where(cb.equal(messageRoot.get("recieverId"),userId)).distinct(true);
        userSet.addAll(entityManager.createQuery(query).getResultList());

        //select recieverId from message where authorId In ('userId')
        query.select(messageRoot.get("recieverId")).where(cb.equal(messageRoot.get("authorId"),userId)).distinct(true);
        userSet.addAll(entityManager.createQuery(query).getResultList());

        return userSet;
    }

    /**
     * Список всех сообщений между указанными пользователями
     * @param user1
     * @param user2
     * @return список сообщений
     */
    public List<Message> findMessagesByUsers(User user1, User user2){

        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> messageRoot = query.from(Message.class);

        Predicate user1Author  = cb.equal(messageRoot.get("authorId"),user1);
        Predicate user2Reciever = cb.equal(messageRoot.get("recieverId"),user2);
        Predicate predicate1 = cb.and(user1Author,user2Reciever);

        Predicate user2Author  = cb.equal(messageRoot.get("authorId"),user2);
        Predicate user1Reciever = cb.equal(messageRoot.get("recieverId"),user1);
        Predicate predicate2 = cb.and(user2Author, user1Reciever);

        query.select(messageRoot).where(cb.or(predicate1,predicate2));
        query.orderBy(cb.asc(messageRoot.get("date")));
        List<Message> messageList =   entityManager.createQuery(query).getResultList();
        messageList.forEach(System.out::println);
        return messageList;
    }
}

