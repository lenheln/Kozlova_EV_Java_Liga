package lesson7.Service;

import lesson7.Dao.MessageDao;
import lesson7.Entity.Message;

public class MessageService {

    private MessageDao messageDao;

    public MessageService(MessageDao messageDao) {
        this.messageDao = new MessageDao();
    }

    public Message sentMessage(Message message){
        return messageDao.saveOrUpdate(message);
    }
}
