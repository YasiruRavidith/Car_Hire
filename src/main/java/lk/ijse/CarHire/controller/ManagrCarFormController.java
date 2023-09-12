package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.CarHire.db.DbConnection;
import lk.ijse.CarHire.dto.Categories;
import lk.ijse.CarHire.dto.ManageCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagrCarFormController {
    public TextField caridtxt;
    public TextField vehicalnumtxt;
    public TextField brandtxt;
    public TextField carmodeltxt;
    public TextField yeattxt;
    public TextField pricepdtxt;
    public TextField carsearch;
    public Label secteedlabletxt;
    public Label availabilitytxt;
    @FXML
    private ComboBox<String> cartypeCombo;

    public  void initialize() throws SQLException {
        loadcombobox();
    }
    private void loadcombobox() throws SQLException {

        Connection con = DbConnection.getInstance().getConnection();

        String sql= "SELECT name FROM car_category";
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery(sql);

        List<String> comboList = new ArrayList<>();

        while(resultSet.next()){
            String name = resultSet.getString(1);
            comboList.add(name);
        }
        cartypeCombo.setItems(FXCollections.observableArrayList(comboList));

    }

    public void carSearchAction(ActionEvent actionEvent) {
        String id = carsearch.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM car WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                String idCar = resultSet.getString(1);
                String Brand = resultSet.getString(2);
                String Model = resultSet.getString(3);
                String Vehicle_Number = resultSet.getString(4);
                String year = resultSet.getString(5);
                String Price_per_day = resultSet.getString(6);
                String category_id = resultSet.getString(7);
                String availability = resultSet.getString(8);

                var manageCar = new ManageCar(idCar,Brand,Model,Vehicle_Number,year,Price_per_day,category_id,availability);

                setFields(manageCar);

            }else{
                new Alert(Alert.AlertType.WARNING,"Car Data not Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void setFields(ManageCar manageCar) {
        this.caridtxt.setText(manageCar.getIdCar());
        this.brandtxt.setText(manageCar.getBrand());
        this.carmodeltxt.setText(manageCar.getModel());
        this.vehicalnumtxt.setText(manageCar.getVehicle_Number());
        this.yeattxt.setText(manageCar.getYear());
        this.pricepdtxt.setText(manageCar.getPrice_per_day());
        this.cartypeCombo.setValue(manageCar.getCategory_id());
        //this.secteedlabletxt.setText(manageCar.getCategory_id());
        this.availabilitytxt.setText(manageCar.getAvailability());

    }

    public void bntsave(ActionEvent actionEvent) {
        String carid = caridtxt.getText();
        String brand = brandtxt.getText();
        String model = carmodeltxt.getText();
        String vehinum = vehicalnumtxt.getText();
        String year = yeattxt.getText();
        double price = Double.parseDouble(pricepdtxt.getText());
        String cartype = cartypeCombo.getValue();
        String avlability = "Available";

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO car VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, carid);
            pstm.setString(2, brand);
            pstm.setString(3, model);
            pstm.setString(4, vehinum);
            pstm.setString(5, year);
            pstm.setDouble(6, price);
            pstm.setString(7, cartype);
            pstm.setString(8, avlability);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "New Car Data Added !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear(){
        caridtxt.setText("");
        carmodeltxt.setText("");
        carsearch.setText("");
        brandtxt.setText("");
        vehicalnumtxt.setText("");
        yeattxt.setText("");
        pricepdtxt.setText("");
        secteedlabletxt.setText("");
        availabilitytxt.setText("");

    }

    public void btnClear(ActionEvent actionEvent) {
        clear();
    }

    public void btonUpdate(ActionEvent actionEvent) {
        String carid = caridtxt.getText();
        String brand = brandtxt.getText();
        String model = carmodeltxt.getText();
        String vehinum = vehicalnumtxt.getText();
        String year = yeattxt.getText();
        double price = Double.parseDouble(pricepdtxt.getText());
        String cartype = cartypeCombo.getValue();
        String avlability = availabilitytxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE car SET  Brand=?, Model=?, Vehicle_Number=?, year=?, Price_per_day=?, category_id=?, availability=?  WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,brand);
            pstm.setString(2,model);
            pstm.setString(3,vehinum);
            pstm.setString(4,year);
            pstm.setDouble(5,price);
            pstm.setString(6,cartype);
            pstm.setString(7,avlability);
            pstm.setString(8,carid);


            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Car Data Updated!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    public void btnDelete(ActionEvent actionEvent) {
        String id = carsearch.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="DELETE FROM car WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            boolean isDeleted = pstm.executeUpdate() >0;
            if(isDeleted){
                clear();
                initialize();
                new Alert(Alert.AlertType.WARNING, "Car Data Deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
