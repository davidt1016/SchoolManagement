package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

public class Admin extends JFrame implements UserInterfaceGUI {

    //Variable Initializations
    private static Container frame;
    private ImageIcon icon;
    private static JLabel label, title, searchUsrName, instructions;
    private static JTextField inputUsrName;
    private static JButton  editInfo, editRecords, editCourse, logoffNCancel, changePass, cancelPass, updatePass;
    private static JPanel panel, accountPanel, recordPanel;
    //for password interfaces
    private static JLabel PassInst, currPass, newPass, confirmPass, errorPass;
    private static JPasswordField inputCurr, inputNewPass, inputConfirmPass;
    protected String newPassW = " ";
    private String userN = " ";
    protected String inUsrname = " ";
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

        setTitle("Administrator Interface");
        frame = getContentPane();
        label = new JLabel();
        panel = new JPanel();
        frame.setLayout(null);
        //Panel Interfaces
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        panel.setBounds(280, 160, 550, 375);

        //account panel
        accountPanel = new JPanel();
        accountPanel.setLayout(null);
        accountPanel.setBounds(12, 10, 525, 170);
        accountPanel.setBackground(Color.white);

        //title of the interface
        title = new JLabel("<html><u>Welcome, Administrator!</html>");
        title.setFont(new Font("Default", Font.BOLD, 18));
        title.setBounds(150, 5, 300, 30);
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
        updatePass.setBounds(10, 10, 115, 20);
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
        cancelPass.setBounds(270, 60, 100, 70);
        cancelPass.setEnabled(false);
        cancelPass.setVisible(false);
        cancelPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource()== cancelPass)
                {
                    Admin ad = new Admin();
                    ad.DisplayUserGUI(userN);

                }
            }
        });
        accountPanel.add(cancelPass);

        changePass = new JButton("<html><div style='text-align: center;'>Change Admin <br> Password</div></html>");
        changePass.setBounds(370, 60, 150, 70);
        changePass.setEnabled(false);
        changePass.setVisible(false);
        changePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Admin pressed change password through changePass button
                if (inputNewPass.getText().length() >= 6 || inputConfirmPass.getText().length() >= 6 || inputCurr.getText().length() >= 6)
                {
                    errorPass.setText(" ");
                    //------------------------Data connection needed to verfiy current admin password is correct----------
                    //Original pass must be correct first in order to proceed to process inputNewpass and inputConfirmPass
                    //For verifying new password matches confirmed password
                    if (Arrays.equals(inputNewPass.getPassword(), inputConfirmPass.getPassword()))
                    {
                        newPassW = inputNewPass.getText();
                        //------------------------Data connection needed for updating admin password----------
                        JOptionPane.showMessageDialog(frame, "Update Password Successfully!");
                        //return back to original display
                        Admin administrator = new Admin();
                        administrator.DisplayUserGUI(userN);
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
        logoffNCancel.setBounds( 420, 10, 100, 30);

        logoffNCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if log off button is pressed, return to Login Screen
                if ( e.getSource() == logoffNCancel )
                {
                    Gui login = new Gui();
                }
            }
        });
        accountPanel.add(logoffNCancel);

        panel.add(accountPanel);

        //edit records panel
        recordPanel = new JPanel();
        recordPanel.setLayout(null);
        recordPanel.setBackground(Color.white);
        recordPanel.setBounds(12, 190, 525, 170);
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
        //Edit Button
        editInfo = new JButton("<html><div style='text-align: center;'>Edit<br> Information</div></html>");
        editInfo.setBounds( 100, 80, 110, 70);
        editInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //-------------------Database Connection needed----------------
                //Retrieve data from the suggestive search of username
                //Suggestive search for username and pass into line 238 with returned/selected usrname and uncomment
                //calling account update information interface
                EditUser edUsr = new EditUser();
               // edUsr.DisplayUserGUI("test"); //Need to pass in username returned from suggestive search
                dispose();

            }
        });
        recordPanel.add(editInfo);
        //Edit course button
        editCourse = new JButton("<html><div style='text-align: center;'>Edit<br>Course</div></html>");
        editCourse.setBounds(310, 80, 110, 70);
        recordPanel.add(editCourse);
        //Edit Records-overlapping for now
        editRecords = new JButton("<html><div style='text-align: center;'>Edit<br>Performance</div></html>");
        editRecords.setBounds(310, 70, 110, 70);
        recordPanel.add(editRecords);
        //Search button
        //search = new JButton("SEARCH");
        //search.setBounds(205, 65, 130, 30);
        //recordPanel.add(search);

        panel.add(recordPanel);



        add(panel);

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
