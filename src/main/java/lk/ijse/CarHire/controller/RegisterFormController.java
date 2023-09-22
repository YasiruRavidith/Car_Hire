package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.db.DbConnection;
import lk.ijse.CarHire.dto.Register;
import lk.ijse.CarHire.dto.tm.RegisterTm;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterFormController {
    public TextField Unametxt;
    public TextField Uemail;
    public TextField Uusername;
    public PasswordField Upw;
    public TextField Uidtxt;
    public TextField Umobile;
    public AnchorPane Registerroot;


    public TextField Searchidtxt;

    @FXML
    private TableColumn<?, ?> TableUusername;

    @FXML
    private TableColumn<?, ?> tableUEmail;

    @FXML
    private TableColumn<?, ?> tableUId;

    @FXML
    private TableColumn<?, ?> tableUMobile;

    @FXML
    private TableColumn<?, ?> tableUName;

    @FXML
    private TableView<RegisterTm> tableUser;

    public  void initialize() throws SQLException {
        setCellValueFactory();
        List<Register> registerList = loadtable();

        setTableData(registerList);


    }

    private void setTableData(List<Register> registerList) {
        ObservableList<RegisterTm> oblist = FXCollections.observableArrayList();

        for (Register register:registerList) {
            var tm = new RegisterTm(register.getId(), register.getName(), register.getUsername(), register.getEmail(), register.getMobile());
            oblist.add(tm);
        }
        tableUser.setItems(oblist);
    }

    private void setCellValueFactory() {
        tableUId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableUName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableUusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableUEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableUMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    private List<Register> loadtable() throws SQLException {

        Connection con = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM user";
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery(sql);

        List<Register> registerList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String mobile = resultSet.getString(3);
            String email = resultSet.getString(4);
            String username = resultSet.getString(5);
            String password = resultSet.getString(6);

            var register = new Register(id,name,username,password,email,mobile);
            registerList.add(register);
        }
        return registerList;
    }

    @FXML
    void bntAdminlog(ActionEvent event) throws IOException {
        String id = Uidtxt.getText();
        String username = Uusername.getText();
        String name = Unametxt.getText();
        String password = Upw.getText();
        String mobile = Umobile.getText();
        String email = Uemail.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, mobile);
            pstm.setString(4, email);
            pstm.setString(5, username);
            pstm.setString(6, password);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {

                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "New User Account Created !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnClear(ActionEvent actionEvent) {
        clear();
        /*RegisterTm reg =tableUser.getSelectionModel().getSelectedItem();
        System.out.println(reg.getId());*/

    }

    public void clear(){
        Uidtxt.setText("");
        Uusername.setText("");
        Umobile.setText("");
        Uemail.setText("");
        Upw.setText("");
        Unametxt.setText("");
        Searchidtxt.setText("");
    }

    public void btnBackToLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.Registerroot.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.centerOnScreen();
    }

    public void txtidOnAction(ActionEvent actionEvent) {
        String id = Searchidtxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM user WHERE iduser=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                String Uidtxt = resultSet.getString(1);
                String Unametxt = resultSet.getString(2);
                String Umobile = resultSet.getString(3);
                String Uemail = resultSet.getString(4);
                String Uusername = resultSet.getString(5);
                String Upw = resultSet.getString(6);

                var register = new Register(Uidtxt, Uusername, Unametxt, Upw, Umobile, Uemail);

                setFields(register);

            }else{
                new Alert(Alert.AlertType.WARNING,"User not Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(Register register) {
        this.Unametxt.setText(register.getName());
        this.Uidtxt.setText(register.getId());
        this.Umobile.setText(register.getMobile());
        this.Uemail.setText(register.getEmail());
        this.Uusername.setText(register.getUsername());
        this.Upw.setText(register.getPassword());

    }

    public void btonUpdate(ActionEvent actionEvent) {
        String id = Uidtxt.getText();
        String username = Uusername.getText();
        String name = Unametxt.getText();
        String password = Upw.getText();
        String mobile = Umobile.getText();
        String email = Uemail.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE user SET  name=?, phone=?, email=?, username=?, password=? WHERE iduser=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,name);
            pstm.setString(2,mobile);
            pstm.setString(3,email);
            pstm.setString(4,username);
            pstm.setString(5,password);
            pstm.setString(6,id);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "User Updated!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String id = Searchidtxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="DELETE From user WHERE iduser=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            boolean isDeleted = pstm.executeUpdate() >0;
            if(isDeleted){
                clear();
                initialize();
                new Alert(Alert.AlertType.WARNING, "User Deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
