import com.company.*;
import com.company.Student;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

public class UnitTest_Student {
//        //Variables initialization and object instaniating
        Student ai = new Student();
        Connection con;

    //testing user input for grade course selection
    @Test
    public void testcase1_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.coursesOptions.setSelectedIndex(0);
        ai.refreshG.doClick();
        assertEquals("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.<br>" +
                " If you are not enrol in any course, please click ENROLL button to add course.</html>", ai.emptyRecord.getText());
    }
    //testing user input for attendance course selection
    @Test
    public void testcase2_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.courseOptionsAttendance.setSelectedIndex(0);
        ai.refreshA.doClick();
        assertEquals("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.</html>", ai.emptyAttendanceRecord.getText());
    }

    //testing new password and confirm password fields not matching error case
    @Test
    public void testcase3_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("newpasspass");
        ai.confirmpass.setText("aaaaaaaaaa");
        ai.newpass.setText("passpass");

        ai.b_confirm.doClick();
        assertEquals("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>"
                , ai.errorPass.getText());
    }

    //testing new password input less than 6 characters error case
    @Test
    public void testcase4_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("passpass");
        ai.confirmpass.setText("new");
        ai.newpass.setText("new");

        ai.b_confirm.doClick();
        assertEquals("<html><font color='Red'>Password should be at least 6 characters! </font></html>"
                , ai.errorPass.getText());
    }

    //testing current password input less than 6 characters error case
    @Test
    public void testcase5_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("new");
        ai.confirmpass.setText("passpass");
        ai.newpass.setText("passpass");

        ai.b_confirm.doClick();
        assertEquals("<html><font color='Red'>Password should be at least 6 characters! </font></html>"
                , ai.errorPass.getText());
    }


    //testing current password input does not match password stored in database
    @Test
    public void testcase6_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("notOldPass");
        ai.confirmpass.setText("passpass");
        ai.newpass.setText("passpass");
        ai.b_confirm.doClick();
        assertFalse(ai.isEnteredPassCorrect);
    }

    //testing current password input matches password stored in database
    @Test
    public void testcase7_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("newpass");

        ai.confirmpass.setText("newpass");
        ai.newpass.setText("newpass");
        ai.b_confirm.doClick();
        assertTrue(ai.isEnteredPassCorrect);
    }

    //testing cGPA calculation and display
    @Test
    public void testcase8_Student()
    {
        ai.DisplayUserGUI("testuser4");
        ai.setVisible(false);
        String TEST_cGPA_s = "";

        ai.overallG.doClick();

        try{
            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            PreparedStatement statement = con.prepareStatement("SELECT count(T.Course_ID) as numCourses, sum(T.Grade) as gradeSum FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.T.Grade is not Null");

            statement.setString(1, "2");
            ResultSet rs = statement.executeQuery();
            //Getting data out from the query
            Float TEST_cGPA_f;
            Float numCourses;
            Float gradeSum;
            while (rs.next()) {
                numCourses = Float.parseFloat(rs.getString("numCourses"));
                gradeSum = Float.parseFloat(rs.getString("gradeSum"));

                TEST_cGPA_f = 4.33f * (gradeSum/numCourses)/100;
                TEST_cGPA_s = String.format("%.2f", TEST_cGPA_f);
            }
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        assertEquals("Your Cumulative (Overall) GPA is: " + TEST_cGPA_s + "/4.33", ai.overallGPA.getText());
        System.out.println(TEST_cGPA_s);
    }


}
