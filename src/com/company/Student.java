package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Vector;

public class Student extends JFrame implements UserInterfaceGUI {
    //Dynamic Array for Storing All the Possible Courses for each Student
    private Vector<String> courseEnrolled = new Vector<String>();
    //For selecting the course
    private static JComboBox coursesOptions;
    private static JComboBox courseOptionsAttendance;

    //For Account Panel
    private static JLabel l_image,l_atitle,l_id,l_name,l_username,l_dob,l_usertype,l_newpass,l_confirmpass,errorPass;
    private static JPasswordField newpass,confirmpass;
    private ImageIcon icon, refreshIcon;
    private static JButton b_logoff,b_updatepass,b_confirm,b_cancel;
    private String PassWord;
    //Test Table
    private String [] columnGrade = {"Course ID", "Course Name", "Grade"};
    private Object [][] dataGrade =
            { {"0001", "ENSC 351", "90%"},
                    {"0002", "ENSC 275", "88%"}  ,
                    {"0003", "CMPT 225", "80%"},
                    {"0004", "ENSC 110", "78%"},
                    {"0005", "ENSC 450", "99%"}
            };
    //For Grade interface
    private static JLabel gradeTitle, CourseGrade, emptyRecord, course, grade, overallGPA, refreshGrade;
    private static JButton overallG, refreshG, enrol;
    //Verifying a course has been selected for Grade panel
    private Boolean isCourseGradeSelected = false;
    private Boolean isSelectedAllGrade = false;
    private Boolean isSelectedAllAtten = false;
    private String GrCourse = " ";

    //For Attendance Panel
    private static JLabel attendanceTitle, CourseAttendance, emptyAttendanceRecord, refreshAttendance;
    private static JButton refreshA;
    //Verifying a course has been selected for Attendance panel
    private Boolean isCourseAttendanceSelected = false;
    private String AttCourse = " ";
    //For storing usrname
    private String usrN = " ";
    Container f;
    //Setter and getter for userName
    //Setter for UserName
    @Override
    public void setusrN(String userName) {
        this.usrN = userName;
    }
    //Getter for username
    @Override
    public String getusrN() {
        return usrN;
    }

