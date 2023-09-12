package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.CarHire.db.DbConnection;
import lk.ijse.CarHire.dto.Categories;
import lk.ijse.CarHire.dto.Customer;
import lk.ijse.CarHire.dto.tm.CategoriesTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
    public TextField CustIdtxt;
    public TextField CustNametxt;
    public TextField CustNic;
    public TextField CustMobiletxt;
    public TextField CustAdrestxt;
    public TextField sechbyIcusD;

    @FXML
    private TableColumn<?, ?> CusAdresColmn;

    @FXML
    private TableColumn<?, ?> CusIDcolmn;

    @FXML
    private TableColumn<?, ?> CusMObilColmn;

    @FXML
    private TableColumn<?, ?> CusnameColmn;

    @FXML
    private TableColumn<?, ?> NICColmn;

    @FXML
    private TableView<Customer> cutromerTable;

    public  void initialize() throws SQLException {
        setCellValueFactory();
        List<Customer> customerList = loadtable();

        setTableData(customerList);
    }

    private void setTableData(List<Customer> customerList) {
        ObservableList<Customer> oblist = FXCollections.observableArrayList();

        for (Customer customer:customerList) {
            var tm = new Customer(customer.getIdCustomer(), customer.getName(), customer.getNic(), customer.getAdress(), customer.getPhone());
            oblist.add(tm);
        }
        cutromerTable.setItems(oblist);
    }

    private void setCellValueFactory() {
        CusIDcolmn.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        CusnameColmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        NICColmn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        CusAdresColmn.setCellValueFactory(new PropertyValueFactory<>("adress"));
        CusMObilColmn.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }

    private List<Customer> loadtable() throws SQLException {

        Connection con = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM customer";
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery(sql);

        List<Customer> customerList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String nic = resultSet.getString(3);
            String adress = resultSet.getString(4);
            String phone = resultSet.getString(5);

            var customer = new Customer(id,name,nic,adress,phone);
            customerList.add(customer);
        }
        return customerList;
    }

    public void sechonaction(ActionEvent actionEvent) {
        serchbyall();
    }

    public void serchbyall(){
        String id = sechbyIcusD.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM customer WHERE idCustomer=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                String cusId = resultSet.getString(1);
                String CusName = resultSet.getString(2);
                String CusNic = resultSet.getString(3);
                String CusAddres = resultSet.getString(4);
                String Cusmobil = resultSet.getString(5);

                var customer = new Customer(cusId,CusName,CusNic,CusAddres,Cusmobil);

                setFields(customer);

            }else{
                new Alert(Alert.AlertType.WARNING,"Customer not Found !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(Customer customer) {
        this.CustIdtxt.setText(customer.getIdCustomer());
        this.CustNametxt.setText(customer.getName());
        this.CustNic.setText(customer.getNic());
        this.CustAdrestxt.setText(customer.getAdress());
        this.CustMobiletxt.setText(customer.getPhone());
    }

    public void bntsave(ActionEvent actionEvent) {
        String cusId = CustIdtxt.getText();
        String cusName = CustNametxt.getText();
        String cusNic = CustNic.getText();
        String cusAdress = CustAdrestxt.getText();
        String cusMobile = CustMobiletxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, cusId);
            pstm.setString(2, cusName);
            pstm.setString(3, cusNic);
            pstm.setString(4, cusAdress);
            pstm.setString(5, cusMobile);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "New Customer Added !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear(){
        CustMobiletxt.setText("");
        CustAdrestxt.setText("");
        CustNic.setText("");
        CustNametxt.setText("");
        CustIdtxt.setText("");
        sechbyIcusD.setText("");
    }

    public void btnClear(ActionEvent actionEvent) {
        clear();
    }

    public void btonUpdate(ActionEvent actionEvent) {
        String cusId = CustIdtxt.getText();
        String cusName = CustNametxt.getText();
        String cusNic = CustNic.getText();
        String cusAdress = CustAdrestxt.getText();
        String cusMobile = CustMobiletxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE customer SET name=?,nic=?,adress=?,phone=? WHERE idCustomer=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,cusName);
            pstm.setString(2,cusNic);
            pstm.setString(3,cusAdress);
            pstm.setString(4,cusMobile);
            pstm.setString(5,cusId);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Delails Updated!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        String id = sechbyIcusD.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="DELETE FROM customer WHERE idCustomer=?";

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

    public void buttonSerch(ActionEvent actionEvent) {
        serchbyall();
    }
}
