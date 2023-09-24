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
import lk.ijse.CarHire.dto.Rent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RentedCarsFormController {

    public Label datedeff;
    public Label rentidlable;
    public Label fromlable;
    public Label tolable;
    public Label custidlable;
    public Label advancedlable;
    public Label refundablelable;
    public Label ppdlable;
    public Label totallable;
    public Label balancelable;
    public TextField extratext;
    public Label caridlable;
    public Label brandlable;
    public Label modellable;
    public Label vehicalnumberlable;
    public Label cartypelable;
    public TextField rentsearch;
    public Label extralabe;
    public AnchorPane rentcaranchor;
    @FXML
    private TableColumn<?, ?> Advancedcol;

    @FXML
    private TableColumn<?, ?> Balancecol;

    @FXML
    private TableView<Rent> CarRentTable;

    @FXML
    private TableColumn<?, ?> CustIDcol;

    @FXML
    private TableColumn<?, ?> Extracol;

    @FXML
    private TableColumn<?, ?> Fromcol;

    @FXML
    private TableColumn<?, ?> PDRentcol;

    @FXML
    private TableColumn<?, ?> Refundcol;

    @FXML
    private TableColumn<?, ?> RentIdCol;

    @FXML
    private TableColumn<?, ?> Returncol;

    @FXML
    private TableColumn<?, ?> Tocol;

    @FXML
    private TableColumn<?, ?> Totalcol;

    public  void initialize() throws SQLException {
        setCellValueFactory();
        loadthable();
    }

    private void loadthable() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM rent";

        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery(sql);

        List<Rent> rentList = new ArrayList<>();

        while(resultSet.next()){
            String rentid = resultSet.getString(1);
            String from = resultSet.getString(2);
            String to = resultSet.getString(3);
            String advanced = resultSet.getString(4);
            String refundable = resultSet.getString(5);
            String isreturn = resultSet.getString(6);
            String pdrent = resultSet.getString(7);
            String total = resultSet.getString(8);
            String balance = resultSet.getString(9);
            String custid = resultSet.getString(10);
            String carid = resultSet.getString(11);
            String extra = resultSet.getString(12);

            var rent = new Rent(rentid,from,to,advanced,refundable,isreturn,pdrent,total,balance,custid,carid,extra);
            rentList.add(rent);
        }
        setTableData(rentList);
    }

    private void setTableData(List<Rent> rentList) {
        String a ="No";
        ObservableList<Rent> oblist = FXCollections.observableArrayList();

        for (Rent rent:rentList) {
            if(a.equals(rent.getIsreturn())){
                var tm = new Rent(rent.getRentid(), rent.getFrom(), rent.getTo(), rent.getAdvanced(), rent.getRefundable(), rent.getIsreturn(), rent.getPerdayrent(), rent.getTotal(), rent.getBalance(), rent.getCustid(), rent.getCarid(), rent.getExtra());
                oblist.add(tm);
            }
        }
        CarRentTable.setItems(oblist);
    }

    private void setCellValueFactory() {
        RentIdCol.setCellValueFactory(new PropertyValueFactory<>("rentid"));
        Fromcol.setCellValueFactory(new PropertyValueFactory<>("from"));
        Tocol.setCellValueFactory(new PropertyValueFactory<>("to"));
        Advancedcol.setCellValueFactory(new PropertyValueFactory<>("advanced"));
        Refundcol.setCellValueFactory(new PropertyValueFactory<>("refundable"));
        Returncol.setCellValueFactory(new PropertyValueFactory<>("isreturn"));
        PDRentcol.setCellValueFactory(new PropertyValueFactory<>("perdayrent"));
        Totalcol.setCellValueFactory(new PropertyValueFactory<>("total"));
        Balancecol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        CustIDcol.setCellValueFactory(new PropertyValueFactory<>("custid"));
        Extracol.setCellValueFactory(new PropertyValueFactory<>("extra"));
    }

    public void TableAction(MouseEvent mouseEvent) throws ParseException {
        datedeff.setText("");
        Rent get = CarRentTable.getSelectionModel().getSelectedItem();
        data(get);
    }

    public void carData(String carid){
        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM car WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,carid);

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
                new Alert(Alert.AlertType.WARNING,"Car Data not Found");
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }

    }
    private void setFields(ManageCar manageCar) {
        this.caridlable.setText(manageCar.getIdCar());
        this.brandlable.setText(manageCar.getBrand());
        this.modellable.setText(manageCar.getModel());
        this.vehicalnumberlable.setText(manageCar.getVehicle_Number());
        this.cartypelable.setText(manageCar.getCategory_id());
    }

    public void data(Rent getn) throws ParseException {
        Rent get = getn;

        carData(get.getCarid());

        Date d1 =new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(LocalDate.now()));
        Date d2 =new SimpleDateFormat("yyyy-MM-dd").parse(get.getTo());

        long diff = TimeUnit.MILLISECONDS.toDays(d1.getTime()- d2.getTime()) % 365;
        if(diff>0) {
            datedeff.setText(diff+"Day(s) Late");
        }
        rentidlable.setText(get.getRentid());
        custidlable.setText(get.getCustid());
        fromlable.setText(get.getFrom());
        tolable.setText(get.getTo());
        advancedlable.setText(get.getAdvanced());
        refundablelable.setText(get.getRefundable());
        ppdlable.setText(get.getPerdayrent());
        extratext.setText(get.getExtra());
        extralabe.setText(get.getExtra());
        totallable.setText(get.getTotal());
        balancelable.setText(get.getBalance());
    }

    public void ExtraAddBtnAction(ActionEvent actionEvent) throws ParseException {

        String id = rentidlable.getText();
        Double extra = Double.parseDouble(extratext.getText());
        Double bal = extra+(Double.parseDouble(balancelable.getText())-Double.parseDouble(extralabe.getText()));
        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE rent SET  Balance=?, Extra=? WHERE idRent=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDouble(1,bal);
            pstm.setDouble(2,extra);
            pstm.setString(3,id);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                initialize();
                balancelable.setText(""+bal);
                extralabe.setText(extratext.getText());
                new Alert(Alert.AlertType.CONFIRMATION, "Extra Add !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void ReturnBtnAction(ActionEvent actionEvent) {
        String id = rentidlable.getText();
        String rtrn ="Yes";

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE rent SET is_retern=? WHERE idRent=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,rtrn);
            pstm.setString(2,id);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                setAvailable();
                clear();
                new Alert(Alert.AlertType.CONFIRMATION, "go to setAvailable");
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void clear(){
        rentidlable.setText("");
        custidlable.setText("");
        fromlable.setText("");
        tolable.setText("");
        advancedlable.setText("");
        refundablelable.setText("");
        ppdlable.setText("");
        extratext.setText("");
        extralabe.setText("");
        totallable.setText("");
        balancelable.setText("");
        datedeff.setText("");
        caridlable.setText("");
        brandlable.setText("");
        modellable.setText("");
        vehicalnumberlable.setText("");
        cartypelable.setText("");
    }

    private void setAvailable(){
        String id = caridlable.getText();
        String vably ="Available";

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE car SET availability=? WHERE idCar=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,vably);
            pstm.setString(2,id);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Returned Successful !");
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void buttonSerch(ActionEvent actionEvent) throws ParseException {
        String id = rentsearch.getText();
        String rtrn ="No";

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM rent WHERE idRent=? AND is_retern=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.setString(2,rtrn);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                String idRent = resultSet.getString(1);
                String from_date = resultSet.getString(2);
                String to_date = resultSet.getString(3);
                String Advanced_payment = resultSet.getString(4);
                String refundable_payment = resultSet.getString(5);
                String is_retern = resultSet.getString(6);
                String perDay_rent = resultSet.getString(7);
                String Total = resultSet.getString(8);
                String Balance = resultSet.getString(9);
                String cust_id = resultSet.getString(10);
                String car_id = resultSet.getString(11);
                String Extra = resultSet.getString(12);

                datedeff.setText("");

                var rent = new Rent(idRent,from_date,to_date,Advanced_payment,refundable_payment,is_retern,perDay_rent,Total,Balance,cust_id,car_id,Extra);
                data(rent);

                rentsearch.setText("");
            }else{
                new Alert(Alert.AlertType.WARNING,"Rent ID not Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnBackToLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Rent_form.fxml"));

        this.rentcaranchor.getChildren().clear();
        this.rentcaranchor.getChildren().add(root);
    }
}
