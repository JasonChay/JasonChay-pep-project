package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService() {
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }

    // Constructor when provided DAOs for use in mock testing
    public MessageService(AccountDAO accountDAO, MessageDAO messageDAO) {
        this.accountDAO = accountDAO;
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        // to post a new message it must not be blank, be no more than 255 chars, and have a valid posted_by
        if (message.getMessage_text().equals("")) {
            return null;
        }
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        if (accountDAO.getAccountByAccountId(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMessageId(int message_id) {
        return messageDAO.getMessageByMessageId(message_id);
    }

    // to delete a message check first if the message_id is of an existing message
    // the delete method has void return type, so we utilize getMessageById method in this class to help us verify the message exists and also to return the message to be deleted
    public Message deleteMessageByMessageId(int message_id) {
        Message messageToDelete = getMessageByMessageId(message_id);
        if (messageToDelete == null) {
            return null;
        }
        messageDAO.deleteMessageByMessageId(message_id);
        return messageToDelete;
    }

    // to update a message check first if the message contents are valid and if the message_id is valid
    // the DAO update method has void return type, so we utilize getMessageById method to create a new message with the new text to be returned
    public Message updateMessageByMessageId(int message_id, String message_text) {
        Message messageToUpdate = getMessageByMessageId(message_id);
        if (messageToUpdate == null) {
            return null;
        }
        if (message_text.equals("")) {
            return null;
        }
        if (message_text.length() > 255) {
            return null;
        }
        messageDAO.updateMessageByMessageId(message_id, message_text);
        return new Message(messageToUpdate.getMessage_id(), messageToUpdate.getPosted_by(), message_text, messageToUpdate.getTime_posted_epoch());
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }
}
