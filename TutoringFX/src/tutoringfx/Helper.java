package tutoringfx;
 
import models.Interaction;
import models.Student;
import models.Tutor;
 
public class Helper {
    public static String info(Student student) {
        return String.format(
            "id: %s\n"
            + "name: %s\n"
            + "enrolled: %s\n",
            student.getId(),
            student.getName(),
            student.getEnrolled()
        );
    }
 
    public static String info(Tutor tutor) {
        return String.format(
            "id: %s\n"
            + "name: %s\n"
            + "email: %s\n"
            + "subject: %s\n",
            tutor.getId(),
            tutor.getName(),
            tutor.getEmail(),
            tutor.getSubject().getName()
        );
    }
    
    public static String info(Interaction interaction) {
        return interaction.getReport();
    }
}
