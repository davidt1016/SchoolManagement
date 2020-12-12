import com.company.*;

import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class UnitTest_Teacher {
//        //Variables initialization and object instaniating
        Teacher ai = new Teacher();
        Connection con;

    //testing user input for grade course selection
    @Test
    public void testcase1_Teacher()
    {
        ai.DisplayUserGUI("mark111");
        ai.setVisible(false);

        ai.coursesOptions.setSelectedIndex(0);
        ai.edit_grade.doClick();
        assertEquals("Please Select a Course First", ai.emptyRecord.getText());
    }
    //testing user input for attendance course selection
    @Test
    public void testcase2_Teacher()
    {
        ai.DisplayUserGUI("mark111");
        ai.setVisible(false);

        ai.courseOptionsAttendance.setSelectedIndex(0);
        ai.edit_attendance.doClick();
        assertEquals("Please Select a Course First", ai.emptyAttendanceRecord.getText());
    }

    //testing new password and confirm password fields not matching error case
    @Test
    public void testcase3_Teacher()
    {
        ai.DisplayUserGUI("mark111");
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
    public void testcase4_Teacher()
    {
        ai.DisplayUserGUI("mark111");
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
    public void testcase5_Teacher()
    {
        ai.DisplayUserGUI("mark111");
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
    public void testcase6_Teacher()
    {
        ai.DisplayUserGUI("mark111");
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
    public void testcase7_Teacher()
    {
        ai.DisplayUserGUI("mark111");
        ai.setVisible(false);

        ai.b_updatepass.doClick();
        ai.currentpass.setText("PASSPASS");

        ai.confirmpass.setText("PASSPASS");
        ai.newpass.setText("PASSPASS");
        ai.b_confirm.doClick();
        assertTrue(ai.isEnteredPassCorrect);
    }

}
