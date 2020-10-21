package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import javax.persistence.EntityManager;


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
        try {
            entityManager.getTransaction().begin();
            if (message.getId() == null) {
                throw new Exception("Couldn't find message in DB");
            } else {
                entityManager.merge(message);
            }
            entityManager.remove(message);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
    }

    /**
     * Получает сообщение из базы данных
     * @param message
     * @return инстанс сообщение из БД
     * @throws Exception
     */
    public Message get(Message message) throws Exception {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        Message msg = null;
        try {
            entityManager.getTransaction().begin();
            if (message.getId() == null) {
                throw new Exception("Couldn't find message in DB");
            } else {
                msg = entityManager.find(Message.class, message.getId());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
        return msg;
    }
}
