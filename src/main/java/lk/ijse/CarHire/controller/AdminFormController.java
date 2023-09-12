package lk.ijse.CarHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.db.Db;

import java.io.IOException;



public class AdminFormController {
    public TextField AdminUname;
    public PasswordField AdminPw;
    public AnchorPane Adminroot;

    public void CreateAccOnAction(ActionEvent actionEvent) throws IOException {
        String usr = AdminUname.getText();
        String pw = AdminPw.getText();

        if(usr.equals(Db.user) && pw.equals(Db.pw)){
            navToSave();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Username or Password").show();
        }

    }
    public void navToSave() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Register_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.Adminroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Register Here");
        stage.centerOnScreen();
    }

    public void LoginAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.Adminroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.centerOnScreen();
    }
}
