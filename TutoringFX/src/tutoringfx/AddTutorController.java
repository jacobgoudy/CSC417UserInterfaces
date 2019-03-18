/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoringfx;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Subject;
import models.Tutor;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * AddTutorController
 * 
 * Controller for the dialog window that allows users to add new tutors to the
 * list. 
 *
 * @author Jacob
 */
public class AddTutorController implements Initializable {

    private TutoringController mainController;
 
    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private ComboBox<Subject> subjectSelection;
    
    @FXML
    private void add(Event event) {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            Subject subject = subjectSelection.getSelectionModel().getSelectedItem();
            
            if(firstName == "" || lastName == "" || email == "" || subject == null) {
                throw new ExpectedException("Fill in all fields!");
            }
            
            String name = lastName + "," + firstName;
            Tutor sameTutor = ORM.findOne(Tutor.class,
                    "where name=?", new Object[]{name});
            
            if(sameTutor != null) {
                throw new ExpectedException("This name already exists!");
            }
            
            EmailValidator validator = EmailValidator.getInstance();
            boolean isValid = validator.isValid(email);
            
            if(!isValid) {
                throw new ExpectedException("Invalid email address!");
            }
            
            
            Tutor newTutor = new Tutor(name, email, subject);
            ORM.store(newTutor);
            
            ListView<Tutor> tutorList = mainController.getTutorList();
            TextArea display = mainController.getDisplay();
            
            tutorList.getItems().clear();
            Collection<Tutor> tutors = ORM.findAll(Tutor.class, "order by name");
            for(Tutor tutor : tutors) {
                tutorList.getItems().add(tutor);
            }
            
            tutorList.getSelectionModel().select(newTutor);
            tutorList.scrollTo(newTutor);
            tutorList.requestFocus();
            mainController.setLastFocused(tutorList);
            
            display.setText(Helper.info(newTutor));
            
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
        Collection<Subject> subjects = ORM.findAll(Subject.class);
        for(Subject subject : subjects) {
            subjectSelection.getItems().add(subject);
        }
    }
}
