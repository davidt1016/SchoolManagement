package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EnrollmentGUI extends JFrame{
    //Variable Initializations
    Container frame;
    private static JLabel title;
    private static JPanel panel, backgroundPanel;
    private static JButton enroll, cancel;
    private String userN = " ";

    //Setter and getter for userName
    //Setter for UserName
    public void setusrN ( String userName )
    {
        this.userN = userName;
    }
    //Getter for username
    public String getusrN()
    {
        return userN;
    }

    //Constructor
    EnrollmentGUI(String userName)
    {
        userN = userName;
        frame = getContentPane();
        setTitle("Course Enrollment");

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(25, 15, 450, 450);
        panel.setBackground(Color.white);
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 500, 500);
        backgroundPanel.setBackground(Color.LIGHT_GRAY);

        //Title for enrollment
        title = new JLabel("<html><u>COURSE ENROLLMENT</html>");
        title.setBounds(135, 5, 200, 20);
        title.setFont(new Font("Default", Font.BOLD, 16));
        panel.add(title);

        //------------------Data Connection Needed-----------------
        //List all the courses offered that is stored in the database


        //Enroll Buttons
        enroll = new JButton("ENROLL");
        enroll.setBounds(150, 400, 300, 40);
        enroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(enroll);
        //Cancel
        cancel = new JButton("Cancel");
        cancel.setBounds(0, 400, 150, 40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == cancel )
                {
                    Student s = new Student();
                    s.DisplayUserGUI(userN);
                    dispose();
                }

            }
        });
        panel.add(cancel);

        backgroundPanel.add(panel);
        frame.add(backgroundPanel);
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
            int output = JOptionPane.showConfirmDialog(frame , "Are you sure you want to exit?", null , JOptionPane.YES_OPTION);
            //For sure for closing and exiting the program
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Return to Student Interface");

            }
            else
            {
                //Display the Enrollment Interface
                EnrollmentGUI EGUI = new EnrollmentGUI(userN);
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
