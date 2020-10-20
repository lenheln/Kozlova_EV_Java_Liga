package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;

import javax.persistence.EntityManager;

public class MessageDao {
    public Message save(Message message){
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
}
