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
import lk.ijse.CarHire.dto.Register;
import lk.ijse.CarHire.dto.tm.CategoriesTm;
import lk.ijse.CarHire.dto.tm.RegisterTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesFormController {
    public TextField categoryIdtxt;
    public TextField categoryNametxt;
    public TextField serchcatogey;


    @FXML
    private TableColumn<?, ?> CatIdColmn;

    @FXML
    private TableColumn<?, ?> CatNameColmn;

    @FXML
    private TableView<CategoriesTm> Catogarytable;

    public  void initialize() throws SQLException {
        setCellValueFactory();
        List<Categories> categoriesList = loadtable();

        setTableData(categoriesList);
    }

    private void setTableData(List<Categories> categoriesList) {
        ObservableList<CategoriesTm> oblist = FXCollections.observableArrayList();

        for (Categories categories:categoriesList) {
            var tm = new CategoriesTm(categories.getCatid(), categories.getCatname());
            oblist.add(tm);
        }
        Catogarytable.setItems(oblist);
    }

    private void setCellValueFactory() {
        CatIdColmn.setCellValueFactory(new PropertyValueFactory<>("catid"));
        CatNameColmn.setCellValueFactory(new PropertyValueFactory<>("catname"));

    }

    private List<Categories> loadtable() throws SQLException {

        Connection con = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM car_category";
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery(sql);

        List<Categories> categoriesList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);

            var categories = new Categories(id,name);
            categoriesList.add(categories);
        }
        return categoriesList;
    }
    public void categorySearchOnAction(ActionEvent actionEvent) {
        SeachbyAll();
    }

    public void SeachbyAll(){
        String id = serchcatogey.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM car_category WHERE idCar_Category=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            ResultSet resultSet =pstm.executeQuery();

            if(resultSet.next()){
                String categoryIdtxt = resultSet.getString(1);
                String categoryNametxt = resultSet.getString(2);

                var categories = new Categories(categoryIdtxt,categoryNametxt);

                setFields(categories);

            }else{
                new Alert(Alert.AlertType.WARNING,"Category not Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(Categories categories) {
        this.categoryIdtxt.setText(categories.getCatid());
        this.categoryNametxt.setText(categories.getCatname());
    }

    public void btnCatagorySave(ActionEvent actionEvent) {
        String catid = categoryIdtxt.getText();
        String catname = categoryNametxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO car_category VALUES(?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, catid);
            pstm.setString(2, catname);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "New Car Category Added !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear(){
        categoryIdtxt.setText("");
        categoryNametxt.setText("");
        serchcatogey.setText("");
    }

    public void btnClear(ActionEvent actionEvent) {
        clear();
    }

    public void btnCategoryUpdate(ActionEvent actionEvent) {
        String catid = categoryIdtxt.getText();
        String catname = categoryNametxt.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="UPDATE car_category SET  name=? WHERE idCar_Category=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,catname);
            pstm.setString(2,catid);

            boolean isUpdate = pstm.executeUpdate() >0;
            if(isUpdate){
                clear();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Category Updated!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String id = serchcatogey.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql ="DELETE FROM car_category WHERE idCar_Category=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            boolean isDeleted = pstm.executeUpdate() >0;
            if(isDeleted){
                clear();
                initialize();
                new Alert(Alert.AlertType.WARNING, "Category Deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void buttonSerch(ActionEvent actionEvent) {
        SeachbyAll();
    }
}
