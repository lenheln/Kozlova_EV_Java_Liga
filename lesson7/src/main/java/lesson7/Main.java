package lesson7;

import lesson7.Config.JpaConfig;
import lesson7.Dao.MessageDao;
import lesson7.Dao.UserDao;
import lesson7.Entity.Message;
import lesson7.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static UserDao userDao;
    public static MessageDao messageDao;

    static {
        userDao = new UserDao();
        messageDao = new MessageDao();
    }

    public static void main(String[] args) throws Exception {

        try {
            List<User> users = new ArrayList<>();
            users.add(new User("Bob", "Dilan", "Love music"));
            users.add(new User("Nick", "Cave", "Love music too"));
            User user = new User("Bob", "Marley", "Love raggie");
            users.add(user);
            saveOrUpdateUsers(users);

            List<Message> messageList = new ArrayList<>();
            messageList.add(new Message("Hello!", users.get(0), users.get(2)));
            messageList.add(new Message("Hi!", users.get(2), users.get(0)));
            messageList.add(new Message("No woman no cry!", users.get(2), users.get(1)));
            messageList.add(new Message("Yo whats'up!", users.get(2), users.get(0)));
            Message message = new Message("Bye", users.get(0), users.get(1));
            messageList.add(message);
//            messageList.add(new Message("Bye", users.get(0), users.get(1)));
            saveOrUpdateMsg(messageList);
            userDao.findDialogsByUser(user);
//            System.out.println("**********\n" + userDao.getUsersBySurnameStartsWith("Mar"));
 //           System.out.println(messageDao.get(message));

            //        userDao.findDialogsByUser(user2);
//            userDao.findUsersByNameAndSurname("Elena", "Cave");
//            userDao.findMessagesByUsers(users.get(0), users.get(1));

        } finally {
            JpaConfig.getEntityManagerFactory().close();
        }
    }

    public static void saveOrUpdateUsers(List<User> userList){
        for (User u: userList) {
            userDao.saveOrUpdate(u);
        }
    }

    public static  void saveOrUpdateMsg(List<Message> messages){
        for (Message m : messages) {
            messageDao.saveOrUpdate(m);
        }
    }
}
