package it.algos.vaad23.backend.login;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.login.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 10-mar-2022
 * Time: 06:32
 */
@Route("login")
@PageTitle("Login | Algos")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("Algos"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent
                .getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

}
