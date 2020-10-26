package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import lesson7.Entity.User;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

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
        } finally {
            entityManager.close();
        }
        return user;
    }

    public User get(Long id) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        User user = null;
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return user;
    }

    public void delete(User user) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (user.getId() == null) {
                throw new Exception("Couldn't find user in DB");
            } else {
                user = entityManager.merge(user);
            }
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }


    /**
     * Ищет пользователей по части фамилии
     *
     * @param partOfSurname
     * @return список пользователей в фамилии которых есть такая часть
     */
    public List<User> getUsersByPartOfSurname(String partOfSurname) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<User> userList = null;
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.like(root.get("surname"), "%"+ partOfSurname + "%"));
            userList = entityManager.createQuery(query).getResultList();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }
        return userList;
    }
    /**
     * Поиск пользователя по имени и фамилии
     * @param name
     * @param surname
     * @return пользователя
     */
    public List<User> getUsersByNameAndSurname(String name, String surname){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<User> users = null;
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root) // select * from User
                    .where(cb.equal(root.get("name"), name), cb.equal(root.get("surname"), surname)); //where User.id = id
            users = entityManager.createQuery(query).getResultList();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return users;
    }

    /**
     * Список пользователей у которых есть диалог с текущим пользователем
     * @param user
     * @return сет пользователей
     */
    public List<String> findDialogsByUser(User user){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<String> resultList = null;
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> query = cb.createQuery(String.class);

            Root<Message> messageRoot = query.from(Message.class);
            Join<Message, User> authorJoin = messageRoot.join("authorId");
            Join<Message, User> recieverJoin = messageRoot.join("recieverId");

            Predicate userAuthor = cb.equal(messageRoot.get("authorId"), user);
            Predicate userReciever = cb.equal(messageRoot.get("recieverId"), user);

            query.select(
                    cb.<String>selectCase()
                            .when(cb.equal(messageRoot.get("authorId"), user), recieverJoin.get("surname"))
                            .otherwise(authorJoin.get("surname"))
            ).where(cb.or(userAuthor, userReciever))
                    .distinct(true);
            resultList = entityManager.createQuery(query).getResultList();
            /**
             *      157 - id for example
             *
             *     SELECT DISTINCT
             * 	   CASE
             * 			WHEN message.authorId IN (157) THEN message.recieverId
             *          ELSE message.authorId
             *          END
             *  	FROM message
             *  	WHERE message.authorId IN (157) OR message.recieverId IN (157);
             */
            
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return resultList;
    }

    /**
     * Список всех сообщений между указанными пользователями
     * @param user1
     * @param user2
     * @return список сообщений
     */
    public List<Message> findMessagesByUsers(User user1, User user2){

        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<Message> messageList = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Message> query = cb.createQuery(Message.class);
            Root<Message> messageRoot = query.from(Message.class);

            Predicate user1Author = cb.equal(messageRoot.get("authorId"), user1);
            Predicate user2Reciever = cb.equal(messageRoot.get("recieverId"), user2);
            Predicate predicate1 = cb.and(user1Author, user2Reciever);

            Predicate user2Author = cb.equal(messageRoot.get("authorId"), user2);
            Predicate user1Reciever = cb.equal(messageRoot.get("recieverId"), user1);
            Predicate predicate2 = cb.and(user2Author, user1Reciever);

            query.select(messageRoot).where(cb.or(predicate1, predicate2));
            query.orderBy(cb.asc(messageRoot.get("date")));
            messageList = entityManager.createQuery(query).getResultList();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return messageList;
    }
}

