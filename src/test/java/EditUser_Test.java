import com.company.EditUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditUser_Test {
    //Testing for wrong name input
    @Test
    public void testcase1_EditUser() {
        EditUser e = new EditUser();
        e.DisplayUserGUI("kevin111");
        e.setVisible(false);

        //inputNewName, inputNewDOB
        e.inputNewName.setText("kev11");
        e.inputNewDOB.setText("1998/11/11");

        //pass
        e.inputNewPass.setText("9999999999");

        e.update.doClick();

        //for assertEquals
        int flag = 0;
        if(e.errorName.isVisible()){
            flag = 1;
        }
        assertEquals(1,flag);

    }

    //Testing for wrong date of birth input
    @Test
    public void testcase2_EditUser() {
        EditUser e = new EditUser();
        e.DisplayUserGUI("kevin111");
        e.setVisible(false);

        //inputNewName, inputNewDOB
        e.inputNewName.setText("Kevin Cao");
        e.inputNewDOB.setText("11/11/1998");

        //pass
        e.inputNewPass.setText("9999999999");

        e.update.doClick();

        //for assertEquals
        int flag = 0;
        if(e.errorDate.isVisible()){
            flag = 1;
        }
        assertEquals(1,flag);

    }

    //Testing for wrong date of birth input
    @Test
    public void testcase3_EditUser() {
        EditUser e = new EditUser();
        e.DisplayUserGUI("kevin111");
        e.setVisible(false);

        //inputNewName, inputNewDOB
        e.inputNewName.setText("Kevin Cao");
        e.inputNewDOB.setText("1998/11/11");

        //pass
        e.inputNewPass.setText("999");

        e.update.doClick();

        //for assertEquals
        int flag = 0;
        if(e.errorPass.isVisible()){
            flag = 1;
        }
        assertEquals(1,flag);

    }

    //Testing for all correct input
    @Test
    public void testcase4_EditUser() {
        EditUser e = new EditUser();
        e.DisplayUserGUI("kevin111");
        e.setVisible(false);

        //inputNewName, inputNewDOB
        e.inputNewName.setText("Kevin Cao");
        e.inputNewDOB.setText("1998/11/11");

        //pass
        e.inputNewPass.setText("9999999999");

        e.update.doClick();

        //for assertEquals
        int flag1 = 0;
        if(e.errorName.isVisible()){
            flag1 = 1;
        }
        assertEquals(0,flag1);

        //for assertEquals
        int flag2 = 0;
        if(e.errorDate.isVisible()){
            flag2 = 1;
        }
        assertEquals(0,flag2);

        //for assertEquals
        int flag3 = 0;
        if(e.errorPass.isVisible()){
            flag3 = 1;
        }
        assertEquals(0,flag3);

    }
}
