/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoringfx;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Tutor;

/**
 * EditInteractionController
 * 
 * Dialog window to enter new information into the interaction report.
 * Was not able to pull information form student or tutor objects from the 
 * tutoring controller.
 *
 * @author Jacob
 */
public class EditInteractionController implements Initializable {
    
    private TutoringController mainController;
 
    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private TextArea reportArea;
    
    @FXML
    private Label tutorLabel;
    
    @FXML
    private Label studentLabel;
    
    @FXML
    private Label subjectLabel;
    
    private boolean edited = false;
    
    private Student student;
    private Tutor tutor;
    
    void setStudent(Student s) {
        student = s;
    }
    
    void setTutor(Tutor t) {
        tutor = t;
    }
    
    @FXML
    private void keyEvent(Event event) {
        edited = true;
    }
    
    @FXML
    private void edit(Event event) {
        Interaction interaction = ORM.findOne(Interaction.class,
                "where tutor_id=? and student_id=?",
                new Object[]{tutor.getId(), student.getId()});
            
        interaction.setReport(reportArea.getText());
            
        ORM.store(interaction);
        
        mainController.setDisplay(interaction.getReport());
    }
    
    @FXML
    private void cancel(Event event) {
        if(edited) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to close without saving?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                event.consume();
                ((Button)event.getSource()).getScene().getWindow().hide();
            }
        }
        else {
            ((Button)event.getSource()).getScene().getWindow().hide();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println(student);
            Interaction interaction = ORM.findOne(Interaction.class,
                "where tutor_id=? and student_id=?",
                new Object[]{tutor.getId(), student.getId()});
            
            studentLabel.setText(student.getName());
            tutorLabel.setText(tutor.getName());
            subjectLabel.setText(tutor.getSubject().getName());
            reportArea.setText(interaction.getReport());
        }
        catch(NullPointerException ex) {
            System.out.println("whoops");
        }
    }    
    
}
