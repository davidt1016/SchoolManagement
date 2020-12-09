package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

//newest

public class Admin extends JFrame implements UserInterfaceGUI {
    //connection paramteres
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst2;
    Statement st, st2;

    //Jlist to display search results
    private static JList resultList;

    //Variable Initializations
    private static Container frame;
    private ImageIcon icon;
    private static JLabel label, title, searchUsrName, instructions, confirmSelectionL;
    public static JTextField inputUsrName;
    public static JButton  editInfo, editRecords, editCourse, logoffNCancel, changePass, cancelPass, updatePass;
    private static JPanel panel, accountPanel, recordPanel;
    //for password interfaces
    private static JLabel PassInst, currPass, newPass, confirmPass, errorPass;
    public static JPasswordField inputCurr, inputNewPass, inputConfirmPass;
    protected String newPassW = " ";
    private String userN = " ";
    private String inUsrname = " ";

    private String selectedName = "";
    private String selectedType = "";
    private String currPassS;
    private String foundID = "";
    private int teacherID =0;

    @Override
    public void setusrN(String userName) {
        this.userN = userName;
    }

    @Override
    public String getusrN() {
        return userN;
    }

    //Use username only remove NAME
    @Override
    public void DisplayUserGUI(String usersName) {
        // We need search bad First name, username to display the records correspondingly
        //Modify anything but Personal ID and username
        //Just one panel to display one thing
        userN = usersName;

        //instantiating the results vector
        Vector<String> resultData = new Vector<String>();

        setTitle("Administrator Interface");
        frame = getContentPane();
        label = new JLabel();
        panel = new JPanel();
        frame.setLayout(null);
        //Panel Interfaces
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        panel.setBounds(280, 120, 550, 490);

        //account panel
        accountPanel = new JPanel();
        accountPanel.setLayout(null);
        accountPanel.setBounds(12, 10, 525, 170);
        accountPanel.setBackground(Color.white);


        //title of the interface
        title = new JLabel("<html><u>Welcome, Administrator!</html>");
        title.setFont(new Font("Default", Font.BOLD, 18));
        title.setBounds(175, 5, 300, 30);
        accountPanel.add(title);


        //For changing password for admin account
        //Password Modification interfaces
        PassInst = new JLabel(" ");
        PassInst.setBounds(5, 35, 250, 20);
        accountPanel.add(PassInst);
        //Current password
        currPass = new JLabel(" ");
        currPass.setBounds(5, 60, 250, 20);
        accountPanel.add(currPass);
        inputCurr = new JPasswordField(150);
        inputCurr.setBounds(120, 60, 150, 20);
        inputCurr.setEditable(false);
        inputCurr.setBorder(new LineBorder(Color.white));
        inputCurr.setBackground(Color.white);
        accountPanel.add(inputCurr);



        //new password
        newPass = new JLabel(" ");
        newPass.setBounds(5, 90, 250, 20);
        accountPanel.add(newPass);
        inputNewPass = new JPasswordField(150);
        inputNewPass.setBounds( 120, 90, 150, 20);
        inputNewPass.setEditable(false);
        inputNewPass.setBorder(new LineBorder(Color.white));
        inputNewPass.setBackground(Color.white);
        accountPanel.add(inputNewPass);

        //Confirm new password
        confirmPass = new JLabel(" ");
        confirmPass.setBounds(3, 120, 250, 20);
        inputConfirmPass = new JPasswordField(150);
        inputConfirmPass.setEditable(false);
        inputConfirmPass.setBorder(new LineBorder(Color.white));
        inputConfirmPass.setBackground(Color.white);
        accountPanel.add(confirmPass);

        inputConfirmPass.setBounds(120, 120, 150, 20);
        accountPanel.add(inputConfirmPass);



        //Button for updating the password
        updatePass = new JButton("Update Password");
        updatePass.setBounds(10, 5, 135, 25);
        updatePass.setBackground(new Color (25,100,205));
        updatePass.setForeground(Color.white);
        updatePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassInst.setText("Please fill in the following fields");
                currPass.setText("Current Password:");
                newPass.setText("New Password:");
                confirmPass.setText("Confirm Password:");
                inputCurr.setEditable(true);
                inputCurr.setBorder(new LineBorder(Color.black));
                inputNewPass.setEditable(true);
                inputNewPass.setBorder(new LineBorder(Color.black));
                inputConfirmPass.setEditable(true);
                inputConfirmPass.setBorder(new LineBorder(Color.black));
                cancelPass.setEnabled(true);
                cancelPass.setVisible(true);
                changePass.setEnabled(true);
                changePass.setVisible(true);
                errorPass.setText(" ");

            }
        });
        accountPanel.add(updatePass);

        //Error in updating password
        errorPass = new JLabel(" ");
        errorPass.setBounds( 5, 150, 400, 20);
        accountPanel.add(errorPass);
        //Cancel operation for updating password
        cancelPass = new JButton("Cancel");
        cancelPass.setBounds(420, 70, 80, 50);
        cancelPass.setBackground(new Color (25,100,205));
        cancelPass.setForeground(Color.white);
        cancelPass.setEnabled(false);
        cancelPass.setVisible(false);
        cancelPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource()== cancelPass)
                {
                    Admin ad = new Admin();
                    ad.DisplayUserGUI(userN);
                    dispose();
                }
            }
        });
        accountPanel.add(cancelPass);

        changePass = new JButton("<html><div style='text-align: center;'>Change Admin <br> Password</div></html>");
        changePass.setBounds(290, 70, 110, 50);
        changePass.setBackground(new Color (25,100,205));
        changePass.setForeground(Color.white);
        changePass.setEnabled(false);
        changePass.setVisible(false);
        changePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Admin pressed change password through changePass button
                if (inputNewPass.getText().length() >= 6 && inputConfirmPass.getText().length() >= 6 && inputCurr.getText().length() >= 6)
                {
                    //For verifying new password matches confirmed password

                    if (Arrays.equals(inputNewPass.getPassword(), inputConfirmPass.getPassword()))
                    {

                        //fetching old password from database
                        try{
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            st2 = con.createStatement();
                            //fetching username
                            PreparedStatement statement2 = con.prepareStatement("SELECT password FROM SMSSytem.Users where username = ?;");
                            statement2.setString(1, userN);
                            ResultSet rs = statement2.executeQuery();

                            while (rs.next()) {
                                currPassS = rs.getString("password");
                            }
                        }catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }catch (ClassNotFoundException classNotFoundException ) {
                            classNotFoundException.printStackTrace();
                        }

                        //comparing old password with the entered one and updating it if they match
                        String currentpassS = new String(inputCurr.getPassword());
                        String newpassS = new String(inputNewPass.getPassword());
                        System.out.print(currentpassS+"               ;");
                        System.out.print(currPass+"               ;");
                        //updating password in database
                        if(currentpassS.equals(currPassS)){
                            try{
                                pst2 = con.prepareStatement("UPDATE SMSSytem.Users SET password = ? where username = ?");
                                pst2.setString(2, userN );
                                pst2.setString(1, newpassS);
                                pst2.executeUpdate();
                                JOptionPane.showMessageDialog(frame, "Update Password Successfully!");
                            }catch (SQLException other_SQLException){
                                other_SQLException.printStackTrace();
                            }

                        }
                        else{
                            System.out.print("Current Password Doesn't Match Our Records ");
                            JOptionPane.showMessageDialog(frame, "Wrong Password Entered!");
                        }

                        errorPass.setText(" ");
                        newPassW = inputNewPass.getText();
                        //return back to original display
                        Admin administrator = new Admin();
                        administrator.DisplayUserGUI(userN);
                    }
                    //Password and confirmed Password do not match
                    else{
                        //Error occur with the confirmed password and the password doesnt match
                        inputNewPass.setText(null);
                        inputConfirmPass.setText(null);
                        inputCurr.setText(null);
                        errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
                    }
                }

                else
                {
                    inputNewPass.setText(null);
                    inputConfirmPass.setText(null);
                    inputCurr.setText(null);
                    errorPass.setText("<html><font color='Red'>Password should be at least 6 characters! </font></html>");
                }
            }
        });

        accountPanel.add(changePass);

        //Initially act as a log off button
        logoffNCancel = new JButton( "Log Off");
        logoffNCancel.setBounds( 410, 10, 100, 30);
        logoffNCancel.setBackground(new Color (250,5,5));
        logoffNCancel.setForeground(Color.white);

        logoffNCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if log off button is pressed, return to Login Screen
                if ( e.getSource() == logoffNCancel )
                {
                    int output = JOptionPane.showConfirmDialog(frame , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
                    if(output == JOptionPane.YES_OPTION)
                    {
                        Gui GI = new Gui();
                        dispose();
                        JOptionPane.showMessageDialog(null , "Return to Login Screen!");
                    }
                    else {
                        Admin AD = new Admin();
                        AD.DisplayUserGUI(userN);
                        dispose();
                    }
                }
            }
        });
        accountPanel.add(logoffNCancel);

        panel.add(accountPanel);

        //edit records panel
        recordPanel = new JPanel();
        recordPanel.setLayout(null);
        recordPanel.setBackground(Color.white);
        recordPanel.setBounds(12, 190, 525, 280);
        //Instructions for Admin
        instructions = new JLabel("Please type in the username that you want to edit");
        instructions.setFont(new Font("Default", Font.PLAIN, 14));
        instructions.setBounds(95, 10, 490, 30);
        recordPanel.add(instructions);

        //User name input
        searchUsrName = new JLabel("Username: ");
        searchUsrName.setBounds(150, 40, 150, 30);
        recordPanel.add(searchUsrName);
        //input for username
        inputUsrName = new JTextField(160);
        inputUsrName.setBounds(220,40, 160, 30);
        recordPanel.add(inputUsrName);

        //JList to display search results
        resultList = new JList<String>(resultData);
        resultList.setBounds(220, 80, 160, 95);
        resultList.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        resultList.setVisible(false);

        //Edit Button
        editInfo = new JButton("<html><div style='text-align: center;'>Edit<br> Information</div></html>");
        editInfo.setBounds( 100, 190, 110, 70);
        editInfo.setBackground(new Color (25,100,205));
        editInfo.setForeground(Color.white);
        editInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //-------------------Database Connection needed----------------
                //Retrieve data from the suggestive search of username
                //Suggestive search for username and pass into line 238 with returned/selected usrname and uncomment
                //calling account update information interface

                EditUser edUsr = new EditUser();
                edUsr.DisplayUserGUI(selectedName); //Need to pass in username returned from suggestive search
                dispose();

            }
        });
        recordPanel.add(editInfo);
        //Edit course button
        editCourse = new JButton("<html><div style='text-align: center;'>Edit<br>Course</div></html>");
        editCourse.setBounds(310, 190, 110, 70);
        editCourse.setBackground(new Color (25,100,205));
        editCourse.setForeground(Color.white);
        editCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //-------------Data Connection Needed Here for Assigning courses for Teacher------------
                //------------need to retrieve usr input name and the corresponding teacherID

                try {
                    con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                    st = con.createStatement();
                    PreparedStatement statement = con.prepareStatement("SELECT Teacher_ID FROM SMSSytem.Teacher where userName = ?");
                    statement.setString(1, selectedName);
                    ResultSet rs = statement.executeQuery();
                    //System.out.print(enteredName + " ");
                    while (rs.next()) {
                        foundID = rs.getString("Teacher_ID");
                    }

                }catch (SQLException other_SQLException) {
                    other_SQLException.printStackTrace();
                }
                AssignCourse aCourse = new AssignCourse(Integer.parseInt(foundID));
                dispose();
            }
        });
        recordPanel.add(editCourse);
        //Edit Records-overlapping for now
        editRecords = new JButton("<html><div style='text-align: center;'>Edit<br>Performance</div></html>");
        editRecords.setBounds(310, 190, 110, 70);
        editRecords.setBackground(new Color (25,100,205));
        editRecords.setForeground(Color.white);
        editRecords.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //For editing performance for students
                        SearchGUI sG = new SearchGUI("ALL",selectedName, "BOTH");
                        dispose();
                    }
                }
        );
        recordPanel.add(editRecords);
        //Search button
        //search = new JButton("SEARCH");
        //search.setBounds(205, 65, 130, 30);
        //recordPanel.add(search);

        panel.add(recordPanel);



        add(panel);

        //confirm selection label
        confirmSelectionL = new JLabel();
        confirmSelectionL.setBounds(220,80,350,35);
        recordPanel.add(confirmSelectionL);

        //Image handling/Resizing
        icon = new ImageIcon(this.getClass().getResource("Admin.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700, Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        label.setIcon(icon);
        label.setBounds(0,0, 1100, 700);
        label.setBorder(null);
        add(label);
        this.addWindowListener(new WL());
        setSize(1100,700);
        setLocationRelativeTo(null);
        setVisible(true);

        //action listener for change in search text filed
        inputUsrName.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //checking if there's input in search field
                if(inputUsrName.getText().length()>0) {
                    resultData.clear();

                    //fetching data from database that matches entered Name and displaying results in a JList
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                        st = con.createStatement();
                        PreparedStatement statement = con.prepareStatement("SELECT U.username, U.userType FROM SMSSytem.Users U where U.username like ?");
                        String enteredName = inputUsrName.getText();
                        statement.setString(1, "%" + enteredName.trim() + "%");
                        ResultSet rs = statement.executeQuery();
                        //System.out.print(enteredName + " ");
                        resultData.clear();
                        resultList.updateUI();
                        while (rs.next()) {
                            if(!rs.getString("username").equals("Admin")) {
                                String foundName = "<html>" + rs.getString("username") + ",&nbsp;&nbsp;&nbsp;&nbsp;" + "@" + "<i>" + rs.getString("userType") + "</i>" + "</html>";
                                confirmSelectionL.setVisible(false);
                                resultList.setVisible(true);
                                resultData.add(foundName);
                                resultList.updateUI();
                            }
                        }

                    }catch (ClassNotFoundException classNotFoundException ) {
                        classNotFoundException.printStackTrace();
                    }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
                    }
                }
                //disabling and clearing search results if search box is empty
                else if(inputUsrName.getText().length()==0){
                    resultData.clear();
                    resultList.setVisible(false);
                }
            }
        });

        //listener for down arrow key move to search results from search box
        inputUsrName.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("Down key pressed");
                    resultList.requestFocusInWindow();
                }

            }
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        //listner for UP and ENTER keys when in navigating search results
        resultList.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                //going back to search box when cursor is at item 0 of the result list and UP key is pushed
                if(resultList.isSelectedIndex(0)) {


                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        System.out.println("UP key pressed");
                        inputUsrName.requestFocus();
                    }
                }
                //fetching selected user data when ENTER key is pushed
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    resultList.setVisible(false);
                    confirmSelectionL.setText("<html>" + selectedName + "</html>");
                    selectedType = ((selectedType.split("&nbsp;@")[1]).split("</i>")[0]).split("<i>")[1];
                    System.out.print(selectedType + "\n");
                    inputUsrName.grabFocus();
                    confirmSelectionL.setVisible(true);

                    if(selectedType.equals("Students")){
                        editCourse.setVisible(false);
                        editRecords.setVisible(true);

                    }

                    else if(selectedType.equals("Teachers")){
                        editCourse.setVisible(true);
                        editRecords.setVisible(false);
                    }

                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        //listener for fetching selected result from result list
        resultList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent l) {
                if (!l.getValueIsAdjusting()) {
                    selectedName = resultList.getSelectedValue().toString();
                    selectedName = selectedName.split(",")[0].split("<html>")[1];

                    selectedType = resultList.getSelectedValue().toString();
                    System.out.print(selectedName + "\n");
                    System.out.print(selectedType + "\n");
                }
            }
        });




        recordPanel.add(resultList);

    }



    //For Showing Messages after the Window has been disposed
    class WL implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(frame , "Are you sure you want to exit?", null , JOptionPane.YES_OPTION);
            //For sure for closing and exiting the program
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Exiting.....");
                //Exit the program
                System.exit(1);
            }
            //"No" option: remain in the same interface.
            else{
                Admin administrator = new Admin();
                administrator.DisplayUserGUI(userN);
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
