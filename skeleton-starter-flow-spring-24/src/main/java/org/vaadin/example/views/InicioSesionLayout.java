package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.DataService;

import java.io.IOException;
import java.net.URISyntaxException;

@Route(value = "inicio-sesion")
public class InicioSesionLayout extends VerticalLayout {
    public InicioSesionLayout() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        TextField usuarioField = new TextField("Usuario");
        PasswordField passwordField = new PasswordField("Contraseña");
        Button iniciarSesionButton = new Button("Iniciar Sesión", event -> {
            String usuario = usuarioField.getValue();
            String password = passwordField.getValue();

            // Aquí iría la lógica para autenticar al usuario
            try {
                if (DataService.iniciarSesion(usuario, password) == 1) {
                    // Si la autenticación es exitosa, redireccionar a la página principal
                    UI.getCurrent().navigate("datos-ayuda-humanitaria-origen");
                } else {
                    Notification.show("Usuario o contraseña incorrectos").setPosition(Notification.Position.MIDDLE);
                }
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        this.add(usuarioField, passwordField, iniciarSesionButton);
    }

}
