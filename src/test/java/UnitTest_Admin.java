package com.company;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class UnitTest_Admin {
//        //Variables initialization and object instaniating
        Admin ai = new Admin();
        Connection con;

    //testing new password and confirm password fields not matching error case
    @Test
    public void testcase1_Admin()
    {
        ai.DisplayUserGUI("Admin");
        ai.setVisible(false);
        ai.updatePass.doClick();
        ai.inputCurr.setText("inputNewPasspass");
        ai.inputConfirmPass.setText("aaaaaaaaaa");
        ai.inputNewPass.setText("passpass");

        ai.changePass.doClick();
        assertEquals("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>"
                , ai.errorPass.getText());
    }

    //testing new password input less than 6 characters error case
    @Test
    public void testcase2_Admin()
    {
        ai.DisplayUserGUI("Admin");
        ai.setVisible(false);

        ai.updatePass.doClick();
        ai.inputCurr.setText("passpass");
        ai.inputConfirmPass.setText("new");
        ai.inputNewPass.setText("new");

        ai.changePass.doClick();
        assertEquals("<html><font color='Red'>Password should be at least 6 characters! </font></html>"
                , ai.errorPass.getText());
    }

    //testing current password input less than 6 characters error case
    @Test
    public void testcase3_Admin()
    {
        ai.DisplayUserGUI("Admin");
        ai.setVisible(false);

        ai.updatePass.doClick();
        ai.inputCurr.setText("new");
        ai.inputConfirmPass.setText("passpass");
        ai.inputNewPass.setText("passpass");

        ai.changePass.doClick();
        assertEquals("<html><font color='Red'>Password should be at least 6 characters! </font></html>"
                , ai.errorPass.getText());
    }


    //testing current password input does not match password stored in database
    @Test
    public void testcase4_Admin()
    {
        ai.DisplayUserGUI("Admin");
        ai.setVisible(false);

        ai.updatePass.doClick();
        ai.inputCurr.setText("notOldPass");
        ai.inputConfirmPass.setText("passpass");
        ai.inputNewPass.setText("passpass");
        ai.changePass.doClick();
        assertFalse(ai.isEnteredPassCorrect);
    }

    //testing current password input matches password stored in database
    @Test
    public void testcase5_Admin()
    {
        ai.DisplayUserGUI("Admin");
        ai.setVisible(false);

        ai.updatePass.doClick();
        ai.inputCurr.setText("rootusers");

        ai.inputConfirmPass.setText("rootusers");
        ai.inputNewPass.setText("rootusers");
        ai.changePass.doClick();
        assertTrue(ai.isEnteredPassCorrect);
    }

}
