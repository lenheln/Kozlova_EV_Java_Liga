package lesson7;

import lesson7.Dao.MessageDao;
import lesson7.Dao.UserDao;
import lesson7.Entity.Message;
import lesson7.Entity.User;

public class Main {

    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("Bob");
        user1.setSurname("Dilan");
        user1.setInfo("Love music");

        User user2 = new User();
        user2.setName("Nick");
        user2.setSurname("Cave");
        user2.setInfo("Love music too");

        User user3 = new User();
        user3.setName("Bob");
        user3.setSurname("Marley");
        user3.setInfo("Love raggie");

        UserDao userDao = new UserDao();
        userDao.saveOrUpdate(user1);
        userDao.saveOrUpdate(user2);
        userDao.saveOrUpdate(user3);

        Message msg1 = new Message("Hello!", user1, user2);
        Message msg2 = new Message("Hi!", user2, user1);
        Message msg3 = new Message("No woman no cry!", user3, user2);
        Message msg4 = new Message("Yo!", user3, user1);
        Message msg5 = new Message("Bamboleylo", user1, user3);
        Message msg6 = new Message("Bue", user1, user2);
        MessageDao messageDao = new MessageDao();
        messageDao.save(msg1);
        messageDao.save(msg2);
        messageDao.save(msg3);
        messageDao.save(msg4);
        messageDao.save(msg5);
        messageDao.save(msg6);
        userDao.findDialogByUser(user2);

    }

    public static void startDialog(User user1, User user2){

    }

}
