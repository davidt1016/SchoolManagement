package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUser extends JFrame  implements UserInterfaceGUI{
    //Database connection
    Connection con;
    PreparedStatement pst;
    Statement st;
    //Background and edit Info Panel and current User Info Panel
    private  JPanel background, editInfo, currInfo;
    //Personal Information Label
    private JLabel currentName, currentDOB, currentUsrID, currentUserName, currentUsrType, newName, newDOB, newPass;
    //Title Label for each Panel and instructions
    private JLabel instructEdit, modifyAcc, CurrAccInfo;
    private JLabel errorDate, errorPass, errorName;
    private JTextField inputNewName, inputNewDOB;
    private JPasswordField inputNewPass;
    private JButton update, cancel;
    private String usrN ="";
    private Boolean isEnteredName = false;
    private Boolean isEnteredPass = false;
    private Boolean isEnteredDate = false;
    //Storing newly updated information
    protected String name = null;
    protected String password =null;
    protected String date = null;
    private String userType = "";

    //Storing old user Info
    private String PID = "";
    private String Name = "";
    private String DOB = "";
    private String oldPass = "";

    Container f;
    //Check for date format yyyy/mm/dd
    //Code from: https://www.geeksforgeeks.org/java-date-format-validation-using-regex/
    private static boolean isValidDate(String d)
    {
        String regex = "^[0-9]{4}/(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)d);
        return matcher.matches();
    }
    //Verifying if the user input is solely string or not
    private static boolean isString(String input)
    {
        return input.matches("[a-zA-Z]+");
    }

    @Override
    public void setusrN(String userName)
    {
        usrN = userName;
    }

    @Override
    public String getusrN() {
        return usrN;
    }
    //For Displaying the user interface for edit information
    @Override
    public void DisplayUserGUI(String usersName) {
        usrN = usersName;
        f = getContentPane();
        setTitle("Edit User Information Interface");
        background = new JPanel();
        background.setBackground(Color.LIGHT_GRAY);
        background.setBounds(0,0,500,600);
        background.setLayout(null);
        //For displaying current info
        currInfo = new JPanel();
        currInfo.setLayout(null);
        currInfo.setBounds( 10, 10 , 480, 200);
        currInfo.setBackground(Color.WHITE);
        //Title
        CurrAccInfo = new JLabel("<html><u>Current User Detailed Information</html>");
        CurrAccInfo.setFont(new Font("Default", Font.BOLD, 14));
        CurrAccInfo.setBounds(125, 5, 280, 30);
        currInfo.add(CurrAccInfo);
        //---------------Data Connection Needed------------
        //Retrieve user info according their username and username is stored as variable usrN
        //Current Account info
        //---------User ID --------


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            st = con.createStatement();
            PreparedStatement statement = con.prepareStatement("SELECT U.password, U.userType FROM SMSSytem.Users U where U.username like ?");
            statement.setString(1, usrN);
            ResultSet rs = statement.executeQuery();
            //System.out.print(enteredName + " ");
            while (rs.next()) {
                userType = rs.getNString("userType");
                oldPass = rs.getNString("password");
            }

        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }


        currentUsrID = new JLabel("User ID: ");
        currentUsrID.setBounds(20, 40, 400, 15);
        currInfo.add(currentUsrID);
        //----------Username----------
        currentUserName = new JLabel("Username: ");
        currentUserName.setBounds(20, 70, 400, 15);
        currInfo.add(currentUserName);
        //----------Name-----------
        currentName = new JLabel("Full Name: ");
        currentName.setBounds(20, 100, 400, 15);
        currInfo.add(currentName);
        //-------------DOB-----------
        currentDOB = new JLabel("Date of Birth: ");
        currentDOB.setBounds(20, 130, 400, 15);
        currInfo.add(currentDOB);
        //----------User Type---------
        currentUsrType = new JLabel("User Type: ");
        currentUsrType.setBounds(20, 160, 400, 15);
        currInfo.add(currentUsrType);

        background.add(currInfo);

        //Edit Current Account Info
        editInfo = new JPanel();
        editInfo.setLayout(null);
        editInfo.setBounds(10, 220, 480, 300);
        editInfo.setBackground(Color.WHITE);

        //Modifying Account
        modifyAcc = new JLabel("<html><u>Modify Account</html>");
        modifyAcc.setFont(new Font("Default", Font.BOLD, 14));
        modifyAcc.setBounds(190, 5, 280, 30);
        editInfo.add(modifyAcc);
        //Instruction for modify account.
        instructEdit = new JLabel("<html>Fill in the field(s) that you want to edit. Any blank field will be treated as keeping original information.</htm>");
        instructEdit.setBounds(10, 25, 470, 60);
        editInfo.add(instructEdit);
        //-------new Name-----------
        newName = new JLabel("Full Name:");
        newName.setBounds(20, 85, 120, 30);
        editInfo.add(newName);
        inputNewName = new JTextField(300);
        inputNewName.setBounds(170, 85, 300, 30);
        editInfo.add(inputNewName);

        //---------new Password----------
        newPass = new JLabel("Password:");
        newPass.setBounds(20, 115, 120, 30);
        editInfo.add(newPass);
        inputNewPass = new JPasswordField(300);
        inputNewPass.setBounds(170, 115, 300, 30);
        editInfo.add(inputNewPass);
        //-------new DOB-------------
        newDOB = new JLabel("Date of Birth (YYYY/DD/MM):");
        newDOB.setBounds(20, 145, 250, 30);
        editInfo.add(newDOB);
        inputNewDOB = new JTextField(280);
        inputNewDOB.setBounds(210, 145, 260, 30);
        editInfo.add(inputNewDOB);
        //----------Error Messages---------
        errorName = new JLabel("Error!! Invalid Input Name. String of Characters ONLY!!");
        errorName.setBounds( 10, 220, 400, 30);
        errorName.setFont(new Font("Default", Font.BOLD, 12));
        errorName.setVisible(false);
        editInfo.add(errorName);
        errorPass = new JLabel("Error!! Password must be at least 6 characters!!");
        errorPass.setBounds(10, 240, 400, 30);
        errorPass.setFont(new Font("Default", Font.BOLD, 12));
        errorPass.setVisible(false);
        editInfo.add(errorPass);
        errorDate = new JLabel("Error!! Invalid Input Date Type!!");
        errorDate.setBounds(10, 260, 400, 30);
        errorDate.setFont(new Font("Default", Font.BOLD, 12));
        errorDate.setVisible(false);
        editInfo.add(errorDate);

        if(userType.equals("Students")){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                st = con.createStatement();
                PreparedStatement statement = con.prepareStatement("SELECT Student_ID, Name, DOB FROM SMSSytem.Student where userName = ?;");
                statement.setString(1, usrN);
                ResultSet rs = statement.executeQuery();
                //Getting data out from the query
                while (rs.next()) {
                    PID = rs.getString("Student_ID");
                    Name = rs.getString("Name");
                    DOB = rs.getString("DOB");
                    System.out.print("usrName: " + usrN);
                }
            }catch (ClassNotFoundException classNotFoundException ) {
                classNotFoundException.printStackTrace();
            }catch (SQLException other_SQLException) {
                other_SQLException.printStackTrace();
            }
        }

        else if(userType.equals("Teachers")){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                st = con.createStatement();
                PreparedStatement statement = con.prepareStatement("SELECT Teacher_ID, Name, DOB FROM SMSSytem.Teacher where userName = ?;");
                statement.setString(1, usrN);
                ResultSet rs = statement.executeQuery();
                //Getting data out from the query
                while (rs.next()) {
                    PID = rs.getString("Teacher_ID");
                    Name = rs.getString("Name");
                    DOB = rs.getString("DOB");
                    System.out.print("usrName: " + usrN);
                }
            }catch (ClassNotFoundException classNotFoundException ) {
                classNotFoundException.printStackTrace();
            }catch (SQLException other_SQLException) {
                other_SQLException.printStackTrace();
            }

        }

        //displaying user info inside lables
        currentUserName.setText(currentUserName.getText()+usrN);
        currentName.setText(currentName.getText()+Name);
        currentDOB.setText(currentDOB.getText()+DOB);
        currentUsrID.setText(currentUsrID.getText()+PID);
        currentUsrType.setText(currentUsrType.getText()+userType);

        //displaying user info inside text fileds
        inputNewName.setText(Name);
        inputNewDOB.setText(DOB);




        //-------------Buttons for cancel, update-----------
        //Update Button
        update = new JButton("Update");
        update.setBounds( 15, 180, 100, 40 );
        update.setBackground(new Color (25,100,205));
        update.setForeground(Color.white);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorName.setVisible(false);
                errorPass.setVisible(false);
                errorDate.setVisible(false);
                isEnteredName = false;
                isEnteredPass = false;
                isEnteredDate = false;
                name = "";
                password = null;
                date = null;
                int validCountString = 0;
                String UpdateList = " ";
                //If usrName is empty->user do not want to update the name
                if (inputNewName.getText().equals(""))
                {
                    name = null;
                    isEnteredName = false;
                }
                //Update the username
                else
                {
                    //Splitting the inputName
                    String newInputName = inputNewName.getText();
                    String []splitname = newInputName.split("\\s+");

                    //Validating all the input name is String only
                    for ( int i = 0; i < splitname.length; i++ )
                    {
                        if (isString(splitname[i]))
                        {
                            validCountString ++;
                            name = name + splitname[i] + " ";
                        }
                    }
                    //Valid input for name and user wants to update name
                    if(validCountString== splitname.length)
                    {
                        isEnteredName = true;
                        errorName.setVisible(false);
                        UpdateList = UpdateList + "Name is Updated!" + "<br/>" ;
                    }
                    else
                    {
                        //Error when there is any character that is not string/alphabet
                        errorName.setVisible(true);
                        inputNewName.setText(null);
                        name = null;
                    }
                }
                //User do not want to update the password
                if (inputNewPass.getText().equals(""))
                {
                    isEnteredPass = false;
                    password = null;
                }
                //User want to update password
                else
                {
                    //Valid password
                    if(inputNewPass.getText().length()>=6)
                    {
                        errorPass.setVisible(false);
                        isEnteredPass = true;
                        password = inputNewPass.getText();
                        UpdateList = UpdateList + "Password is Updated!" + "<br/>" ;
                    }
                    //Invalid password
                    else
                    {
                        errorPass.setVisible(true);
                        inputNewPass.setText(null);
                    }
                }
                //Check for date
                //Empty date->user do not want to update date of birth
                if(inputNewDOB.getText().equals(""))
                {
                    isEnteredDate = false;
                    date = null;
                }
                //wants to update date of birth->non-empty field for date of birth
                else
                {
                    //Valid Input date type
                    if(isValidDate(inputNewDOB.getText()))
                    {
                        isEnteredDate = true;
                        errorDate.setVisible(false);
                        date = inputNewDOB.getText();
                        UpdateList = UpdateList + "Date of Birth is Updated!" + "<br/>" ;
                    }
                    //Invalid Input Date type
                    else
                    {
                        inputNewDOB.setText(null);
                        errorDate.setVisible(true);
                    }
                }
                //Either date or name or password has input
                if ( isEnteredDate || isEnteredName || isEnteredPass)
                {
                    //----------------Data Connection needed---------------
                    //name, password, and date variable can be used directly=>it stores either NULL (user hasn't updated anything)
                    //or some string of characters, where user has type in something
                    if(password != null){
                        try{
                            pst = con.prepareStatement("Update Users Set password = ? where userName = ?");
                            pst.setString(1, password);
                            pst.setString(2, usrN);
                            //Execute the update on the database
                            pst.executeUpdate();
                        }catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                    if(userType.equals("Students")) {
                        try {
                            //actual insertion into the Students table
                            pst = con.prepareStatement("Update Student Set Name = ?, DOB = ? where userName = ?");
                            if(name == null){
                                name = Name;
                            }
                            if(date == null){
                                date = DOB;
                            }
                            pst.setString(1, name); //add all the resulting input string to "name" variable
                            pst.setString(2, date);
                            pst.setString(3, usrN);
                            //Execute the update on the database
                            pst.executeUpdate();

                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                    else if(userType.equals("Teachers")) {
                        try {
                            //actual insertion into the Teachers table
                            pst = con.prepareStatement("Update Teacher Set Name = ?, DOB = ? where userName = ?");
                            if(name == null){
                                name = Name;
                            }
                            if(date == null){
                                date = DOB;
                            }
                            pst.setString(1, name);
                            pst.setString(2, date);
                            pst.setString(3, usrN);
                            //Execute the update on the database
                            pst.executeUpdate();

                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                    if(userType.equals("Students")){
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            st = con.createStatement();
                            PreparedStatement statement = con.prepareStatement("SELECT Student_ID, Name, DOB FROM SMSSytem.Student where userName = ?;");
                            statement.setString(1, usrN);
                            ResultSet rs = statement.executeQuery();
                            //Getting data out from the query
                            while (rs.next()) {
                                PID = rs.getString("Student_ID");
                                Name = rs.getString("Name");
                                DOB = rs.getString("DOB");
                                System.out.print("usrName: " + usrN);
                            }
                        }catch (ClassNotFoundException classNotFoundException ) {
                            classNotFoundException.printStackTrace();
                        }catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                    else if(userType.equals("Teachers")){
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            st = con.createStatement();
                            PreparedStatement statement = con.prepareStatement("SELECT Teacher_ID, Name, DOB FROM SMSSytem.Teacher where userName = ?;");
                            statement.setString(1, usrN);
                            ResultSet rs = statement.executeQuery();
                            //Getting data out from the query
                            while (rs.next()) {
                                PID = rs.getString("Teacher_ID");
                                Name = rs.getString("Name");
                                DOB = rs.getString("DOB");
                                System.out.print("usrName: " + usrN);
                            }
                        }catch (ClassNotFoundException classNotFoundException ) {
                            classNotFoundException.printStackTrace();
                        }catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }

                    }

                    //displaying user info inside lables
                    currentName.setText(currentName.getText().split(":")[0] + ": " + Name);
                    currentDOB.setText(currentDOB.getText().split(":")[0] + ": " + DOB);



                    JOptionPane.showMessageDialog(f, "<html>"+UpdateList+"</html>");
                }

            }
        });
        editInfo.add(update);
        //Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds( 370, 180, 100, 40 );
        cancel.setBackground(new Color (25,100,205));
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin a = new Admin();
                a.DisplayUserGUI("Admin"); //need to determine the admin username
                dispose();

            }
        });
        editInfo.add(cancel);

        background.add(editInfo);
        add(background);
        this.addWindowListener(new WL());
        setSize(500, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class WL implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                Admin ad = new Admin();
                ad.DisplayUserGUI(usrN);
                dispose();
            }
            else{
                EditUser eU = new EditUser();
                eU.DisplayUserGUI(usrN);
                dispose();
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {

        }
        @Override
        public void windowIconified(WindowEvent e) { }
        @Override
        public void windowDeiconified(WindowEvent e) { }
        @Override
        public void windowActivated(WindowEvent e) { }
        @Override
        public void windowDeactivated(WindowEvent e) { }
    }
}
