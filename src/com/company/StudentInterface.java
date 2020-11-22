package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class StudentInterface extends JFrame {

    private String[] term = {"2018 Fall","2018 Spring", "2019 Fall", "2019 Spring", "2020 Fall", "2020 Spring",
            "2021 Fall","2021 Spring", "2022 Fall", "2022 Spring", "2023 Fall", "2023 Spring",
            "2024 Fall","2024 Spring" };

    //For Account Panel
    private static JLabel l_image,l_atitle,l_id,l_name,l_username,l_dob,l_usertype,l_newpass,l_confirmpass,errorPass;
    private static JPasswordField newpass,confirmpass;
    private ImageIcon icon;
    private static JButton b_logoff,b_updatepass,b_confirm,b_cancel;
    private String PassWord;
    //For Grade Panel
    private static JLabel gradeTitle, SemesterGrade, emptyRecord, course, grade, overallGPA;
    private static JButton nextG, backG, overallG;
    //For Attendance Panel
    private static JLabel attendanceTitle, semesterAttendance, emptyAttendanceRecord;
    private static JButton nextA, backA;

    Container f;
    //**************Later need to put username and password in parameter in constructor to extract data***************
    //StudentInterface(String usrname, String passWord)
    StudentInterface(){
        setTitle("Student Interface");
        f = getContentPane();

        //Create instance of JPanel
        JPanel p=new JPanel();
        //For Grade Panel
        JPanel p_grade = new JPanel();
        //For Attendance Panel
        JPanel p_attend = new JPanel();

        p.setBounds(70,10,950,640);
        p.setBackground(Color.lightGray);
        f.add(p);

        //For account panel
        JPanel pa=new JPanel();
        pa.setBounds(165,10,620,250);
        pa.setBackground(Color.white);
        p.add(pa);

        //For grade panel
        p_grade.setBounds( 30, 290, 420, 320);
        p_grade.setBackground(Color.white);
        p.add(p_grade);
        p_grade.setLayout(null);
        //Grade Text
        grade = new JLabel("<html><u>GRADE</html>");
        grade.setFont(new Font("Default", Font.BOLD, 16));
        grade.setBounds(180, 5, 60,30);
        p_grade.add(grade);
        //Semester Options for Grade
        SemesterGrade = new JLabel("Term:");
        SemesterGrade.setFont(new Font("Default", Font.PLAIN, 14));
        SemesterGrade.setBounds(15,25, 70, 40);
        p_grade.add(SemesterGrade);

        //For attendance panel
        p_attend.setBounds( 500, 290, 420, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ATTENDANCE</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 5, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        semesterAttendance = new JLabel("Term:");
        semesterAttendance.setFont(new Font("Default", Font.PLAIN, 14));
        semesterAttendance.setBounds(15,25, 70, 40);
        p_attend.add(semesterAttendance);

        //Account UI
        l_image=new JLabel();
        icon = new ImageIcon(this.getClass().getResource("student.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0,1100,700);
        f.add(l_image);

        //Create instance of JLabel for input value
        l_atitle=new JLabel("<html><u>Welcome to Student Interface!</html>");
        l_atitle.setFont(new Font("Default", Font.BOLD, 20));
        l_atitle.setBounds(165,4, 350,30);
        l_id=new JLabel("Personal ID: ");
        l_id.setBounds(50,35, 150,30);
        l_name=new JLabel("Name: ");
        l_name.setBounds(50,75, 150,30);
        l_username=new JLabel("Username: ");
        l_username.setBounds(50,115,150,30);
        l_dob=new JLabel("Date of Birth: ");
        l_dob.setBounds(50,155,150,30);
        l_usertype=new JLabel("User Type: ");
        l_usertype.setBounds(50,195,150,30);
        l_newpass = new JLabel("");
        l_newpass.setBounds(350,85,150, 30);
        l_confirmpass = new JLabel("");
        l_confirmpass.setBounds(330,125,150, 30);
        errorPass = new JLabel("");
        errorPass.setFont(new Font("Default", Font.BOLD, 14));
        errorPass.setBounds(300,195,315, 30);
        pa.add(l_atitle);
        pa.add(l_id);
        pa.add(l_name);
        pa.add(l_username);
        pa.add(l_dob);
        pa.add(l_usertype);
        pa.add(l_newpass);
        pa.add(l_confirmpass);
        pa.add(errorPass);

        newpass = new JPasswordField();
        newpass.setBounds(460,90,150, 30);
        confirmpass = new JPasswordField();
        confirmpass.setBounds(460,130,150, 30);
        newpass.setEditable(false);
        confirmpass.setEditable(false);
        newpass.setBorder(new LineBorder(Color.white));
        confirmpass.setBorder(new LineBorder(Color.white));
        newpass.setBackground(Color.white);
        confirmpass.setBackground(Color.white);
        pa.add(newpass);
        pa.add(confirmpass);
        this.addWindowListener(new WL());

        b_logoff=new JButton("Log Off");
        b_logoff.setBounds(480,4,80, 30);
        b_logoff.setBackground(new Color (250,5,5));
        b_logoff.setForeground(Color.white);
        b_updatepass=new JButton("Update Password");
        b_updatepass.setBounds(400,45,140, 30);
        b_updatepass.setBackground(new Color (25,100,205));
        b_updatepass.setForeground(Color.white);
        b_confirm = new JButton("Confirm");
        b_confirm.setBounds(435,165,80, 30);
        b_confirm.setBackground(new Color (25,100,205));
        b_confirm.setForeground(Color.white);
        b_confirm.setEnabled(false);
        b_confirm.setVisible(false);
        b_cancel = new JButton("Cancel");
        b_cancel.setBounds(535,165,80, 30);
        b_cancel.setBackground(new Color (25,100,205));
        b_cancel.setForeground(Color.white);
        b_cancel.setEnabled(false);
        b_cancel.setVisible(false);
        pa.add(b_logoff);
        pa.add(b_updatepass);
        pa.add(b_confirm);
        pa.add(b_cancel);

        b_logoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gui GI = new Gui();
                dispose();
            }
        });

        b_updatepass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_newpass.setText("New Password: ");
                l_confirmpass.setText("Confirmed Password: ");
                newpass.setEditable(true);
                confirmpass.setEditable(true);
                newpass.setBorder(new LineBorder(Color.black));
                confirmpass.setBorder(new LineBorder(Color.black));
                b_confirm.setEnabled(true);
                b_confirm.setVisible(true);
                b_cancel.setEnabled(true);
                b_cancel.setVisible(true);
            }
        });

        b_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Validating the length of the password has to be at least 6 characters
                if ( newpass.getText().length() >= 6 && confirmpass.getText().length() >= 6)
                {
                    //Checking if password matches or not
                    //Password and confirmed Password match
                    //Password and confirmed Password match
                    if (Arrays.equals(newpass.getPassword(), confirmpass.getPassword()))
                    {
                        PassWord = newpass.getText();
                        errorPass.setText(" ");
                        JOptionPane.showMessageDialog(f, "Update Password Successfully!");
                        l_newpass.setText("");
                        l_confirmpass.setText("");
                        newpass.setText(null);
                        confirmpass.setText(null);
                        newpass.setEditable(false);
                        confirmpass.setEditable(false);
                        newpass.setBorder(new LineBorder(Color.white));
                        confirmpass.setBorder(new LineBorder(Color.white));
                        newpass.setBackground(Color.white);
                        confirmpass.setBackground(Color.white);
                        b_confirm.setEnabled(false);
                        b_confirm.setVisible(false);
                        b_cancel.setEnabled(false);
                        b_cancel.setVisible(false);
                    }
                    //Password and confirmed Password do not match
                    else
                    {
                        //Error occur with the confirmed password and the password doesnt match
                        newpass.setText(null);
                        confirmpass.setText(null);
                        errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
                    }
                }
                else{
                    //Error occur with the confirmed password and the password doesnt match
                    newpass.setText(null);
                    confirmpass.setText(null);
                    errorPass.setText("<html><font color='Red'>Password should be at least 6 characters! </font></html>");
                }
            }
        });

        b_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorPass.setText(" ");
                l_newpass.setText("");
                l_confirmpass.setText("");
                newpass.setText(null);
                confirmpass.setText(null);
                newpass.setEditable(false);
                confirmpass.setEditable(false);
                newpass.setBorder(new LineBorder(Color.white));
                confirmpass.setBorder(new LineBorder(Color.white));
                newpass.setBackground(Color.white);
                confirmpass.setBackground(Color.white);
                b_confirm.setEnabled(false);
                b_confirm.setVisible(false);
                b_cancel.setEnabled(false);
                b_cancel.setVisible(false);
            }
        });

        //1100 width and 700 height
        setSize(1100,700);

        //Use no layout managers
        p.setLayout(null);
        pa.setLayout(null);

        //Center frame on screen
        setLocationRelativeTo(null);

        //Let JFrame to close properly
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the frame visible
        setVisible(true);
    }

    //For Showing Messages after the Window has been disposed
    class WL implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                System.exit(1);
            }
            else{
                StudentInterface SI = new StudentInterface();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Return to Login Screen!");
            }
            else{
                StudentInterface SI = new StudentInterface();
            }
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
}
