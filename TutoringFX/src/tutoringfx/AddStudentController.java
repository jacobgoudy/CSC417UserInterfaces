/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoringfx;

import java.net.URL;
import java.sql.Date;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Student;

/**
 * AddStudentController
 * 
 * Controller for the dialog window to add a student. First and last name are
 * input and a new student is created with the current date as the enrolled date.
 *
 * @author Jacob
 */
public class AddStudentController implements Initializable {
    
    private TutoringController mainController;
 
    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    
    // Used for getting the enrolled date.
    public static java.sql.Date currentDate() {
        long now = new java.util.Date().getTime();
        java.sql.Date date = new java.sql.Date(now);
        return date;
    }
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private void add(Event event) {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            
            if(firstName == "" || lastName == "") {
                throw new ExpectedException("Fill in both names!");
            }
            
            String name = lastName + "," + firstName;
            Student sameStudent = ORM.findOne(Student.class,
                    "where name=?", new Object[]{name});
            
            if(sameStudent != null) {
                throw new ExpectedException("This name already exists!");
            }
            
            Date enrolled = currentDate();
            
            Student newStudent = new Student(name, enrolled);
            ORM.store(newStudent);
            
            ListView<Student> studentList = mainController.getStudentList();
            TextArea display = mainController.getDisplay();
            
            studentList.getItems().clear();
            Collection<Student> students = ORM.findAll(Student.class);
            for(Student student : students) {
                studentList.getItems().add(student);
            }
            
            studentList.getSelectionModel().select(newStudent);
            studentList.scrollTo(newStudent);
            studentList.requestFocus();
            mainController.setLastFocused(studentList);
            
            display.setText(Helper.info(newStudent));
            
            ((Button) event.getSource()).getScene().getWindow().hide();
        }
        catch(ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    
    @FXML
    private void cancel(Event event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing to do
    }   
    
}
