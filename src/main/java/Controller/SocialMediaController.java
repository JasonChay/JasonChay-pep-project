package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount != null) {
            ctx.json(om.writeValueAsString(registeredAccount));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.processLogin(account);
        if (verifiedAccount != null) {
            ctx.json(om.writeValueAsString(verifiedAccount));
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            ctx.json(om.writeValueAsString(createdMessage));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
        String pathParam = ctx.pathParam("message_id");
        Message message = messageService.getMessageByMessageId(Integer.valueOf(pathParam));
        if (message != null) {
            ctx.json(message);
            ctx.status(200);
        } 
        else {
            ctx.status(200);
        }
    }

    private void deleteMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
        String pathParam = ctx.pathParam("message_id");
        Message message = messageService.deleteMessageByMessageId(Integer.valueOf(pathParam));
        if (message != null) {
            ctx.json(message);
            ctx.status(200);
        }
        else {
            ctx.status(200);
        }
    }

    private void patchMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
        String pathParam = ctx.pathParam("message_id");
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);

        Message updatedMessage = messageService.updateMessageByMessageId(Integer.valueOf(pathParam), message.getMessage_text());
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }

    private void getAllMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        String pathParam = ctx.pathParam("account_id");
        List<Message> messages = messageService.getAllMessagesByAccountId(Integer.valueOf(pathParam));
        ctx.json(messages);
        ctx.status(200);
    }
}