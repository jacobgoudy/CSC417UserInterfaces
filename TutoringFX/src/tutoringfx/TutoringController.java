/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoringfx;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.DBProps;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Tutor;

/**
 * TutoringController
 * 
 * Controller for the main window in the TutoringFX program.  Contains all
 * controls and event actions for the program.
 *
 * @author Jacob Goudy
 */
public class TutoringController implements Initializable {
    
    @FXML
    private ListView<Student> studentList;
    
    @FXML
    private ListView<Tutor> tutorList;
    
    @FXML
    private TextArea display;
    
    private Node lastFocused = null;
    
    private final Collection<Integer> studentInteractionIds = new HashSet<>();
    private final Collection<Integer> tutorInteractionIds = new HashSet<>();
    // Getters
    ListView<Student> getStudentList() {
        return studentList;
    }
 
    ListView<Tutor> getTutorList() {
        return tutorList;
    }
 
    TextArea getDisplay() {
        return display;
    }
    
    // Setters
    void setDisplay(String text) {
        display.setText(text);
    }
    
    // Handler methods==========================================================
    @FXML
    private void refocus(Event event) {
        if (lastFocused != null) {
            lastFocused.requestFocus();
        }
    }
    
    @FXML
    private void clear(Event event) {
        tutorList.getSelectionModel().clearSelection();
        studentList.getSelectionModel().clearSelection();
        studentInteractionIds.clear();
        tutorInteractionIds.clear();
        studentList.refresh();
        tutorList.refresh();
        display.setText("");
        lastFocused = null;
    }

    // Select methods for students and tutors
    @FXML
    private void studentSelect(Event event) {
        Student student = studentList.getSelectionModel().getSelectedItem();
        
        if(student == null) {
            refocus(event);
            return;
        }
        
        lastFocused = studentList;
        
        // finds all interactions with this student
        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                "where student_id=?", new Object[]{student.getId()});
        
        studentInteractionIds.clear();
        for(Interaction interaction : interactions) {
            studentInteractionIds.add(interaction.getTutorId());
        }
        
        tutorList.refresh();
        
