import com.company.Gui;
import com.company.SearchGUI;
import com.company.Teacher;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SearchGUI_Test {
    //Testing for wrong grade change by Teacher (Range: 0-100)
    @Test
    public void testcase1_SearchGUI() {
        SearchGUI s = new SearchGUI("MATH 101","mark111","GR");

        s.setVisible(false);

        s.tf_gr.setText("115");

        s.select.doClick();

        //for assertEquals
        //errorGrade.setText("<html><font color = 'red'>Error!! Input Grade Out of Range. Please Type in Value between 0 and 100. </font></html>");
        assertEquals("<html><font color = 'red'>Error!! Input Grade Out of Range. Please Type in Value between 0 and 100. </font></html>", s.errorGrade.getText());

    }

    //Testing for correct grade change by Teacher (Range: 0-100)
    @Test
    public void testcase2_SearchGUI() {
        SearchGUI s = new SearchGUI("MATH 101","mark111","GR");

        s.setVisible(false);

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            s.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        s.tf_gr.setText("60");

        s.select.doClick();

        //for assertEquals
        //errorGrade.setText("<html><font color = 'red'>Error!! Input Grade Out of Range. Please Type in Value between 0 and 100. </font></html>");
        assertTrue(s.isGradeChanged());

    }

    //Testing for wrong attendance change by Teacher (Range: 0-365)
    @Test
    public void testcase3_SearchGUI() {
        SearchGUI s = new SearchGUI("MATH 101","mark111","AT");

        s.setVisible(false);

        s.tf_att.setText("400");

        s.select.doClick();

        //for assertEquals
        //errorAttendance.setText("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>");
        assertEquals("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>", s.errorAttendance.getText());

    }

    //Testing for correct attendance change by Teacher (Range: 0-365)
    @Test
    public void testcase4_SearchGUI() {
        SearchGUI s = new SearchGUI("MATH 101","mark111","AT");

        s.setVisible(false);

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            s.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        s.tf_att.setText("300");

        s.select.doClick();

        //for assertEquals
        //errorAttendance.setText("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>");
        assertTrue(s.isAttendanceChanged());

    }

    //Testing for wrong grade and attendance change by Admin (Range: 0-365)
    @Test
    public void testcase5_SearchGUI() {
        SearchGUI s = new SearchGUI("ALL","Admin","BOTH");

        s.setVisible(false);

        s.tf_gr.setText("105");

        s.tf_att.setText("400");

        s.select.doClick();

        //for assertEquals
        //errorAttendance.setText("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>");
        assertEquals("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>", s.errorAttendance.getText());
        assertEquals("<html><font color = 'red'>Error!! Input Grade Out of Range. Please Type in Value between 0 and 100. </font></html>", s.errorGrade.getText());

    }

    //Testing for correct grade and attendance change by Admin (Range: 0-365)
    @Test
    public void testcase6_SearchGUI() {
        SearchGUI s = new SearchGUI("ALL","Admin","BOTH");

        s.setVisible(false);

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            s.con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        s.tf_gr.setText("65");

        s.tf_att.setText("300");

        s.select.doClick();

        //for assertEquals
        //errorAttendance.setText("<html><font color = 'red'>Error!! Input Attendance Out of Range. Please Type in Value between 0 and 365. </font></html>");
        assertTrue(s.isGradeChanged());
        assertTrue(s.isAttendanceChanged());

    }





}
