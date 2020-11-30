package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SearchGUI extends JFrame{

    //Variable Initializations
    Container f;
    private static JLabel title,l_search;
    private static JTextField tf_search;
    private static JPanel panel, backgroundPanel;
    private static JButton select, cancel;
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

    SearchGUI(String userName){
        userN = userName;

        userN = userName;
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
        title = new JLabel("<html><u>Search for Student</html>");
        title.setBounds(150, 5, 200, 20);
        title.setFont(new Font("Default", Font.BOLD, 16));
        panel.add(title);

        //Label for search
        l_search = new JLabel(("Name Search: "));
        l_search.setBounds(85,105,100,20);
        panel.add(l_search);

        //Text field for search
        tf_search = new JTextField("");
        tf_search.setBounds(190,100,150,30);
        panel.add(tf_search);

        //Enroll Buttons
        select = new JButton("Select");
        select.setBounds(150, 400, 300, 40);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(select);

        //Cancel
        cancel = new JButton("Cancel");
        cancel.setBounds(0, 400, 150, 40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == cancel )
                {
                    Teacher t = new Teacher();
                    t.DisplayUserGUI(userN);
                    dispose();
                }
            }
        });
        panel.add(cancel);

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
                JOptionPane.showMessageDialog(null , "Return to Teacher Interface");
                Teacher t = new Teacher();
                t.DisplayUserGUI(userN);
            }
            else
            {
                //Display the Enrollment Interface
                SearchGUI SI = new SearchGUI(userN);
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
