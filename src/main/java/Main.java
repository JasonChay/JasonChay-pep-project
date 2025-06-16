import Controller.SocialMediaController;
import io.javalin.Javalin;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        /*SocialMediaDAO socialMediaDAO = new SocialMediaDAO();
        Account newAccount = new Account("jasonchay", "password");
        socialMediaDAO.registerAccount(newAccount);
        Account verifiedAccount = socialMediaDAO.processLogin(newAccount);
        //System.out.println(verifiedAccount.getAccount_id());
        System.out.println(verifiedAccount == null);*/

    }
}
