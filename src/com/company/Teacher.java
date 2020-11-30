package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Vector;

public class Teacher extends JFrame implements UserInterfaceGUI {

    //Dynamic Array for Storing All the Possible Courses for each Student
    private Vector<String> courseEnrolled = new Vector<String>();
    //For selecting the course
    private static JComboBox coursesOptions;
    private static JComboBox courseOptionsAttendance;

    //For Account Panel
    private static JLabel l_image,l_atitle,l_id,l_name,l_username,l_dob,l_usertype,l_currentpass,l_newpass,l_confirmpass,errorPass;
    private static JPasswordField currentpass,newpass,confirmpass;
    private ImageIcon icon, refreshIcon;
    private static JButton b_logoff,b_updatepass,b_confirm,b_cancel;
    protected String newPassWord;

    //For Grade interface
    private static JLabel  CourseGrade, emptyRecord,  grade;
    private static JButton enroll_course, refreshG, edit_grade, confirmG, cancelG;
    //Verifying a course has been selected for Grade panel
    private Boolean isCourseGradeSelected = false;

    //For Attendance Panel
    private static JLabel attendanceTitle, CourseAttendance, emptyAttendanceRecord;
    private static JButton refreshA, edit_attendance, confirmA, cancelA;
    //Verifying a course has been selected for Attendance panel
    private Boolean isCourseAttendanceSelected = false;

    private String userN = " ";
    Container f;
    @Override
    public void setusrN(String userName) {
        this.userN = userName;
    }

    @Override
    public String getusrN() {
        return userN;
    }

