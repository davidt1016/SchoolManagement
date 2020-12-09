import com.company.AccountGUI;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

public class UnitTestSMS {
        //Variables initialization and object instaniating
        AccountGUI ai = new AccountGUI();
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
    @Test
    public void testcase1_AccountGUI()
    {
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
        AccessUsrInfo(ai.inputUsr.getText(), ai.inputFirst.getText()+" "+ ai.inputLast.getText(), ai.InputdateofBirth.getText(),"Students");
    }
    @Test
    public void testcase2_AccountGUI()
    {
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
        AccessUsrInfo(ai.inputUsr.getText(), ai.inputFirst.getText()+" "+ ai.inputLast.getText(), ai.InputdateofBirth.getText(),"Students");
    }


}
