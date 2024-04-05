package com.example.csc325_firebase_webview_auth.view;//package modelview;

import com.example.csc325_firebase_webview_auth.model.Person;
import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AccessFBView {


    @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    @FXML
    private TextArea outputField;
    @FXML
    private ImageView ImageProfile;
    @FXML
    private TableView <Person> OutPutView;
    @FXML
    private TableColumn<Person, String> NameColumn;
    @FXML
    private TableColumn<Person, String> MajorColumn;
    @FXML
    private TableColumn<Person, Integer> AgeColumn;
    @FXML
    private MenuItem menuItemRegister;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem closeMenuItem;


    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    void initialize() {
        // Other initialization code...

        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        MajorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        AgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        OutPutView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listOfUsers = FXCollections.observableArrayList();
        OutPutView.setItems(listOfUsers);
    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData();
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
    }

    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/files/WebContainer.fxml");
    }

    public void addData() {
        // First, add to Firestore as you're already doing
        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));

        // Asynchronously write data to Firestore
        ApiFuture<WriteResult> result = docRef.set(data);

        // Now, update the TableView
        String name = nameField.getText();
        String major = majorField.getText();
        int age = Integer.parseInt(ageField.getText());

        // Create a new Person object
        Person newPerson = new Person(name, major, age);

        // Add this Person to the list backing the TableView
        listOfUsers.add(newPerson);

        // Ensure your TableView is set to use listOfUsers as its item source
        // This line should be somewhere in your initialization code if it's not already
        OutPutView.setItems(listOfUsers);
    }

    public boolean readFirebase() {
        key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) {
                    outputField.setText(outputField.getText() + document.getData().get("Name") + " , Major: " +
                            document.getData().get("Major") + " , Age: " +
                            document.getData().get("Age") + " \n ");
                    System.out.println(document.getId() + " => " + document.getData().get("Name"));
                    person = new Person(String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString()));
                    listOfUsers.add(person);
                }
                NameColumn.setCellValueFactory(
                        new PropertyValueFactory<Person,String>("name"));
                MajorColumn.setCellValueFactory(
                        new PropertyValueFactory<Person, String>("Major"));
                AgeColumn.setCellValueFactory(
                        new PropertyValueFactory<Person, Integer>("Age"));
                ObservableList<Person> ListPeople = OutPutView.getItems();
                ListPeople.addAll(listOfUsers);
            }

            else {
                System.out.println("No data");
            }
            key = true;

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    @FXML
    private void GoToSignUp(ActionEvent event) {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/SignUp.fxml"));
            Parent root = loader.load();

            // Get the source of the event, which is the MenuItem
            MenuItem menuItemRegister = (MenuItem) event.getSource();
            // Then get the Scene and Window from any Node within the Scene, but first, we need to get a reference to a Node
            Scene scene = menuItemRegister.getParentPopup().getOwnerWindow().getScene();
            // Assuming we successfully retrieved the Scene, get the Stage from the Scene
            Stage stage = (Stage) scene.getWindow();

            // Set the new Scene on the Stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe log it or show an error message
        }
    }
    @FXML
    private void CloseApplication(){
        Platform.exit();
    }
    @FXML
    public void deleteRecord() {

    }



}

