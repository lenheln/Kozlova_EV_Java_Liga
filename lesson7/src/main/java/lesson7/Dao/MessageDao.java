package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import lesson7.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MessageDao {
    public Message saveOrUpdate(Message message){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try {
            // start the transaction
            entityManager.getTransaction().begin();

            // save a object
            if (message.getId() == null) {
                entityManager.persist(message);
            } else {
                message = entityManager.merge(message);
            }

            // commit transction
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
        return message;
    }

    public void delete(Message message) throws Exception {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if(message.getId() == null){
            throw new Exception("Couldn't find message in DB");
        } else {
            entityManager.merge(message);
        }
        entityManager.remove(message);
        entityManager.getTransaction().commit();
    }

    public Message get(Message message) throws Exception {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if(message.getId() == null){
            throw new Exception("Couldn't find message in DB");
        } else {
            entityManager.merge(message);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(cb.equal(root,message));
        return entityManager.createQuery(query).getSingleResult();
    }
}