        display.setText(Helper.info(student));
    }
    
    @FXML
    private void tutorSelect(Event event) {
        Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
        
        if(tutor == null) {
            refocus(event);
            return;
        }
        
        lastFocused = studentList;
        
        // finds all interactions with this tutor
        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                "where tutor_id=?", new Object[]{tutor.getId()});
        
        tutorInteractionIds.clear();
        for(Interaction interaction : interactions) {
            tutorInteractionIds.add(interaction.getStudentId());
        }
        
        studentList.refresh();
        
        display.setText(Helper.info(tutor));
    }
    
    @FXML
    private void studentOrderName(Event event) {
        studentList.getItems().clear();
        
        Collection<Student> students = ORM.findAll(Student.class, 
                "order by name");
        for(Student student : students) {
            studentList.getItems().add(student);
        }
        
        studentList.refresh();
    }
    
    @FXML
    private void studentOrderDate(Event event) {
        studentList.getItems().clear();
        
        Collection<Student> students = ORM.findAll(Student.class, 
                "order by enrolled");
        for(Student student : students) {
            studentList.getItems().add(student);
        }
        
        studentList.refresh();
    }
    
    @FXML
    private void showInteraction(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
        
            lastFocused = display;
        
            if(tutor == null || student == null) {
                throw new ExpectedException("Must select a student and a tutor!");
            }
        
            Interaction interaction = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[] {tutor.getId(), student.getId()});
        
            if(interaction != null) {
                display.setText(Helper.info(interaction));
            }
            else {
                throw new ExpectedException("No interaction found!");
            }
        }
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        }
    }
    
    @FXML
    private void link(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            
            lastFocused = display;
        
            if(tutor == null || student == null) {
                throw new ExpectedException("Must select a tutor/student!");
            }
        
            Interaction interaction = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[] {tutor.getId(), student.getId()});
        
            if(interaction != null) {
                throw new ExpectedException("Tutor/student already linked!");
            }
            
            Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                    String.format("where student_id=%s", student.getId()));
            
            for(Interaction inter : interactions) {
                Tutor currentTutor = ORM.findOne(Tutor.class, 
                        "where id=?", new Object[]{inter.getTutorId()});
                if(currentTutor.getSubject() == tutor.getSubject()) {
                    throw new ExpectedException(String.format(
                            "%s is already has a %s tutor!",
                            student.getName(),
                            tutor.getSubject()));
                }
            }
            
            interaction = new Interaction(tutor.getId(), student.getId(),"EMPTY");
            
            ORM.store(interaction);
            ORM.store(tutor);
            
            tutorInteractionIds.add(student.getId());
            studentInteractionIds.add(tutor.getId());
            
            tutorList.refresh();
            studentList.refresh();
            
            display.setText(interaction.getReport());
        }
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        }
    }
    
    @FXML
    public void unlink(Event event) {
        try{
            Student student = studentList.getSelectionModel().getSelectedItem();
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            
            lastFocused = display;
        
            if(tutor == null || student == null) {
                throw new ExpectedException("Must select a tutor/student!");
            }
        
            Interaction interaction = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[] {tutor.getId(), student.getId()});
            
            if(interaction == null) {
                throw new ExpectedException("Tutor/student not linked!");
            }
            
            ORM.remove(interaction);
            ORM.store(tutor);
            
            tutorInteractionIds.remove(student.getId());
            studentInteractionIds.remove(tutor.getId());
            
            studentList.refresh();
            tutorList.refresh();
            
            if(lastFocused == display) {
                display.setText("");
            }
        }
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        }
    }
    
    @FXML
    public void removeStudent(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();
            
            if(student == null) {
                throw new ExpectedException("No student selected!");
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }
            
            Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                    "where student_id=?", new Object[]{student.getId()});
            for(Interaction interaction : interactions) {
                ORM.remove(interaction);
            }
            
            ORM.remove(student);
            
            studentList.getItems().remove(student);
            studentList.getSelectionModel().clearSelection();
            studentInteractionIds.clear();
            tutorList.refresh();
            
            if(lastFocused == studentList) {
                lastFocused = null;
                display.setText("");
            }
        }
        catch(ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        }
    }
    
    @FXML
    private void addStudent(Event event) {
        try {
            URL fxml = getClass().getResource("AddStudent.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();
            
            Scene scene = new Scene(fxmlLoader.getRoot());
            
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            
            dialogStage.setTitle("Add a Student");
            
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            dialogStage.show();
            
            AddStudentController dialogController = fxmlLoader.getController();
            
            dialogController.setMainController(this);
            
            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    @FXML
    private void addTutor(Event event) {
        try {
            URL fxml = getClass().getResource("AddTutor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();
            
            Scene scene = new Scene(fxmlLoader.getRoot());
            
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            
            dialogStage.setTitle("Add a Tutor");
            
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            dialogStage.show();
            
            AddTutorController dialogController = fxmlLoader.getController();
            
            dialogController.setMainController(this);
            
            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    @FXML
    private void addSubject(Event event) {
        try {
            URL fxml = getClass().getResource("AddSubject.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();
            
            Scene scene = new Scene(fxmlLoader.getRoot());
            
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            
            dialogStage.setTitle("Add a Subject");
            
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            dialogStage.show();
            
            AddSubjectController dialogController = fxmlLoader.getController();
            
            dialogController.setMainController(this);
            
            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    @FXML
    private void editInteraction(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            
            if(student == null || tutor == null) {
                throw new ExpectedException("Student/tutor not selected!");
            }
            
            URL fxml = getClass().getResource("EditInteraction.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();
            
            Scene scene = new Scene(fxmlLoader.getRoot());
            
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            
            dialogStage.setTitle("Add a Subject");
            
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            dialogStage.show();
            
            EditInteractionController dialogController = fxmlLoader.getController();
            
            dialogController.setStudent(student);
            dialogController.setTutor(tutor);
            
            dialogController.setMainController(this);
            
            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });
        }
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            refocus(event);
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    //==========================================================================
    
    void setLastFocused(Node lastFocused) {
        this.lastFocused = lastFocused;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ORM.init(DBProps.getProps());
        
        Collection<Student> students = ORM.findAll(Student.class, 
                "order by name");
        for (Student student : students) {
            studentList.getItems().add(student);
        }
        
        Collection<Tutor> tutors = ORM.findAll(Tutor.class, 
                "order by name");
        for (Tutor tutor : tutors) {
            tutorList.getItems().add(tutor);
        }
        
        StudentCellCallback studentCellCallback = new StudentCellCallback();
        studentList.setCellFactory(studentCellCallback);
        
        TutorCellCallback tutorCellCallback = new TutorCellCallback();
        tutorList.setCellFactory(tutorCellCallback);
        
        studentCellCallback.setHighlightedIds(tutorInteractionIds);
        tutorCellCallback.setHighlightedIds(studentInteractionIds);
    }    
}
