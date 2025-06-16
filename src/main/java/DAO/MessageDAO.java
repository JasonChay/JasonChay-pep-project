package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // add a message into the database and return it with the generated message_id afterward
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setInt(3, (int) message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pKeyResultSet = ps.getGeneratedKeys();
            while(pKeyResultSet.next()) {
                int generated_pkey = pKeyResultSet.getInt("message_id");
                return new Message(generated_pkey, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // return List object of all messages or an empty List if there are no messages
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet pKeyResultSet = ps.executeQuery();
            while(pKeyResultSet.next()) {
                Message message = new Message();
                message.setMessage_id(pKeyResultSet.getInt("message_id"));
                message.setPosted_by(pKeyResultSet.getInt("posted_by"));
                message.setMessage_text(pKeyResultSet.getString("message_text"));
                message.setTime_posted_epoch(pKeyResultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // retrieve a messasge given message_id
    public Message getMessageByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // delete a message given message_id
    // we will retrieve the Message object of the message to be deleted in the service class
    public void deleteMessageByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // update a message given message_id
    // we will retrieve the Message object of the message to be updated in the service class
    public void updateMessageByMessageId(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // return List object of all messages given an account_id or an empty List if there are no messages
    public List<Message> getAllMessagesByAccountId(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, account_id);

            ResultSet pKeyResultSet = ps.executeQuery();
            while(pKeyResultSet.next()) {
                Message message = new Message();
                message.setMessage_id(pKeyResultSet.getInt("message_id"));
                message.setPosted_by(pKeyResultSet.getInt("posted_by"));
                message.setMessage_text(pKeyResultSet.getString("message_text"));
                message.setTime_posted_epoch(pKeyResultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
