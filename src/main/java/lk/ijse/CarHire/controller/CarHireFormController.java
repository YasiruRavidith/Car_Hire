package lk.ijse.CarHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CarHireFormController {
    public AnchorPane Carhireroot;
    public AnchorPane CarHire_Panelroot;

    public void CategoryActionButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Categories_form.fxml"));

        this.CarHire_Panelroot.getChildren().clear();
        this.CarHire_Panelroot.getChildren().add(root);
    }

    public void ManageCarActiononButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Managr_Car_form.fxml"));

        this.CarHire_Panelroot.getChildren().clear();
        this.CarHire_Panelroot.getChildren().add(root);
    }

    public void CustomerActionButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Customer_form.fxml"));

        this.CarHire_Panelroot.getChildren().clear();
        this.CarHire_Panelroot.getChildren().add(root);
    }

    public void RentActionButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Rent_form.fxml"));

        this.CarHire_Panelroot.getChildren().clear();
        this.CarHire_Panelroot.getChildren().add(root);
    }

    public void ActiononLogout(ActionEvent actionEvent) throws IOException {
        Parent loinroot = FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"));

        Scene scene = new Scene(loinroot);
        Stage stage = (Stage) this.Carhireroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Log in");
        stage.centerOnScreen();
    }
}
