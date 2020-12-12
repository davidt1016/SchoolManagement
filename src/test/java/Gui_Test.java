import com.company.AccountGUI;
import com.company.Admin;
import com.company.Gui;
import com.company.Student;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.sql.*;

import static org.junit.Assert.assertEquals;

public class Gui_Test {

    Gui g = new Gui();


    //Testing for wrong username input
    @Test
    public void testcase1_Gui() {
        //g.Gui();
        g.setVisible(false);

        //tf_user
        g.tf_user.setText("k111");

        //pass
        g.pass.setText("9999999999");

        g.b_login.doClick();

        //for assertEquals
        int flag = 0;
        if(g.err_user.isVisible()){
            flag = 1;
        }
        assertEquals(1,flag);

    }

    //Testing for wrong password input
    @Test
    public void testcase2_Gui() {
        //g.Gui();
        g.setVisible(false);

        //tf_user
        g.tf_user.setText("kevin111");

        //pass
        g.pass.setText("99999999");

        g.b_login.doClick();

        //for assertEquals
        int flag = 0;
        if(g.err_pass.isVisible()){
            flag = 1;
        }
        assertEquals(1,flag);

    }
    @Test
   public void testcase3_Gui()
   {
       //g.Gui();
       g.setVisible(false);

       //tf_user
       g.tf_user.setText("davidteng");

       //pass
       g.pass.setText("12345678");

       g.b_login.doClick();

       //for assertEquals
       int flag = 0;
       if(g.err_user.isVisible()){
           flag = 1;
       }
       //no error message for wrong username input
       assertEquals(0,flag);

       //for assertEquals
       int flag1 = 0;
       if(g.err_pass.isVisible()){
           flag1 = 1;
       }
       //no error message for wrong password input
       assertEquals(0,flag1);

   }


}
