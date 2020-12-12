import com.company.AccountGUI;
import com.company.AssignCourse;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AssignCourse_Test {
    //Testing for assigning course to mark111
    @Test
    public void testcase1_AssignCourse()
    {
        AssignCourse ai = new AssignCourse(1);

        ai.setVisible(false);

        String foundName = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ai.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            PreparedStatement statement = ai.con.prepareStatement("SELECT Course_ID, Course_Name FROM SMSSytem.Course where (Teacher_ID = 1)");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                foundName = rs.getString("Course_ID");
            }
        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        ai.courseSearch.setText("MATH 101");
        ai.selectedCourse = "MATH 101";

        ai.enroll.doClick();

        //Assign COurse correctly
        assertEquals("MATH 101", foundName);

    }
}
