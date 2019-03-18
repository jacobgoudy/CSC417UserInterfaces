package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class Student extends Model {
  
    public static final String TABLE = "student";
    
    private int id = 0;
    private String name;
    private Date enrolled;
  
    // Default constructor
    Student() {
    }
  
    public Student(String name, Date enrolled) {
        this.name = name;
        this.enrolled = enrolled;
    }
  
    // Getters
    @Override
    public int getId() { 
        return id;
    }
  
    public String getName() {
        return name;
    }
  
    public Date getEnrolled() {
        return enrolled;
    }
    
    public String getTutoredIn() {
        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                String.format("where student_id=%s", id));
        
        String subjects = "";
        
        for(Interaction interaction : interactions) {
            Tutor tutor = ORM.findOne(Tutor.class, "where id=?", 
                    new Object[]{interaction.getTutorId()});
            subjects = subjects + tutor.getSubject() + " ";
        }
        
        return subjects;
    }
  
    // Setters
    public void setName(String name) {
        this.name = name;
    }
  
    public void setEnrolled(Date enrolled) {
        this.enrolled = enrolled;
    }
  
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            name = rs.getString("name");
            enrolled = rs.getDate("enrolled");
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
  
    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                "insert into %s (name,enrolled) values (?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setDate(++i, enrolled);
            st.executeUpdate();
            id = ORM.getMaxId(TABLE);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
  
    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                "update %s set name=?,enrolled=? where id=?", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setDate(++i, enrolled);
            st.setInt(++i, id);
            st.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", id, name, enrolled);
    }
}
