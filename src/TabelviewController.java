/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Educ
 */
public class TabelviewController implements Initializable {

    @FXML
    private TextField txtFieldID;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldMajor;
    @FXML
    private TextField txtFieldGrade;
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<modeltable, Integer> tcID;
    @FXML
    private TableColumn<modeltable, String> tcName;
    @FXML
    private TableColumn<modeltable, String> tcmajor;
    @FXML
    private TableColumn<modeltable, Double> tcgrade;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonReset;
    Statement statement;
    @FXML
    private Button showquary;
    @FXML
    private Button buttonsho;
    @FXML
    private ComboBox combobox;

    /**
     * Initializes the controller class.
     */
    //Q1)  Develop a data manipulation application :
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection
                    = DriverManager.getConnection("jdbc:mysql://localhost/unvarsity?serverTimezone=UTC", "root", "");
            this.statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tcID.setCellFactory(new PropertyValueFactory("id"));
        tcName.setCellFactory(new PropertyValueFactory("name"));
        tcgrade.setCellFactory(new PropertyValueFactory("grade"));
        tcmajor.setCellFactory(new PropertyValueFactory("major"));
        tableView.getSelectionModel().selectedItemProperty().addListener(event -> showselectedstudent());
    }

    @FXML
    private void buttondshowHandle(ActionEvent event) throws SQLException {
        ResultSet rs = this.statement.executeQuery("Select * From student");
        tableView.getItems().clear();
        while (rs.next()) {
            modeltable modeltable = new modeltable();
            modeltable.setId(rs.getInt("id"));
            modeltable.setName(rs.getString("name"));
            modeltable.setGrade(rs.getDouble("grade"));
            modeltable.setMajor(rs.getString("major"));
            //tableView.getItems().add(modeltable);
        }
    }

    private void buttonAddHandle(ActionEvent event) throws SQLException {
        Integer id = Integer.parseInt(txtFieldID.getText());
        String name = txtFieldName.getText();
        String major = txtFieldMajor.getText();
        double grade = Double.parseDouble(txtFieldGrade.getText());
        String sql = "Insert Into student(" + id + ",'" + name + "','" + major + "'," + grade + ")";
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void buttonUpdateHandle(ActionEvent event) throws SQLException {
        Integer id = Integer.parseInt(txtFieldID.getText());
        String name = txtFieldName.getText();
        String major = txtFieldMajor.getText();
        double grade = Double.parseDouble(txtFieldGrade.getText());
        String sqll = "Update student Set name = '" + name + "',major" + major + "',grade" + grade + "Where id= " + id;
        this.statement.executeUpdate(sqll);
    }

    @FXML
    private void buttonDeleteHandle(ActionEvent event) throws SQLException {
        Integer id = Integer.parseInt(txtFieldID.getText());
        String name = txtFieldName.getText();
        String major = txtFieldMajor.getText();
        double grade = Double.parseDouble(txtFieldGrade.getText());
        String sql = "Delete From student Where id = " + id + "',name" + name + "',grade" + grade + "',major" + major + "";
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void buttonResetHandle(ActionEvent event) {
        resetcontrols();
    }

    private void resetcontrols() {
        txtFieldID.setText("");
        txtFieldName.setText("");
        txtFieldMajor.setText("");
        txtFieldGrade.setText("");
        tableView.getItems().clear();
    }

    private void showselectedstudent() {
        modeltable modeltable = (modeltable) tableView.getSelectionModel().getSelectedItem();
        if (modeltable != null) {
            txtFieldID.setText(String.valueOf(modeltable.getId()));
            txtFieldName.setText(modeltable.getName());
            txtFieldMajor.setText(modeltable.getMajor());
            txtFieldGrade.setText(String.valueOf(modeltable.getGrade()));
            tableView.getItems().clear();
        }
    }
    ObservableList<String> list = FXCollections.observableArrayList(
            "Select all students studying 'Software Engineering' major",
             "Select students who have excellent grades",
             "Select all pass students in ascending order based on their names.",
             "For all students studying 'Computer Science' major and have grade less than 70%, increase their grades by 3 "
    );

    //Q2) Upgrade the application in Q1 :
    @FXML
    private void buttonshowquaryHandle(ActionEvent event) throws SQLException {
        String select = (String) combobox.getSelectionModel().getSelectedItem();
        switch (select) {

            case "Select all students studying 'Software Engineering' major":
                String qu = "SELECT * FROM `student` WHERE major = 'Software Engineering'";
                ResultSet rsd = this.statement.executeQuery(qu);
                while (rsd.next()) {
                    modeltable modeltable = new modeltable();
                    modeltable.setId(rsd.getInt("id"));
                    modeltable.setName(rsd.getString("name"));
                    modeltable.setGrade(rsd.getDouble("grade"));
                    modeltable.setMajor(rsd.getString("major"));
                    //tableView.getItems().add(modeltable);
                      this.statement.executeUpdate(qu);
                }
                break;

            case "Select students who have excellent grades":
                String qua = "SELECT * FROM student WHERE grade >= 90";
                 ResultSet rss = this.statement.executeQuery(qua);
                while (rss.next()) {
                    modeltable modeltable = new modeltable();
                    modeltable.setId(rss.getInt("id"));
                    modeltable.setName(rss.getString("name"));
                    modeltable.setGrade(rss.getDouble("grade"));
                    modeltable.setMajor(rss.getString("major"));
                   // tableView.getItems().add(modeltable);
                    this.statement.executeUpdate(qua);
                    
                }
                break;

            case "Select all pass students in ascending order based on their names.":
                String quar = "SELECT * FROM `student` WHERE grade >=60 ORDER BY name ASC ";
                 ResultSet rsa = this.statement.executeQuery(quar);
                while (rsa.next()) {
                    modeltable modeltable = new modeltable();
                    modeltable.setId(rsa.getInt("id"));
                    modeltable.setName(rsa.getString("name"));
                    modeltable.setGrade(rsa.getDouble("grade"));
                    modeltable.setMajor(rsa.getString("major"));
                   // tableView.getItems().add(modeltable);
                    this.statement.executeUpdate(quar);
                }
                break;

            case "For all students studying 'Computer Science' major and have grade less than 70%, increase their grades by 3 ":
                String quary = "SELECT * , IF(major = 'Computer Science' and grade <=70 , grade+3 , 'Not matching') as increase FROM `student`;";
                 ResultSet rsr = this.statement.executeQuery(quary);
                while (rsr.next()) {
                    modeltable modeltable = new modeltable();
                    modeltable.setId(rsr.getInt("id"));
                    modeltable.setName(rsr.getString("name"));
                    modeltable.setGrade(rsr.getDouble("grade"));
                    modeltable.setMajor(rsr.getString("major"));
                    //tableView.getItems().add(modeltable);
                  this.statement.executeUpdate(quary);
                }
                break;

        }
    }
}
