package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import org.vaadin.example.DataService;
import org.vaadin.example.objetos_parametro.Usuario;

import java.io.IOException;
import java.net.URISyntaxException;

@Route(value = "inicio-sesion")
public class InicioSesionLayout extends FlexLayout {

    public InicioSesionLayout() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // Campo de usuario
        TextField usuarioField = new TextField();
        usuarioField.setPlaceholder("Usuario");
        usuarioField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        usuarioField.setWidth("300px");

        // Campo de contraseña
        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Contraseña");
        passwordField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        passwordField.setWidth("300px");

        // Botón de inicio de sesión
        Button iniciarSesionButton = new Button("Iniciar Sesión", event -> {
            // Lógica de inicio de sesión aquí
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
        iniciarSesionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        iniciarSesionButton.setWidth("200px");

        // Botón de registro
        Button registroButton = new Button("Registrarse", event -> {
            // Lógica de registro aquí
            Usuario nuevoUsuario = new Usuario(usuarioField.getValue(), passwordField.getValue(), null, null, null);
            try {
                DataService.addUsuario(nuevoUsuario);
                Notification.show("Usuario registrado exitosamente").setPosition(Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Error al registrar el usuario: " + e.getMessage()).setPosition(Notification.Position.MIDDLE);
            }
        });
        registroButton.setWidth("200px");

        // Layout para los campos de usuario y contraseña
        VerticalLayout fieldsLayout = new VerticalLayout(usuarioField, passwordField);
        fieldsLayout.setSpacing(true);

        // Layout para los botones
        HorizontalLayout buttonsLayout = new HorizontalLayout(iniciarSesionButton, registroButton);
        buttonsLayout.setSpacing(true);

        // Contenedor para centrar y estilizar el contenido
        Div contentContainer = new Div(fieldsLayout, buttonsLayout);
        contentContainer.getStyle().set("background-color", "#ffffff"); // Color de fondo blanco
        contentContainer.getStyle().set("border-radius", "10px"); // Borde redondeado
        contentContainer.getStyle().set("padding", "20px"); // Relleno interno
        contentContainer.getStyle().set("box-shadow", "0px 0px 10px 0px rgba(0,0,0,0.1)"); // Sombra

        // Añadir el contenedor al layout principal
        add(contentContainer);
    }
}