    @Override
    public void DisplayUserGUI(String usersName) {
        usrN = usersName;


        //-----------------------------Database Connections Required----------------------
        /*Getting corresponding personal ID, Name, username, Date of Birth and Usertype and
        add it to Display. Current variable, usrN, is holding the username that is being passed in
        and it can be used to retrieve in the database. Display the corresponding data to corresponding
        field as shown from line 91 and onward
        */

        //--------------Database Connections Required to store Personal information------------------


        //According to what courses this specific student (username) has taken, add the course lists to the courseEnrolled
        //Retreive data (What course this student is taking)

        //Connection here with database to display information for Account, Grade, and Attendance
        //For combo box, a list of courses taken by the students
        //Retrieve a list of courses from Database and do "courseEnrolled.add(courses);"
        courseEnrolled.add("-------------");
        courseEnrolled.add("   SHOW ALL  ");

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


        //Create instance of JLabel for input value
        l_atitle=new JLabel("<html><u>Welcome to Student Interface!</html>");
        l_atitle.setFont(new Font("Default", Font.BOLD, 20));
        l_atitle.setBounds(165,4, 350,30);
        l_id=new JLabel("Personal ID: "); // Change this String to include User Personal ID such as "Personal ID: "+ {personalID retrieved from database}
        l_id.setBounds(50,35, 150,30);
        l_name=new JLabel("Name: "); // Change this String to include User Name such as "Name : "+ {Name retrieved from database}
        l_name.setBounds(50,75, 150,30);
        l_username=new JLabel("Username: "); // Change this String to include Username such as "Username: "+ {Username retrieved from database}
        l_username.setBounds(50,115,150,30);
        l_dob=new JLabel("Date of Birth: "); // Change this String to include Date of Birth such as "Date of Birth: "+ {Date of Birth retrieved from database}
        l_dob.setBounds(50,155,150,30);
        l_usertype=new JLabel("User Type: "); // Change this String to include User Type such as "User Type: "+ {Date of Birth retrieved from database}
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
                Gui GI = new Gui();
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


        //For grade panel
        p_grade.setBounds( 15, 290, 445, 320);
        p_grade.setBackground(Color.white);
        p.add(p_grade);
        p_grade.setLayout(null);
        //Grade Text
        grade = new JLabel("<html><u>GRADE</html>");
        grade.setFont(new Font("Default", Font.BOLD, 16));
        grade.setBounds(180, 5, 60,30);
        p_grade.add(grade);
        //Semester Options for Grade
        CourseGrade = new JLabel("Select a Course to Display Grade:");
        CourseGrade.setFont(new Font("Default", Font.PLAIN, 14));
        CourseGrade.setBounds(10,30, 250, 40);
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
                    //a course or show all options has been selected
                    else
                    {
                        isCourseGradeSelected = true;
                        //If Show all is selected->Display all grade
                        if (coursesOptions.getSelectedItem() == "   SHOW ALL  ")
                        {
                            isSelectedAllGrade = true;
                        }
                        //Show all is not selected
                        else
                        {
                            isSelectedAllGrade = false;
                            GrCourse = (String) coursesOptions.getSelectedItem();
                        }
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

        //For Enrol button
        enrol = new JButton("ENROLL");
        enrol.setBounds(250, 250, 100, 40);
        enrol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If enroll is called, then call another enroll interface
                if ( e.getSource() == enrol )
                {
                    //Display Enrol GUI
                    EnrollmentGUI eGUI = new  EnrollmentGUI(usrN);
                }
            }
        });
        p_grade.add(enrol);

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
                        overallGPA.setText(" ");
                        //conditions to check if student choose all or only a specific course has been chosen
                        if (isSelectedAllGrade)
                        {
                            //--------------------Database Connection Needed--------------
                            //A list of courses that student takes
                            //Extract all courses that this student take

                            /*Testing-show data properly
                            //Reference link for dynamic data insertion:
                            http://www.java2s.com/Tutorial/Java/0240__Swing/publicJTableVectorrowDataVectorcolumnNames.htm
                            JTable gradeT = new JTable(dataGrade, columnGrade );
                            gradeT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                                @Override
                                //Printing out the selected row that users have chosen
                                public void valueChanged(ListSelectionEvent e) {
                                    String Table_click = (gradeT.getModel().getValueAt(gradeT.getSelectedRow(), 1).toString());
                                    //System.out.println(gradeT.getValueAt(gradeT.getSelectedRow(), 1).toString());
                                    System.out.println(Table_click);
                                }
                            });
                            gradeT.setFillsViewportHeight(true);
                            JScrollPane scrollPane = new JScrollPane(gradeT);
                            scrollPane.setBounds( 25, 100, 400, 100);
                            p_grade.add(scrollPane);
                            */
                        }
                        else
                        {
                            //--------------------Database Connection Needed--------------
                            //Retrieve Records for a specific course. "GrCourse" variable can retrieve a specific course that
                            //user has chosen specifically
                        }
                    }
                    //Display error
                    else
                    {
                        emptyRecord.setText("<html>No Records Found. Please select a course to display or " +
                                "click SHOW ALL to display all <br> of the courses you have taken before.<br> " +
                                "If you are not enrol in any course, please click ENROLL button to add course.</html>");
                    }
                }
            }
        });
        refreshG.setBounds(395, 40, 20, 20);
        p_grade.add(refreshG);
        //Overall GPA Display
        overallGPA = new JLabel(" ");
        overallGPA.setBounds(10, 70, 240, 30);
        p_grade.add(overallGPA);

        //For overall GPA button
        overallG = new JButton("Overall GPA");
        overallG.setBounds( 70, 250, 100, 40);
        overallG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Triggered when overall Grade Button is clicked
                if (e.getSource() == overallG)
                {
                    //Displaying OVerall GPA message
                    emptyRecord.setText( " ");
                    //---------------Connection with Database---------------
                    //Extract Cummulative GPA for this particular student
                    overallGPA.setText("Your Cumulative (Overall) GPA is: ");
                }
            }
        });
        p_grade.add(overallG);

        //For attendance panel
        p_attend.setBounds( 490, 290, 445, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ATTENDANCE</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 5, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        CourseAttendance = new JLabel("Select a Course to Display Attendance:");
        CourseAttendance.setFont(new Font("Default", Font.PLAIN, 14));
        CourseAttendance.setBounds(5,30, 270, 40);
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
                        //All courses have been chosen to display attendance
                        if(courseOptionsAttendance.getSelectedItem() == "   SHOW ALL  ")
                        {
                            isSelectedAllAtten = true;
                        }
                        //A specific course has been chosen
                        else
                        {
                            isSelectedAllAtten = false;
                            AttCourse = (String) courseOptionsAttendance.getSelectedItem();
                        }
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

        //Course Selections for Attendance
        courseOptionsAttendance.setBounds(265, 30, 160, 40);
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
                        //Users have chosen to display all attendance records for each course(s) that he/she has taken so far
                        //ALL records
                        if (isSelectedAllAtten)
                        {
                            //--------------------------Database connection Needed----------------------
                            //Retrieve all attendance records for all of the courses that users have taken so far
                            //Records of all the courses that this student has taken and all the corresponding attendance record
                        }
                        //a specific course is selected and display attendance to that specific course only
                        else
                        {
                            //---------------------Database Connection Needed-------------
                            //Student has chosen a specific course and that specific option is stored in variable 'AttCourse'
                            //Retrieve attendance record specific to that course only

                        }
                    }
                    //None has been selected
                    else
                    {
                        emptyAttendanceRecord.setText("<html>No Records Found. Please select a course to display or " +
                                "click SHOW ALL to display all <br> of the courses you have taken before.</html>");
                    }
                }

            }
        });
        refreshA.setBounds( 423, 40, 20, 20);
        p_attend.add(refreshA);


        //Account UI
        l_image=new JLabel();
        icon = new ImageIcon(this.getClass().getResource("student.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0,1100,700);
        f.add(l_image);


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
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                System.exit(1);
            }
            else{
                Student s = new Student();
                s.DisplayUserGUI(usrN);
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
                Student s = new Student();
                s.DisplayUserGUI(usrN);
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
