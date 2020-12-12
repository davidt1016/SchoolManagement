import com.company.AccountGUI;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

public class AccountGUI_Test {
        //Variables initialization and object instaniating
        //AccountGUI ai = new AccountGUI();

        /*
        Connection con;
        PreparedStatement pst;
        Statement st;
        private String SID, PID, Name, DOB;

        //Testing full name, test username, test date of Birth, test usertype
        private void AccessUsrInfo(String usrname, String fullname, String DateBirth, String userType)
        {
            //fetching user info
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                st = con.createStatement();
                PreparedStatement statement = con.prepareStatement("SELECT Student_ID, Name, DOB FROM SMSSytem.Student where username = ?;"); //may need some changes
                statement.setString(1, usrname);
                ResultSet rs = statement.executeQuery();
                //Getting data out from the query
                while (rs.next()) {
                    SID = rs.getString("Student_ID");
                    PID = usrname + SID;
                    Name = rs.getString("Name");
                    DOB = rs.getString("DOB");

                }
            }catch (ClassNotFoundException classNotFoundException ) {
                classNotFoundException.printStackTrace();
            }catch (SQLException other_SQLException) {
                other_SQLException.printStackTrace();
            }
            //Test Assert Equals Here
            assertEquals(fullname, Name);
            assertEquals(DateBirth, DOB);
            //Test Assert usrname and userType....may need to update query

        }

         */

        /*
        //Testing password
        private void AccessPassword(String currentPass, String confirmedPass)
        {
            try{
                st = con.createStatement();
                //fetching username
                PreparedStatement statement = con.prepareStatement("SELECT password FROM SMSSytem.Users where username = ?;");
                statement.setString(1, userN);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    currPass = rs.getString("password");
                }
            }catch (SQLException other_SQLException) {
                other_SQLException.printStackTrace();
            }
        //Test Assert Equals Here
        assertEquals(fullname, Name);
        assertEquals(DateBirth, DOB);
        //Test Assert usrname and userType....may need to update query

    }

         */

