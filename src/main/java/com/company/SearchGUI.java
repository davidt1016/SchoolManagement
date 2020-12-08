package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.Vector;
import javax.naming.Name;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SearchGUI extends JFrame{
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst2;
    Statement st;

    //Variable Initializations
    Container f;
    private static JLabel title,l_search, confirmSelectionL, l_grade, l_attendance, selectCourse;
    private static JTextField tf_search, tf_gr, tf_att, inputCourse;
    private static JPanel panel, backgroundPanel;
    private static JButton select, cancel;
    protected String usrType = " ";
    private String userN;
    private String CourseN = " ";
    private String mode;
    private String foundName = "";

    private  String selectedName;

    private static JList resultList;
    private String SID;

    //Setter and getter for userName
    //Setter for CourseName
    public void setCourseN ( String CourseName )
    {
        this.CourseN = CourseName;
    }
    //Getter for CourseName
    public String getCourseN()
    {
        return CourseN;
    }

    //Setter for userN
    public void setUserN ( String username )
    {
        this.userN = username;
    }
    //Getter for userN
    public String geUserN()
    {
        return userN;
    }

    //Setter for mode
    public void setMode ( String chosenMode)
    {
        this.mode = chosenMode;
    }
    //Getter for mode
    public String getMode()
    {
        return mode;
    }
    //Setter for usrType
    public void setUsrType(String Users)
    {
        this.usrType = Users;
    }
    //Getter for UsrType
    public String getUsrType()
    {
        return usrType;
    }
    SearchGUI(String CourseName, String username, String chosenMode){
        CourseN = CourseName;
        userN = username;
        mode = chosenMode;

        Vector<String> resultData = new Vector<String>();
        //Checking for which user type it is based on the passed in course
        //if it is "ALL" then usrType will be admin since admin can modify everything else,
        //it will be teacher interface for a specific course
        //Admin
        if (CourseN == "ALL")
        {
            usrType = "Admin";
        }
        else
        {
            usrType = "Teacher";
        }



        f = getContentPane();
        f.setLayout(null);
        setTitle("Search Interface");

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 15, 450, 450);
        panel.setBackground(Color.white);
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 500, 500);
        backgroundPanel.setBackground(Color.LIGHT_GRAY);
        backgroundPanel.add(panel);
        f.add(backgroundPanel);



        //Title for search
        title = new JLabel();
        //Label for search
        l_search = new JLabel();

        //Text field for search
        tf_search = new JTextField("");
        tf_search.setBounds(140,100,250,30);
        //For admin to search course
        selectCourse = new JLabel();
        inputCourse = new JTextField("");
        //Will need another suggestive search for input course
        inputCourse.setBounds(140,100,250,30);
        inputCourse.setVisible(false);
        panel.add(inputCourse);
        //User type for admin will display different title
        //For admin interfaces label
        if (usrType == "Admin")
        {
            title.setText("<html><u>Edit"+" "+userN+" Performance Records</html>");
            title.setBounds(80, 5, 420, 60);
            l_search.setText(null);
            tf_search.setEditable(true);
            selectCourse.setText("Course Search: ");
            inputCourse.setEditable(false);
            inputCourse.setVisible(false);

        }
        //teacher interfaces
        else
        {
            title.setText("<html><u>Search for Student</html>");
            title.setBounds(150, 5, 300, 20);
            l_search.setText("Name Search: ");
            Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
            tf_search.setEditable(true);
            tf_search.setBorder(b);
            inputCourse.setEditable(false);
            inputCourse.setVisible(false);

        }

        title.setFont(new Font("Default", Font.BOLD, 16));
        panel.add(title);
        l_search.setBounds(35,105,100,20);
        selectCourse.setBounds(35,105,100,20);
        panel.add(selectCourse);
        panel.add(l_search);
        panel.add(tf_search);

        //Label for grade
        l_grade = new JLabel(("Grade: "));
        l_grade.setBounds(35,275,100,20);
        panel.add(l_grade);

        //Label for attendance
        l_attendance = new JLabel(("Attendance: "));
        l_attendance.setBounds(35,300,100,20);
        panel.add(l_attendance);

        //Text field for grade
        tf_gr = new JTextField("");
        tf_gr.setBounds(140,275,70,20);
        panel.add(tf_gr);

        //Text field for attendance
        tf_att= new JTextField("");
        tf_att.setBounds(140,300,70,20);
        panel.add(tf_att);


        //JList to display search results
        resultList = new JList<String>(resultData);
        resultList.setBounds(140, 140, 250, 95);
        resultList.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        resultList.setVisible(false);

        //label to display result selected
        confirmSelectionL = new JLabel();
        confirmSelectionL.setBounds(35,240,350,35);

        //checking which mode to operate in, based on button pressed in teacher interface
        if(mode == "GR"){
            tf_att.setVisible(false);
            l_attendance.setVisible(false);

            tf_gr.setVisible(true);
            l_grade.setVisible(true);
        }
        else if(mode == "AT"){
            tf_att.setVisible(true);
            l_attendance.setVisible(true);

            tf_gr.setVisible(false);
            l_grade.setVisible(false);
        }
        //Admin priority
        else if ( mode =="BOTH")
        {
            tf_att.setVisible(true);
            l_attendance.setVisible(true);
            tf_gr.setVisible(true);
            l_grade.setVisible(true);
        }

        //listener for text input in search box (implementing suggestive search)
        tf_search.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                //checking if there's input in search field
                if(tf_search.getText().length()>0) {
                    resultData.clear();

                    if(mode == "GR" || mode == "AT") {
                        //fetching data from database that matches entered Name and displaying results in a JList
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            st = con.createStatement();
                            PreparedStatement statement = con.prepareStatement("SELECT S.userName, S.Name FROM SMSSytem.Takes T, SMSSytem.Student S where T.Student_ID = S.Student_ID and T.Course_ID = ? and S.Name like ?");
                            String enteredName = tf_search.getText();
                            statement.setString(1, CourseN);
                            statement.setString(2, "%" + enteredName.trim() + "%");
                            ResultSet rs = statement.executeQuery();
                            //System.out.print(enteredName + " ");
                            resultData.clear();
                            resultList.updateUI();
                            while (rs.next()) {
                                String foundName = "<html>" + rs.getString("Name") + ",&nbsp;&nbsp;&nbsp;&nbsp;" + "@" + "<i>" + rs.getString("userName") + "</i>" + "</html>";
                                confirmSelectionL.setVisible(false);
                                resultList.setVisible(true);
                                resultData.add(foundName);
                                resultList.updateUI();
                            }

                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                    else if(mode == "BOTH") {
                        //fetching data from database that matches entered Name and displaying results in a JList
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            st = con.createStatement();
                            PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, T.Grade, T.Attendance, C.Course_Name FROM SMSSytem.Takes T, SMSSytem.Course C, SMSSytem.Student S where T.Student_ID = S.Student_ID and T.Course_ID = C.Course_ID and S.userName = ? and T.Course_ID like ?");
                            String enteredName = tf_search.getText();
                            statement.setString(1, userN);
                            statement.setString(2, "%" + enteredName.trim() + "%");
                            ResultSet rs = statement.executeQuery();
                            //System.out.print(enteredName + " ");
                            resultData.clear();
                            resultList.updateUI();
                            while (rs.next()) {
                                foundName = "<html>" + rs.getString("Course_ID") + ",&nbsp;&nbsp;&nbsp;&nbsp;" + "@" + "<i>" + rs.getString("Course_Name") + "</i>" + "</html>";
                                confirmSelectionL.setVisible(false);
                                resultList.setVisible(true);
                                resultData.add(foundName);
                                resultList.updateUI();
                            }

                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }

                }
                //disabling and clearing search results if search box is empty
                else if(tf_search.getText().length()==0){
                    resultData.clear();
                    resultList.setVisible(false);
                }
            }
        });
        //listener for down arrow key move to search results from search box
        tf_search.addKeyListener(new KeyListener() {
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
                        tf_search.requestFocus();
                    }
                }
                //fetching selected user data when ENTER key is pushed
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    resultList.setVisible(false);
                    confirmSelectionL.setText("<html>" + " Edit and Press Confirm to Save: " + selectedName + "</html>");
                    if(mode != "BOTH") {
                        selectedName = ((selectedName.split("&nbsp;@")[1]).split("</i>")[0]).split("<i>")[1];
                    }
                    tf_search.grabFocus();
                    confirmSelectionL.setVisible(true);

                    if(mode == "BOTH"){
                        selectedName = selectedName.split(",")[0].split("<html>")[1];
                        CourseN = selectedName;
                        selectedName = userN;
                        System.out.print("IN BOTH");
                        System.out.print(selectedName + "\n");
                        System.out.print(CourseN + "\n");
                    }

                    try{
                        PreparedStatement statement = con.prepareStatement("SELECT T.Student_ID, T.Grade, T.Attendance FROM SMSSytem.Users U natural join SMSSytem.Student S natural join SMSSytem.Takes T where SMSSytem.T.Course_ID = ? and U.userName = ?;");
                        statement.setString(1, CourseN);
                        statement.setString(2, selectedName);
                        ResultSet rs = statement.executeQuery();
                        //Getting data out from the query
                        while (rs.next()) {
                            SID = rs.getString("Student_ID");
                            System.out.print("grade is" + "\n");
                            System.out.print(rs.getString("Grade") + "\n");
                            tf_gr.setText(rs.getString("Grade"));
                            tf_att.setText(rs.getString("Attendance"));
                        }
                    }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
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
                    System.out.print(selectedName + "\n");
                }
            }
        });

        //Enroll Buttons
        select = new JButton("Confirm");
        select.setBounds(45, 400, 150, 40);
        select.setBackground(new Color (25,100,205));
        select.setForeground(Color.white);
        select.addActionListener(new ActionListener() {
            //updating student data with entered values
            @Override
            public void actionPerformed(ActionEvent e) {
                //checking which filed to update based on operating mode (grade or attendance)

                //updating grade
                if (mode == "GR" || mode == "BOTH") {
                    //ensuring grade is not left blank
                    if (!tf_gr.getText().isBlank()) {
                        try {
                            pst2 = con.prepareStatement("UPDATE SMSSytem.Takes SET Grade = ? where Student_ID = ? and Course_ID = ?;");
                            pst2.setString(1, tf_gr.getText());
                            pst2.setString(2, SID);
                            pst2.setString(3, CourseN);
                            //Execute the update on the database
                            pst2.executeUpdate();
                            if(mode != "BOTH") {
                                Teacher T = new Teacher();
                                T.DisplayUserGUI(userN);
                                dispose();
                            }
                            else{
                                Admin A = new Admin();
                                A.DisplayUserGUI("Admin");
                                dispose();
                            }

                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }
                }
                //updating attendance
                if (mode == "AT" || mode == "BOTH") {
                    if(!tf_att.getText().isBlank()) {
                        try {
                            pst2 = con.prepareStatement("UPDATE SMSSytem.Takes SET Attendance = ? where Student_ID = ? and Course_ID = ?;");
                            pst2.setString(1, tf_att.getText());
                            pst2.setString(2, SID);
                            pst2.setString(3, CourseN);
                            //Execute the update on the database
                            pst2.executeUpdate();
                            if(mode != "BOTH") {
                                Teacher T = new Teacher();
                                T.DisplayUserGUI(userN);
                                dispose();
                            }
                            else{
                                Admin A = new Admin();
                                A.DisplayUserGUI("Admin");
                                dispose();
                            }

                        } catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }
                    }
                }
            }
        });
        panel.add(select);
        panel.add(resultList);

        //Cancel
        cancel = new JButton("Cancel");
        cancel.setBounds(260, 400, 150, 40);
        cancel.setBackground(new Color (25,100,205));
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == cancel && usrType == "Teacher")
                {
                    Teacher t = new Teacher();
                    t.DisplayUserGUI(userN);
                    dispose();
                }
                else if (e.getSource() == cancel && usrType == "Admin")
                {
                    Admin ad = new Admin();
                    ad.DisplayUserGUI("Admin");
                    dispose();
                }
            }
        });
        panel.add(cancel);
        panel.add(confirmSelectionL);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        this.addWindowListener(new WL());
    }

    //For Showing Messages after the Window has been disposed
    class WL implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you sure you want to exit?", null , JOptionPane.YES_OPTION);
            //For sure for closing and exiting the program
            if(output == JOptionPane.YES_OPTION)
            {
                if ( usrType == "Teacher")
                {
                    JOptionPane.showMessageDialog(null , "Return to Teacher Interface");
                    Teacher t = new Teacher();
                    t.DisplayUserGUI(userN);
                    dispose();
                }
                else if (usrType=="Admin")
                {
                    JOptionPane.showMessageDialog(null , "Return to Admin Interface");
                    Admin ad = new Admin();
                    ad.DisplayUserGUI("Admin");
                    dispose();
                }
            }
            else
            {
                //Display the Enrollment Interface
                SearchGUI SI = new SearchGUI(CourseN, userN, mode);
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
