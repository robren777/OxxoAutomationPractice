package RMS.util.PageObject;

import org.openqa.selenium.By;

import utils.selenium.SeleniumUtil;

public class Control_mInicio {

    SeleniumUtil u;
    String user;
    String password;

    // Generacion del constructor
    public Control_mInicio(SeleniumUtil u, String user, String password) {
        super();
        this.u = u;
        this.user = user;
        this.password = password;
    }

    // Pantalla de inicio de sesion
    By UserId = By.id("login-user-name");
    By PasswordId = By.id("login-user-password");
    By ButtonlogOn = By.id("login-submit-button");

    // Inicio de sesion

    public void logOn() {

        u.highLight(UserId);
        u.click(UserId);
        u.sendKeys(UserId, user);
        u.highLight(PasswordId);
        u.click(PasswordId);
        u.sendKeys(PasswordId, password);
        u.highLight(ButtonlogOn);
        u.click(ButtonlogOn);

    }

}
