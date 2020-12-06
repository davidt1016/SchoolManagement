package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class SchoolGUI implements ActionListener {
    Connection con;
    PreparedStatement pst;
    Statement st;
    private static JFrame frameW;
    private static JLabel school;
    private static JPanel panel;
    private static JLabel instructions;
    private static JLabel userName;
    private static JLabel password;
    private static JTextField usrNameInput;
    private static JTextField passwordInput;
    private static JButton newUsr;
    private static JButton retrieve;

    public void Display()
    {
        frameW = new JFrame("School Management System Testing");
        frameW.setSize(500,500);
        panel = new JPanel();
        panel.setLayout(null);
        frameW.add(panel);

        school = new JLabel("School Management Login Testing");
        school.setBounds(120, 20, 300, 15);
        panel.add(school);

        instructions = new JLabel("Please Enter Your Credentials: ");
        instructions.setBounds(15, 90, 300, 15);
        panel.add(instructions);

        userName = new JLabel("User Name: ");
        userName.setBounds(15, 150, 200, 15);
        panel.add(userName);
        usrNameInput = new JTextField(50);
        usrNameInput.setBounds(150, 150, 150, 30);
        panel.add(usrNameInput);


        password = new JLabel("Password: ");
        password.setBounds(15, 200, 200, 15);
        panel.add(password);
        passwordInput = new JTextField(50);
        passwordInput.setBounds(150, 200, 150, 30);
        panel.add(passwordInput);

        newUsr = new JButton("New User");
        newUsr.setBounds(15, 260, 110, 30);
        panel.add(newUsr);
        newUsr.addActionListener(new SchoolGUI());

        retrieve = new JButton("Display Data in Database");
        retrieve.setBounds( 150, 260, 200, 30);
        retrieve.addActionListener(new SchoolGUI());
        panel.add(retrieve);

        frameW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameW.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if( e.getSource()== newUsr )
        {
            try {

                String usrsName = usrNameInput.getText();
                String passw = passwordInput.getText();

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                pst  = con.prepareStatement("insert into users(usrName, password)values(?,?)");
                pst.setString(1, usrsName);
                pst.setString(2, passw);
                //Execute the update on the database
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "User Created!!!");
                usrNameInput.setText(" ");
                passwordInput.setText(" ");

            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        }
        else if ( e.getSource() == retrieve )
        {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                //Amazon AWS
                con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                //Reference: https://www.tutorialspoint.com/jdbc/jdbc-select-records.htm

                st = con.createStatement();
                String querySQL = "SELECT * FROM users";
                ResultSet rs = st.executeQuery(querySQL);
                String users;
                String passW;
                System.out.println("Records in current Database:");
                //Getting data out from the database
                while(rs.next())
                {
                    users = rs.getString("usrName");
                    passW =rs.getString("password");
                    System.out.print("usrName: "+ users);
                    System.out.println(", password: "+ passW);

                }
                rs.close();
                JOptionPane.showMessageDialog(null, "User Data Retrieved!!!");
                usrNameInput.setText(" ");
                passwordInput.setText(" ");

            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }

    }
}