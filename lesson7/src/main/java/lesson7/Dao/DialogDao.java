//package lesson7.Dao;
//
//import lesson7.Config.JpaConfig;
//import lesson7.Entity.Dialog;
//import lesson7.Entity.User;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class DialogDao {
//
//    public Dialog save(Dialog dialog){
//        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
// //       Dialog dialog = new Dialog(userList);
//        try {
//            // start the transaction
//            entityManager.getTransaction().begin();
//
//            // save a object
//            if (dialog.getId() == null) {
//                entityManager.persist(dialog);
//            } else {
//                dialog = entityManager.merge(dialog);
//            }
//
//            // commit transction
//            entityManager.getTransaction().commit();
//
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            entityManager.close();
//            throw e;
//        }
//        return dialog;
//    }
//}
