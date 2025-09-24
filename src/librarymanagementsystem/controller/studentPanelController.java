package librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import librarymanagementsystem.model.DatabaseConnection;
import librarymanagementsystem.model.LoadStage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class studentPanelController implements Initializable {

    @FXML
    private TableView booksTable;
    @FXML
    private TableColumn colBookId;
    @FXML
    private TableColumn colBookName;
    @FXML
    private TableColumn colBorrowedDate;
    @FXML
    private TableColumn colDueDate;
    @FXML
    private JFXButton home;

    @FXML
    private TableView finesTable;
    @FXML
    private TableColumn colFineBookName;
    @FXML
    private TableColumn colFineAmount;
    @FXML
    private TableColumn colFineStatus;

    @FXML
    private ListView notificationsList;

    @FXML
    private Label studentName;
    @FXML
    private Label studentEmail;

    @FXML
    private TabPane studentTabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBorrowedBooks();
        loadFines();
        loadNotifications();
        loadProfile();
    }

    private void loadBorrowedBooks() {
        ObservableList<Object> data = FXCollections.observableArrayList();
        // Example: Implement loading books for the logged-in student from DB
        // You can use student ID from loginController.userID
        booksTable.setItems(data);
    }

    private void loadFines() {
        ObservableList<Object> data = FXCollections.observableArrayList();
        // Example: Implement loading fines for the logged-in student from DB
        finesTable.setItems(data);
    }

    private void loadNotifications() {
        ObservableList<String> data = FXCollections.observableArrayList();
        // Example: Implement loading notifications for the logged-in student from DB
        notificationsList.setItems(data);
    }

    private void loadProfile() {
        // Example: Load student name and email from loginController static fields or DB
        try (Connection conn = DatabaseConnection.Connect();
             PreparedStatement pre = conn.prepareStatement("SELECT Username, Email FROM User WHERE ID = ?")) {
            pre.setInt(1, loginController.userID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                studentName.setText(rs.getString("Username"));
                studentEmail.setText(rs.getString("Email"));
            }
        } catch (SQLException ex) {
            System.err.println("Error loading profile: " + ex.getMessage());
        }
    }

    @FXML
    private void loadHomePanel(ActionEvent event) {
        studentTabPane.getSelectionModel().select(0);
    }

    @FXML
    private void loadBooksPanel(ActionEvent event) {
        studentTabPane.getSelectionModel().select(0); // My Books tab
    }

    @FXML
    private void loadFinesPanel(ActionEvent event) {
        studentTabPane.getSelectionModel().select(1); // Fines tab
    }

    @FXML
    private void loadNotificationsPanel(ActionEvent event) {
        studentTabPane.getSelectionModel().select(2); // Notifications tab
    }

    @FXML
    private void loadProfilePanel(ActionEvent event) {
        studentTabPane.getSelectionModel().select(3); // Profile tab
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        booktDataEntryController.isinEditMode = false;
        LoadStage stage = new LoadStage("/librarymanagementsystem/view/login.fxml", home);
    }
}
