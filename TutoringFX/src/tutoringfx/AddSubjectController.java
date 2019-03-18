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

/**
 * AddSubjectController
 * 
 * Controller for the dialog window that allows a user to add a new subject to 
 * the list. Tests the subject name against existing subjects to prevent
 * duplicates.
 *
 * @author Jacob
 */
public class AddSubjectController implements Initializable {

    private TutoringController mainController;
 
    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private TextArea subjectArea;
    
    @FXML
    private TextField subjectField;
    
    @FXML
    private void add(Event event) {
        try {
            String subjectName = subjectField.getText().trim();
            
            if(subjectName.compareTo("") == 0) {
                throw new ExpectedException("Fill in all fields!");
                
                
            }
            
            Subject sameSubject = ORM.findOne(Subject.class,
                    "where name=?", new Object[]{subjectName});
            if(sameSubject != null) {
                throw new ExpectedException("This name already exists!");
            }
            
            
            Subject newSubject = new Subject(subjectName);
            ORM.store(newSubject);
            
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
        String subjectNames = "";
        
        Collection<Subject> subjects = ORM.findAll(Subject.class);
        for(Subject subject : subjects) {
            subjectNames = subjectNames + subject.getName() + "\n";
        }
        
        subjectArea.setText(subjectNames);
    }
}
