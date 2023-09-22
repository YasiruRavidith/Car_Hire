package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CarHire.db.DbConnection;
import lk.ijse.CarHire.dto.ManageCar;
import lk.ijse.CarHire.dto.Register;
import lk.ijse.CarHire.dto.tm.ManageCarTm;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class RentFormController {
    public TextField rentIdtxt;
    public TextField atvansedtxt;
    public TextField refundubletxt;
    public Label Perdayrentlable;
    public Label Totallable;
    public Label balunceLable;
    public TableColumn Cartypetype;
    public Label cartypetxt;
    public DatePicker datapickerId;
    public Label datapike_rlable;
    public TextField CustomerIdtxt1;
    public Label modeltxt1;
    public Label brandtxt1;
    public Label caridtxt1;
    public AnchorPane rentanchor;


    @FXML
    private TableColumn<?, ?> PPDType;

    @FXML
    private TableColumn<?, ?> VehiNumType;

    @FXML
    private TableColumn<?, ?> brandType;

    @FXML
    private TableColumn<?, ?> carIdtype;

    @FXML
    private TableView<ManageCarTm> cartypeTable;

    @FXML
    private TableColumn<?, ?> modelType;

    public  void initialize() throws SQLException {
        setCellValueFactory();
        loadthable();

        LocalDate maxDate = LocalDate.now().plusMonths(1);
        LocalDate minDate = LocalDate.now().plusDays(1);
        datapickerId.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }
                });
    }

    private void loadthable() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM car";

        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery(sql);

        List<ManageCar> manageCarList = new ArrayList<>();

        while(resultSet.next()){
            String idCar = resultSet.getString(1);
            String Brand = resultSet.getString(2);
            String Model = resultSet.getString(3);
            String Vehicle_Number = resultSet.getString(4);
            String year = resultSet.getString(5);
            String Price_per_day = resultSet.getString(6);
            String category_id = resultSet.getString(7);
            String availability = resultSet.getString(8);

            var manageCar = new ManageCar(idCar,Brand,Model,Vehicle_Number,year,Price_per_day,category_id,availability);
            manageCarList.add(manageCar);
        }
        setTableData(manageCarList);
    }

    private void setTableData(List<ManageCar> manageCarList) {
        String a ="Available";
        ObservableList<ManageCarTm> oblist = FXCollections.observableArrayList();

        for (ManageCar manageCar:manageCarList) {
            if(a.equals(manageCar.getAvailability())){
            var tm = new ManageCarTm(manageCar.getIdCar(), manageCar.getBrand(),manageCar.getModel(),manageCar.getVehicle_Number(),manageCar.getPrice_per_day(), manageCar.getCategory_id(), manageCar.getAvailability(), manageCar.getYear());
            oblist.add(tm);
            }
        }
        cartypeTable.setItems(oblist);
    }

    private void setCellValueFactory() {
        carIdtype.setCellValueFactory(new PropertyValueFactory<>("idCar"));
        brandType.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        modelType.setCellValueFactory(new PropertyValueFactory<>("Model"));
        VehiNumType.setCellValueFactory(new PropertyValueFactory<>("Vehicle_Number"));
        PPDType.setCellValueFactory(new PropertyValueFactory<>("Price_per_day"));
        Cartypetype.setCellValueFactory(new PropertyValueFactory<>("category_id"));
    }

    public void ButnRent(ActionEvent actionEvent) {

        ManageCarTm crty =cartypeTable.getSelectionModel().getSelectedItem();

        if(reterncheker(CustomerIdtxt1.getText())){
            new Alert(Alert.AlertType.ERROR, "Last rented Vehical Not Returned !").show();
        }else{

        String idrent = rentIdtxt.getText();
        String fromdate = ""+LocalDate.now();
        String todate =datapickerId.getValue().toString();
        Double advance = Double.parseDouble(atvansedtxt.getText());
        Double refund = Double.parseDouble(refundubletxt.getText());
        String isreturn = "No";
        Double ppdisrt = Double.parseDouble(crty.getPrice_per_day());
        String cutid =CustomerIdtxt1.getText();
        String caridrent = crty.getIdCar();

        double days =(double)DAYS.between(LocalDate.now(),datapickerId.getValue());
        double total =days*Double.parseDouble(crty.getPrice_per_day());

        double bal =total-Double.parseDouble(atvansedtxt.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO rent VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, idrent);
            pstm.setString(2, fromdate);
            pstm.setString(3, todate);
            pstm.setDouble(4, advance);
            pstm.setDouble(5, refund);
            pstm.setString(6, isreturn);
            pstm.setDouble(7, ppdisrt);
            pstm.setDouble(8, total);
            pstm.setDouble(9, bal);
            pstm.setString(10, cutid);
            pstm.setString(11, caridrent);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                rentbutton();
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "New Rent Detail Add !").show();
            }else{
                delete();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        }
    }

    public boolean reterncheker(String cusid){
        boolean checker=true;
        String cusid1 =cusid;
        String retn="No";
        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM rent WHERE cust_id=? AND is_retern=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,cusid1);
            pstm.setString(2,retn);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                checker =true;
            }else{
                checker =false;
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return checker;
    }

    public void rentbutton(){

        ManageCarTm crty =cartypeTable.getSelectionModel().getSelectedItem();


        String id = crty.getIdCar();
        String brand = crty.getBrand();
        String model = crty.getModel();
        String vehinum = crty.getVehicle_Number();
        String year = crty.getYear();
        Double ppd = Double.parseDouble(crty.getPrice_per_day());
        String catid = crty.getCategory_id();
        String avbly = "Not Available";

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE car SET  Brand=?, Model=?, Vehicle_Number=?, year=?, Price_per_day=?, category_id=?, availability=? WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,brand);
            pstm.setString(2,model);
            pstm.setString(3,vehinum);
            pstm.setString(4,year);
            pstm.setDouble(5,ppd);
            pstm.setString(6,catid);
            pstm.setString(7,avbly);
            pstm.setString(8,id);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Car Updated!");
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void delete(){
        String id = rentIdtxt.getId();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="DELETE FROM rent WHERE idRent=?";

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

    public void DatepickerOnaction(ActionEvent actionEvent) {

        ManageCarTm mgc =cartypeTable.getSelectionModel().getSelectedItem();

        String s =datapickerId.getValue().toString();
        datapike_rlable.setText("("+LocalDate.now()+") - ("+s+")");

        double days =(double)DAYS.between(LocalDate.now(),datapickerId.getValue());
        double total =days*Double.parseDouble(mgc.getPrice_per_day());
        Totallable.setText(""+total);


        double bal =total-Double.parseDouble(atvansedtxt.getText());
        balunceLable.setText("" +bal);
    }

    public void TableAction(MouseEvent mouseEvent) {
        ManageCarTm mgc =cartypeTable.getSelectionModel().getSelectedItem();

        cartypetxt.setText(mgc.getCategory_id());
        Perdayrentlable.setText(mgc.getPrice_per_day());
        caridtxt1.setText(mgc.getIdCar());
        brandtxt1.setText(mgc.getBrand());
        modeltxt1.setText(mgc.getModel());
        clear();
    }

    public void clear(){
        datapike_rlable.setText("Select Date");
        Totallable.setText("");
        balunceLable.setText("");

    }

    public void ButnRentedCars(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/RentedCars_form.fxml"));

        this.rentanchor.getChildren().clear();
        this.rentanchor.getChildren().add(root);
    }
}
