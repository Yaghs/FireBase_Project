package com.example.csc325_firebase_webview_auth.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class LogInController {
    @FXML
    private Button LogInButton;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button PasswordButton;
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField PassWord;


    @FXML
    private void GoToSignUp(){
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/SignUp.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control, like the LogIn button, and set the scene
            Stage stage = (Stage) LogInButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe log it or show an error message
        }
    }

    @FXML
    private void LogInValidation(){
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/AccessFBView.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control, like the LogIn button, and set the scene
            Stage stage = (Stage) LogInButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe log it or show an error message
        }
    }

}
