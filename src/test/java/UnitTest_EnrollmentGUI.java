import com.company.*;

import org.junit.Test;

import java.sql.*;


import static org.junit.Assert.*;

public class UnitTest_EnrollmentGUI {
    //Variables initialization and object instaniating
    public Connection con;
    PreparedStatement pst3;



    //testing enrolling in a course already enrolled in
    @Test
    public void testcase1_EnrollmentGUI()
    {
        EnrollmentGUI ai = new EnrollmentGUI("testuser4", 2);
        ai.setVisible(false);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ai.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        ai.courseSearch.setText("GEOG 100");
        ai.selectedCourse = "GEOG 100";
        ai.enroll.doClick();
        assertEquals("Course Already Taken!", ai.confirmEnrollmentL.getText());
    }

    //testing enrolling in a course not already enrolled in
    @Test
    public void testcase2_EnrollmentGUI()
    {
        EnrollmentGUI ai = new EnrollmentGUI("testuser4", 2);
        ai.setVisible(false);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ai.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        ai.courseSearch.setText("CSCI 100");
        ai.selectedCourse = "CSCI 100";
        ai.enroll.doClick();
        assertTrue(ai.isAddCourse);
        try {
            pst3 = ai.con.prepareStatement("DELETE FROM `SMSSytem`.`Takes`WHERE(`Student_ID` = '2')and(`Course_ID` = 'CSCI 100')");
            pst3.executeUpdate();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }
    }

}