    //Testing for creating existing account
    @Test
    public void testcase1_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("kevin111");
        ai.inputFirst.setText("Kevin");
        ai.inputLast.setText("Cao");
        ai.InputdateofBirth.setText("1998/11/11");
        //pass, confirmedPass
        ai.pass.setText("9999999999");
        ai.confirmedPass.setText("9999999999");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //Because the account is already exist, so the expected value is false
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for creating new account
    @Test
    public void testcase2_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);

        ai.inputUsr.setText("Dustin5488");
        ai.inputFirst.setText("Dustin");
        ai.inputLast.setText("Lin");
        ai.InputdateofBirth.setText("1786/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //This will create a new account.
        //If this test get call again, it will fail because this account is already created
        assertEquals(true, ai.getCreateAccount());

        //Delete
        PreparedStatement pst3;

        try {
            pst3 = ai.con.prepareStatement("DELETE FROM SMSSytem.Student WHERE(userName = 'Dustin5')");
            pst3.executeUpdate();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        try {
            pst3 = ai.con.prepareStatement("DELETE FROM SMSSytem.Users WHERE(username = 'Dustin5')");
            pst3.executeUpdate();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

    }

    //Testing for wrong input
    @Test
    public void testcase3_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText(null);
        ai.inputFirst.setText(null);
        ai.inputLast.setText(null);
        ai.InputdateofBirth.setText(null);
        //pass, confirmedPass
        ai.pass.setText("hiiiiiiiiii");
        ai.confirmedPass.setText("hiiiiiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for wrong password
    @Test
    public void testcase4_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hi");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorPass.setText("<html><font color='Red'>Passwords MUST be at least 6 characters. </font></html>");
        assertEquals("<html><font color='Red'>Passwords MUST be at least 6 characters. </font></html>", ai.errorPass.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for wrong confirmed password
    @Test
    public void testcase5_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hi");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorPass.setText("<html><font color='Red'>Passwords MUST be at least 6 characters. </font></html>");
        assertEquals("<html><font color='Red'>Passwords MUST be at least 6 characters. </font></html>", ai.errorPass.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for created password and confirmed password do not match
    @Test
    public void testcase6_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiii");
        ai.confirmedPass.setText("hiiiiiih");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
        assertEquals("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>", ai.errorPass.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for when there is one or two input left blank
    @Test
    public void testcase7_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("");
        //pass, confirmedPass
        ai.pass.setText("hiiiiiii");
        ai.confirmedPass.setText("hiiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorMessages.setText("<html><font color='Red'>ERROR!! One or more fields have been left blank!! </font></html>");
        assertEquals("<html><font color='Red'>ERROR!! One or more fields have been left blank!! </font></html>", ai.errorMessages.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for when there is error input for date of birth
    @Test
    public void testcase8_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("18/07/1998");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorDate.setText("<html><font color='Red'>Incompatible Date Format. Please Try Again with (YYYY/DD/MM) </font></html>");
        assertEquals("<html><font color='Red'>Incompatible Date Format. Please Try Again with (YYYY/DD/MM) </font></html>", ai.errorDate.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for when there is less than 3 alphabets characters input for username
    @Test
    public void testcase9_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("mi54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorUsrName.setText("<html><font color='Red'>ERROR!! First three characters in the usrname has to be alphabets!!</font></html>");
        assertEquals("<html><font color='Red'>ERROR!! First three characters in the usrname has to be alphabets!!</font></html>", ai.errorUsrName.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for when there is less than 6 characters input for username
    @Test
    public void testcase10_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("mi");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorUsrName.setText("<html><font color='Red'>ERROR!!Username MUST be at least 6 Characters!</font></html>");
        assertEquals("<html><font color='Red'>ERROR!!Username MUST be at least 6 Characters!</font></html>", ai.errorUsrName.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for whether user selects a user type
    @Test
    public void testcase11_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiii");
        ai.confirmedPass.setText("hiiiiii");
        ai.usrT.setSelectedItem("");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorUsrType.setText("<html><font color='Red'>Error! Please Select A User Type </font></html>");
        assertEquals("<html><font color='Red'>Error! Please Select A User Type </font></html>", ai.errorUsrType.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for wrong first name input
    @Test
    public void testcase12_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael1");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiiiiiii");
        ai.confirmedPass.setText("hiiiiiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorfirstName.setText("<html><font color='Red'>ERROR!! First Name must be string of characters ONLY!! </font></html>");
        assertEquals("<html><font color='Red'>ERROR!! First Name must be string of characters ONLY!! </font></html>", ai.errorfirstName.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for wrong last name input
    @Test
    public void testcase13_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang1");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiiiiiii");
        ai.confirmedPass.setText("hiiiiiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorLastName.setText("<html><font color='Red'>ERROR!! Last Name must be string of characters ONLY!! </font></html>");
        assertEquals("<html><font color='Red'>ERROR!! Last Name must be string of characters ONLY!! </font></html>", ai.errorLastName.getText());
        assertEquals(false, ai.getCreateAccount());
    }

    //Testing for if there is an existing username
    @Test
    public void testcase14_AccountGUI()
    {
        AccountGUI ai = new AccountGUI();
        ai.Display();
        ai.setVisible(false);
        ai.inputUsr.setText("michael54321");
        ai.inputFirst.setText("Michael");
        ai.inputLast.setText("Zhang");
        ai.InputdateofBirth.setText("1996/18/07");
        //pass, confirmedPass
        ai.pass.setText("hiiiiiiiiii");
        ai.confirmedPass.setText("hiiiiiiiiii");
        ai.usrT.setSelectedItem("Students");
        ai.create.doClick();
        //------------------Retrieve Database-------------
        //for assertEquals
        //errorUserExsists.setText("<html><font color='Red'>Username Already Exists! Please Try again with a Different Username </font></html>");
        assertEquals("<html><font color='Red'>Username Already Exists! Please Try again with a Different Username </font></html>", ai.errorUserExsists.getText());
        assertEquals(false, ai.getCreateAccount());
    }


}
