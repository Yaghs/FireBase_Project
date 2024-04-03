package com.example.csc325_firebase_webview_auth.view;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuthException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpController {

    @FXML
    private TextField UserName;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private Text InvalidPassword;
    @FXML
    private Text InvalidConfirmPassword;
    @FXML
    private Text RegistrationError;
    @FXML
    private Button SignUpButton;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{3,16}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,18}$");

    @FXML
    private void handleSignUpAction() {
        if (!validateUsername(UserName.getText())) {
            // Show invalid username message
        } else if (!validatePassword(PasswordField.getText())) {
            InvalidPassword.setOpacity(1.0); // Show invalid password message
        } else if (!PasswordField.getText().equals(ConfirmPassword.getText())) {
            InvalidConfirmPassword.setOpacity(1.0); // Show passwords do not match message
        } else {
            // If all validations pass, attempt to register the user
            registerUser(UserName.getText(), PasswordField.getText());
        }
    }

    private boolean validateUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    private boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Registers a new user with Firebase Authentication.
     *
     * @param email The email for the new user.
     * @param password The password for the new user.
     */
    private void registerUser(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());

            // Navigate to the login screen
            navigateToLogin();

        } catch (FirebaseAuthException e) {
            System.out.println("Failed to create new user: " + e.getMessage());
            RegistrationError.setOpacity(1.0);
        }
    }

    private void navigateToLogin() {
        try {
            // Load the FXML document for the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/LogIn.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control, e.g., the SignUp button
            Stage stage = (Stage) UserName.getScene().getWindow();

            // Set the scene on the current stage to switch screens
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe log it or show an error message
        }
    }

}