    @Override
    public void DisplayUserGUI(String usersName) {
        userN = usersName;

        //Connection here with database to display information for Account, Grade, and Attendance
        //For combo box, a list of courses taken by the students
        courseEnrolled.add("-------------");
        courseEnrolled.add("   SHOW ALL  ");

        //Create instance of JPanel
        JPanel p=new JPanel();
        //For Grade Panel
        JPanel p_grade = new JPanel();
        //For Attendance Panel
        JPanel p_attend = new JPanel();

        setTitle("Teacher Interface");
        f = getContentPane();

        //Panel Interfaces
        p.setBounds(70,10,950,640);
        p.setBackground(Color.lightGray);
        f.add(p);

        //For account panel
        JPanel pa=new JPanel();
        pa.setBounds(165,10,620,250);
        pa.setBackground(Color.white);
        p.add(pa);

        //For grade panel
        p_grade.setBounds( 15, 290, 445, 320);
        p_grade.setBackground(Color.white);
        p.add(p_grade);
        p_grade.setLayout(null);
        //Grade Text
        grade = new JLabel("<html><u>GRADE</html>");
        grade.setFont(new Font("Default", Font.BOLD, 16));
        grade.setBounds(180, 2, 60,30);
        p_grade.add(grade);
        //Semester Options for Grade
        CourseGrade = new JLabel("Select a Course:");
        CourseGrade.setFont(new Font("Default", Font.PLAIN, 14));
        CourseGrade.setBounds(55,30, 250, 40);
        p_grade.add(CourseGrade);
        //For selecting courses----ComboBox
        coursesOptions = new JComboBox(courseEnrolled);
        coursesOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //When the course JCombobox is triggered
                if ( e.getSource() == coursesOptions )
                {
                    //No course has been selected
                    if (coursesOptions.getSelectedItem() == "-------------")
                    {
                        isCourseGradeSelected = false;

                    }
                    //a course or show all option has been selected
                    else
                    {
                        isCourseGradeSelected = true;
                    }
                }
            }
        });
        coursesOptions.setBounds(235, 30, 160, 40);
        p_grade.add(coursesOptions);
        //For refresh Button Icon
        refreshIcon = new ImageIcon(this.getClass().getResource("refresh.png"));
        Image imRef = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshIcon = new ImageIcon(imRef);
        //Refresh Button
        refreshG = new JButton(refreshIcon);
        //When there is no record
        emptyRecord = new JLabel("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.<br>" +
                "If you are not enrol in any course, please click ENROLL button to add course.</html>");
        emptyRecord.setBounds(60, 65, 300, 190);
        emptyRecord.setFont(new Font("Default", Font.PLAIN, 14));
        p_grade.add(emptyRecord);

        //For Edit Grade button
        edit_grade = new JButton("Edit Grade");
        edit_grade.setBounds(160, 270, 100, 40);
        confirmG = new JButton("Confirm");
        confirmG.setBounds(120,225,80,40);
        cancelG = new JButton("Cancel");
        cancelG.setBounds(220,225,80,40);
        confirmG.setEnabled(false);
        cancelG.setEnabled(false);
        confirmG.setVisible(false);
        cancelG.setVisible(false);
        p_grade.add(confirmG);
        p_grade.add(cancelG);
        p_grade.add(edit_grade);

        edit_grade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If Edit Grade is called
                confirmG.setEnabled(true);
                cancelG.setEnabled(true);
                confirmG.setVisible(true);
                cancelG.setVisible(true);
            }
        });

        cancelG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmG.setEnabled(false);
                cancelG.setEnabled(false);
                confirmG.setVisible(false);
                cancelG.setVisible(false);
            }
        });

        //Need Database Table
        confirmG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action Listener when the REFRESH ICON is CLICKED for grade
        refreshG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Refresh Button is clicked
                if ( e.getSource() == refreshG )
                {
                    //A course is selected, hide the emptyRecord messages
                    if ( isCourseGradeSelected )
                    {
                        emptyRecord.setText(" ");
                    }
                    //Display all of the courses taken and their grade
                    else
                    {

                    }
                }
            }
        });
        refreshG.setBounds(395, 40, 20, 20);
        p_grade.add(refreshG);

        //For Enroll Course button
        enroll_course = new JButton("Enroll Course");
        enroll_course.setBounds( 40,2,110, 30);
        enroll_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Triggered when overall Grade Button is clicked
                if (e.getSource() == enroll_course)
                {
                    //Display Enrol GUI
                    //EnrollmentGUI eGUI = new EnrollmentGUI(userN);
                }
            }
        });
        pa.add(enroll_course);

        //For attendance panel
        p_attend.setBounds( 490, 290, 445, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ABSENCES</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 2, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        CourseAttendance = new JLabel("Select a Course:");
        CourseAttendance.setFont(new Font("Default", Font.PLAIN, 14));
        CourseAttendance.setBounds(55,30, 270, 40);
        p_attend.add(CourseAttendance);
        //For selecting the course for Attendance
        courseOptionsAttendance = new JComboBox(courseEnrolled);
        courseOptionsAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == courseOptionsAttendance )
                {
                    //No course has been selected
                    if (courseOptionsAttendance.getSelectedItem() == "-------------")
                    {
                        isCourseAttendanceSelected = false;
                    }
                    //a course or show all option has been selected
                    else
                    {
                        isCourseAttendanceSelected = true;
                    }
                }
            }
        });

        //Empty Attendance Record
        emptyAttendanceRecord = new JLabel("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.</html>");
        emptyAttendanceRecord.setBounds(70, 100, 300, 150);
        emptyAttendanceRecord.setFont(new Font("Default", Font.PLAIN, 14));
        p_attend.add(emptyAttendanceRecord);

        //For Edit Attendance button
        edit_attendance = new JButton("Edit Absences");
        edit_attendance.setBounds(160, 270, 130, 40);
        confirmA = new JButton("Confirm");
        confirmA.setBounds(130,225,80,40);
        cancelA = new JButton("Cancel");
        cancelA.setBounds(230,225,80,40);
        confirmA.setEnabled(false);
        cancelA.setEnabled(false);
        confirmA.setVisible(false);
        cancelA.setVisible(false);
        p_attend.add(confirmA);
        p_attend.add(cancelA);
        p_attend.add(edit_attendance);

        edit_attendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If Edit Attendance is called
                confirmA.setEnabled(true);
                cancelA.setEnabled(true);
                confirmA.setVisible(true);
                cancelA.setVisible(true);
            }
        });

        cancelA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmA.setEnabled(false);
                cancelA.setEnabled(false);
                confirmA.setVisible(false);
                cancelA.setVisible(false);
            }
        });

        //Course Selections for Attendance
        courseOptionsAttendance.setBounds(235, 30, 160, 40);
        p_attend.add(courseOptionsAttendance);
        refreshA = new JButton(refreshIcon);
        refreshA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Refresh for attendance button is pressed
                if ( e.getSource()== refreshA)
                {
                    //a course has been selected or select ALL has been selected
                    if(isCourseAttendanceSelected)
                    {
                        emptyAttendanceRecord.setText(" ");
                    }
                    //None has been selected
                    else
                    {

                    }
                }

            }
        });
        refreshA.setBounds( 395, 40, 20, 20);
        p_attend.add(refreshA);

        //Image handling/Resizing
        l_image = new JLabel();
        icon = new ImageIcon(this.getClass().getResource("Office-Teacher.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700, Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0, 1100, 700);
        l_image.setBorder(null);
        f.add(l_image);

        //Create instance of JLabel for input value
        l_atitle=new JLabel("<html><u>Welcome to Teacher Interface!</html>");
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
        l_currentpass = new JLabel("");
        l_currentpass.setBounds(330,65,150, 30);
        l_newpass = new JLabel("");
        l_newpass.setBounds(350,105,150, 30);
        l_confirmpass = new JLabel("");
        l_confirmpass.setBounds(330,145,150, 30);
        errorPass = new JLabel("");
        errorPass.setFont(new Font("Default", Font.BOLD, 14));
        errorPass.setBounds(300,215,315, 30);
        pa.add(l_atitle);
        pa.add(l_id);
        pa.add(l_name);
        pa.add(l_username);
        pa.add(l_dob);
        pa.add(l_usertype);
        pa.add(l_currentpass);
        pa.add(l_newpass);
        pa.add(l_confirmpass);
        pa.add(errorPass);

        currentpass = new JPasswordField();
        currentpass.setBounds(460,70,150, 30);
        newpass = new JPasswordField();
        newpass.setBounds(460,110,150, 30);
        confirmpass = new JPasswordField();
        confirmpass.setBounds(460,150,150, 30);
        currentpass.setEditable(false);
        newpass.setEditable(false);
        confirmpass.setEditable(false);
        currentpass.setBorder(new LineBorder(Color.white));
        newpass.setBorder(new LineBorder(Color.white));
        confirmpass.setBorder(new LineBorder(Color.white));
        currentpass.setBackground(Color.white);
        newpass.setBackground(Color.white);
        confirmpass.setBackground(Color.white);
        pa.add(currentpass);
        pa.add(newpass);
        pa.add(confirmpass);
        this.addWindowListener(new WL());

        b_logoff=new JButton("Log Off");
        b_logoff.setBounds(480,2,80, 30);
        b_logoff.setBackground(new Color (250,5,5));
        b_logoff.setForeground(Color.white);
        b_updatepass=new JButton("Update Password");
        b_updatepass.setBounds(400,37,140, 30);
        b_updatepass.setBackground(new Color (25,100,205));
        b_updatepass.setForeground(Color.white);
        b_confirm = new JButton("Confirm");
        b_confirm.setBounds(435,185,80, 30);
        b_confirm.setBackground(new Color (25,100,205));
        b_confirm.setForeground(Color.white);
        b_confirm.setEnabled(false);
        b_confirm.setVisible(false);
        b_cancel = new JButton("Cancel");
        b_cancel.setBounds(535,185,80, 30);
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
                Gui GI = new Gui();
                dispose();
            }
        });

        b_updatepass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_currentpass.setText("Current Password: ");
                l_newpass.setText("New Password: ");
                l_confirmpass.setText("Confirmed Password: ");
                currentpass.setEditable(true);
                newpass.setEditable(true);
                confirmpass.setEditable(true);
                currentpass.setBorder(new LineBorder(Color.black));
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
                if ( currentpass.getText().length() >= 6 && newpass.getText().length() >= 6 && confirmpass.getText().length() >= 6)
                {
                    //Checking if password matches or not
                    //Password and confirmed Password match
                    //Password and confirmed Password match
                    if (Arrays.equals(newpass.getPassword(), confirmpass.getPassword()))
                    {
                        newPassWord = newpass.getText();
                        errorPass.setText(" ");
                        JOptionPane.showMessageDialog(f, "Update Password Successfully!");
                        l_currentpass.setText("");
                        l_newpass.setText("");
                        l_confirmpass.setText("");
                        currentpass.setText(null);
                        newpass.setText(null);
                        confirmpass.setText(null);
                        currentpass.setEditable(false);
                        newpass.setEditable(false);
                        confirmpass.setEditable(false);
                        currentpass.setBorder(new LineBorder(Color.white));
                        newpass.setBorder(new LineBorder(Color.white));
                        confirmpass.setBorder(new LineBorder(Color.white));
                        currentpass.setBackground(Color.white);
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
                        currentpass.setText(null);
                        newpass.setText(null);
                        confirmpass.setText(null);
                        errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
                    }
                }
                else{
                    //Error occur with the confirmed password and the password doesnt match
                    currentpass.setText(null);
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
                l_currentpass.setText("");
                l_newpass.setText("");
                l_confirmpass.setText("");
                currentpass.setText(null);
                newpass.setText(null);
                confirmpass.setText(null);
                currentpass.setEditable(false);
                newpass.setEditable(false);
                confirmpass.setEditable(false);
                currentpass.setBorder(new LineBorder(Color.white));
                newpass.setBorder(new LineBorder(Color.white));
                confirmpass.setBorder(new LineBorder(Color.white));
                currentpass.setBackground(Color.white);
                newpass.setBackground(Color.white);
                confirmpass.setBackground(Color.white);
                b_confirm.setEnabled(false);
                b_confirm.setVisible(false);
                b_cancel.setEnabled(false);
                b_cancel.setVisible(false);
            }
        });

        setSize(1100,700);
        //Use no layout managers
        p.setLayout(null);
        pa.setLayout(null);
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
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                System.exit(1);
            }
            else{
                Teacher t = new Teacher();
                t.DisplayUserGUI(userN);
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
                Teacher t = new Teacher();
                t.DisplayUserGUI(userN);
            }
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
