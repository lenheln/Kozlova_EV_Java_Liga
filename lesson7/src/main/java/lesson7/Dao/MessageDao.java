package lesson7.Dao;

import lesson7.Config.JpaConfig;
import lesson7.Entity.Message;
import javax.persistence.EntityManager;


public class MessageDao {
    public Message saveOrUpdate(Message message){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (message.getId() == null) {
                entityManager.persist(message);
            } else {
                message = entityManager.merge(message);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return message;
    }

    public void delete(Message message) {
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
        } finally {
            entityManager.close();
        }
    }

    /**
     * Получает сообщение из базы данных
     * @param id
     * @return инстанс сообщение из БД
     * @throws Exception
     */
    public Message get(Long id) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        Message msg = null;
        try {
            entityManager.getTransaction().begin();
            msg = entityManager.find(Message.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return msg;
    }
}
