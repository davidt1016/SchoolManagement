package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame{
    private static JLabel l_title,l_user,l_pass,l_image;
    private static JTextField tf_user;
    private static JButton b_login,b_register;
    private ImageIcon icon;
    private static JPasswordField pass;
    private static JLabel err_user,err_pass;
    Container f;
    private String username,password;

    Gui(){
        //Use Java Swing to create an input GUI
        //Create instance of JFrame
        //JFrame f=new JFrame("Login Screen");
        setTitle("Login Screen");
        f = getContentPane();
        //f.setLayout(new FlowLayout());

        //Create instance of JPanel
        JPanel p=new JPanel();
        p.setBounds(340,440,400,220);
        p.setBackground(Color.lightGray);
        f.add(p);

        l_image=new JLabel();
        icon = new ImageIcon(this.getClass().getResource("school.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0,1100,700);
        f.add(l_image);

        //Create instance of JLabel for input value
        l_title = new JLabel("<html><u>School Management System</html>");
        l_title.setFont(new Font("Default", Font.BOLD, 17));
        l_title.setBounds( 85, 1, 400, 30);
        l_user=new JLabel("Username: ");
        l_user.setBounds(50,30, 150,30);
        l_pass=new JLabel("Password: ");
        l_pass.setBounds(50,80, 150,30);
        p.add(l_title);
        p.add(l_user);
        p.add(l_pass);

        //Create instance of JTextField for input values
        tf_user=new JTextField();
        tf_user.setBounds(160,35,150,30);
        pass=new JPasswordField();
        pass.setBounds(160,85,150,30);
        p.add(tf_user);
        p.add(pass);

        //Create instance of JLabel for error message
        err_user = new JLabel("User log in error!");
        err_user.setBounds(100,170,150,30);
        err_user.setForeground(Color.RED);
        err_pass=new JLabel("Password log in error!");
        err_pass.setBounds(100,190,150,30);
        err_pass.setForeground(Color.RED);
        p.add(err_user);
        p.add(err_pass);

        //Create instance of JButton
        b_login=new JButton("Login");
        b_login.setBounds(60,135,90, 30);
        b_login.setBackground(new Color (25,100,205));
        b_login.setForeground(Color.white);
        b_register=new JButton("Register");
        b_register.setBounds(210,135,90, 30);
        b_register.setBackground(new Color (25,100,205));
        b_register.setForeground(Color.white);
        p.add(b_login);
        p.add(b_register);

        b_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //********* Login Credential Here **************//
                username = tf_user.getText();
                password = pass.getText();

                //Connection to Database Here


                StudentInterface SI = new StudentInterface(username);
                dispose();
            }
        });

        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountGUI AI = new AccountGUI();
                AI.Display();
                dispose();
            }
        });

        //1100 width and 700 height
        //f.setSize(1100,700);
        setSize(1100,700);

        //Use no layout managers
        //f.setLayout(null);
        p.setLayout(null);

        //Center frame on screen
        //f.setLocationRelativeTo(null);
        setLocationRelativeTo(null);

        //Let JFrame to close properly
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the frame visible
        //f.setVisible(true);
        setVisible(true);
    }
}


