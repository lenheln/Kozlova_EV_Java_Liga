package lesson7.Service;

import lesson7.Dao.MessageDao;
import lesson7.Entity.Message;

public class MessageService {

    private MessageDao messageDao;

    public Message sentMessage(Message message){
        messageDao = new MessageDao();
        return messageDao.save(message);
    }
}
