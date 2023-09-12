package lk.ijse.CarHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.db.Db;
import lk.ijse.CarHire.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUser;
    public PasswordField txtPw;
    public AnchorPane Loginroot;

    public void ActionONLOgin(ActionEvent actionEvent) throws IOException {
        String usr = txtUser.getText();
        String pw = txtPw.getText();

        if(usr.equals(Db.user) && pw.equals(Db.pw)){
            navigate();
        }else{
            try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM user WHERE username=? AND password=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,usr);
                pstm.setString(2,pw);


            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                navigate();
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid Username or Password").show();
            }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void navigate() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/CarHire_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.Loginroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Car Hire");
        stage.centerOnScreen();

    }

    public void BtnRegister(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Admin_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.Loginroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Car Hire");
        stage.centerOnScreen();
    }
}
