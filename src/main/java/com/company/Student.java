package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class Student extends JFrame implements UserInterfaceGUI {
    Connection con;
    PreparedStatement pst;
    Statement st;
    //Dynamic Array for Storing All the Possible Courses for each Student
    private Vector <String> courseEnrolled = new Vector<String>();
    //For selecting the course
    private static JComboBox coursesOptions;
    private static JComboBox courseOptionsAttendance;

    //For Account Panel
    private static JLabel l_image,l_atitle,l_id,l_name,l_username,l_dob,l_usertype, l_currentpass, l_newpass,l_confirmpass,errorPass;
    public static JPasswordField currentpass, newpass,confirmpass;
    private ImageIcon icon, refreshIcon;
    public static JButton b_logoff,b_updatepass,b_confirm,b_cancel;
    private String newPassWord;

    //For Grade interface
    private static JLabel  CourseGrade, emptyRecord, grade, overallGPA;
    private static JButton overallG, refreshG, enrol;
    //Verifying a course has been selected for Grade panel
    private Boolean isCourseGradeSelected = false;
    private Boolean isSelectedAllGrade = false;
    private Boolean isSelectedAllAtten = false;
    private String GrCourse = " ";
    //For Attendance Panel
    private static JLabel attendanceTitle, CourseAttendance, emptyAttendanceRecord;
    private static JButton refreshA;
    //Verifying a course has been selected for Attendance panel
    private Boolean isCourseAttendanceSelected = false;
    private String AttCourse = " ";
    private String[] AttCourseParts;
    private String[] GrCourseParts;
    //For storing usrname
    private String usrN = " ";
    Container f;
    private String PID;
    private String SID;
    private String Name;
    private String DOB;
    private String currPass;

    private String Courses;
    private String CoursesID;
    private String Grades;
    private String Attendances;

    private String Course;
    private String CourseID;
    private String Grade;
    private String cGPA_s = "";

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
        courseEnrolled.add("-------------");

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

        //fetching user info
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            st = con.createStatement();
            PreparedStatement statement = con.prepareStatement("SELECT Student_ID, Name, DOB FROM SMSSytem.Student where username = ?;");
            statement.setString(1, usrN);
            ResultSet rs = statement.executeQuery();
            //Getting data out from the query
            while (rs.next()) {
                SID = rs.getString("Student_ID");
                PID = usrN + SID;
                Name = rs.getString("Name");
                DOB = rs.getString("DOB");
                System.out.print("usrName: " + usrN);
            }
        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        //fetching courses for drop down list
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            //st = con.createStatement();
            PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, C.Course_Name, C.Teacher_ID, T.Grade, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
            statement.setString(1, SID);
            ResultSet rs = statement.executeQuery();
            //Getting data out from the query
            while (rs.next()) {
                Courses = rs.getString("Course_Name");
                CoursesID = rs.getString("Course_ID");
                Grades = rs.getString("Grade");
                Attendances = rs.getString("Attendance");

                System.out.print(Courses + " ");
                courseEnrolled.add(CoursesID + ": " + Courses);
            }
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }



        courseEnrolled.add("-------------");
        courseEnrolled.add("All COURSES");

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
        l_id=new JLabel("Personal ID: "); //{personalID
        l_id.setBounds(50,35, 150,30);
        l_name=new JLabel("Name: "); //  "Name :
        l_name.setBounds(50,75, 150,30);
        l_username=new JLabel("Username: "); //  "Username:
        l_username.setBounds(50,115,150,30);
        l_dob=new JLabel("Date of Birth: "); // {Date of Birth retrieved from database}
        l_dob.setBounds(50,155,250,30);
        l_usertype=new JLabel("User Type: "); // "User Type:
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

        l_name.setText(l_name.getText()+" "+Name);
        l_username.setText(l_username.getText()+" "+usrN);
        l_dob.setText(l_dob.getText()+" "+DOB);
        l_id.setText(l_id.getText()+" "+PID);
        l_usertype.setText(l_usertype.getText()+" Student");

        newpass = new JPasswordField();
        newpass.setBounds(460,110,150, 30);
        confirmpass = new JPasswordField();
        confirmpass.setBounds(460,150,150, 30);
        newpass.setEditable(false);
        confirmpass.setEditable(false);
        newpass.setBorder(new LineBorder(Color.white));
        confirmpass.setBorder(new LineBorder(Color.white));
        newpass.setBackground(Color.white);
        confirmpass.setBackground(Color.white);
        pa.add(newpass);
        pa.add(confirmpass);

        currentpass = new JPasswordField();
        currentpass.setBounds(460,70,150, 30);
        currentpass.setEditable(false);
        currentpass.setBorder(new LineBorder(Color.white));
        currentpass.setBackground(Color.white);
        pa.add(currentpass);

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
                int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
                if(output == JOptionPane.YES_OPTION)
                {
                    Gui GI = new Gui();
                    dispose();
                    JOptionPane.showMessageDialog(null , "Return to Login Screen!");
                }
                else {
                    Student s = new Student();
                    s.DisplayUserGUI(usrN);
                    dispose();
                }
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
                    //Checking if passwords match or not
                    //Password and confirmed Password match
                    if (Arrays.equals(newpass.getPassword(), confirmpass.getPassword()))
                    {
                        //fetching old password from database
                        try{
                            st = con.createStatement();
                            //fetching username
                            PreparedStatement statement = con.prepareStatement("SELECT password FROM SMSSytem.Users where username = ?;");
                            statement.setString(1, usrN);
                            ResultSet rs = statement.executeQuery();

                            while (rs.next()) {
                                currPass = rs.getString("password");
                            }
                        }catch (SQLException other_SQLException) {
                            other_SQLException.printStackTrace();
                        }

                        //comparing old password with the entered one and updating it if they match
                        String currentpassS = new String(currentpass.getPassword());
                        String newpassS = new String(newpass.getPassword());
                        System.out.print(currentpassS+"               ;");
                        System.out.print(currPass+"               ;");
                        //updating password in database
                        if(currentpassS.equals(currPass)){
                            try{
                                pst = con.prepareStatement("UPDATE SMSSytem.Users SET password = ? where username = ?");
                                pst.setString(2, usrN );
                                pst.setString(1, newpassS);
                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(f, "Update Password Successfully!");
                            }catch (SQLException other_SQLException){
                                other_SQLException.printStackTrace();
                            }

                        }
                        else{
                            System.out.print("Current Password Doesn't Match Our Records ");
                            JOptionPane.showMessageDialog(f, "Wrong Password Entered!");
                        }


                        newPassWord = newpass.getText();
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
                        if (coursesOptions.getSelectedItem() == "All COURSES")
                        {
                            isSelectedAllGrade = true;
                        }
                        //Show all is not selected
                        else
                        {
                            isSelectedAllGrade = false;
                            GrCourse = (String) coursesOptions.getSelectedItem();
                            GrCourseParts = GrCourse.split("\\:");
                            GrCourse = GrCourseParts[0];
                            System.out.print(GrCourse);
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
        enrol.setBackground(new Color (25,100,205));
        enrol.setForeground(Color.white);
        enrol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If enroll is called, then call another enroll interface
                if ( e.getSource() == enrol )
                {
                    //Display Enrol GUI
                    EnrollmentGUI eGUI = new  EnrollmentGUI(usrN, Integer.parseInt(SID));
                    dispose();
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
                            emptyRecord.setText("");
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, C.Course_Name, T.Grade FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
                                statement.setString(1, SID);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                emptyRecord.setText("");
                                String GradeList = "";
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    CoursesID = rs.getString("Course_ID");
                                    Grade = rs.getString("Grade");
                                    //latestfix2611 checking null value
                                    if (Grade == null){
                                        Grade = "N/A";
                                    }
                                    else{
                                        Grade = Grade + "%";
                                    }


                                    System.out.print("ALL " + Courses + " ");
                                    GradeList = GradeList +CoursesID + ": " + Course + ": " + Grade + "<br/>";

                                    //latestfix2611END
                                }
                                //Check Compatibility for ScrollPane
                                //Edit here
                                emptyRecord.setText("<html>" + GradeList + "</html>");
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }

                        }
                        else
                        {
                            //Retrieve Records for a specific course. "GrCourse" variable can retrieve a specific course that
                            //user has chosen specifically
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, C.Course_Name, T.Grade FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.C.Course_ID = ?");
                                statement.setString(1, SID);
                                statement.setString(2, GrCourse);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    CourseID = rs.getString("Course_ID");
                                    Grade = rs.getString("Grade");
                                    //latestfix2611 checking null value
                                    if (Grade == null){
                                        Grade = "N/A";
                                    }
                                    else{
                                        Grade = Grade + "%";
                                    }

                                    System.out.print(Courses + " ");
                                    emptyRecord.setText("");
                                    emptyRecord.setText(emptyRecord.getText() + CourseID + ": " + Course + ": " + Grade);
                                    //latestfix2611END
                                }
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }
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
        overallGPA.setBounds(10, 70, 280, 30);
        p_grade.add(overallGPA);

        //For overall GPA button
        overallG = new JButton("Overall GPA");
        overallG.setBounds( 65, 250, 120, 40);
        overallG.setBackground(new Color (25,100,205));
        overallG.setForeground(Color.white);
        overallG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Triggered when overall Grade Button is clicked
                if (e.getSource() == overallG)
                {
                    //Displaying OVerall GPA message
                    emptyRecord.setText( " ");
                    //---------------Connection with Database---------------
                    //Extracting Cummulative GPA for this particular student

                    try{
                        //latestfix2611 changed the query
                        PreparedStatement statement = con.prepareStatement("SELECT count(T.Course_ID) as numCourses, sum(T.Grade) as gradeSum FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.T.Grade is not Null");
                        //latestfix2611END
                        statement.setString(1, SID);
                        ResultSet rs = statement.executeQuery();
                        //Getting data out from the query
                        overallGPA.setText("");
                        Float cGPA_f;
                        Float numCourses;
                        Float gradeSum;
                        while (rs.next()) {
                            numCourses = Float.parseFloat(rs.getString("numCourses"));
                            gradeSum = Float.parseFloat(rs.getString("gradeSum"));
                            cGPA_f = 4.33f * (gradeSum/numCourses)/100;
                            cGPA_s = String.format("%.2f", cGPA_f);


                        }
                    }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
                    }


                    overallGPA.setText("Your Cumulative (Overall) GPA is: " + cGPA_s + "/4.33");
                }
            }
        });
        p_grade.add(overallG);

        //For attendance panel
        p_attend.setBounds( 490, 290, 445, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ABSENCES</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 5, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        CourseAttendance = new JLabel("Select a Course to Display Absences:");
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
                        if(courseOptionsAttendance.getSelectedItem() == "All COURSES")
                        {
                            isSelectedAllAtten = true;
                        }
                        //A specific course has been chosen
                        else
                        {
                            isSelectedAllAtten = false;
                            AttCourse = (String) courseOptionsAttendance.getSelectedItem();
                            AttCourseParts = AttCourse.split("\\:");
                            AttCourse = AttCourseParts[0];
                            System.out.print(AttCourse);
                        }
                    }
                }
            }
        });

        //Empty Attendance Record
        emptyAttendanceRecord = new JLabel("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.</html>");
        emptyAttendanceRecord.setBounds(60, 65, 300, 190);
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
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, C.Course_Name, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
                                statement.setString(1, SID);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                emptyAttendanceRecord.setText("");
                                String AttendanceList = "";
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    CoursesID = rs.getString("Course_ID");
                                    Attendances = rs.getString("Attendance");
                                    //latestfix2611 checking null value
                                    if (Attendances == null){
                                        Attendances = "N/A";
                                    }

                                    System.out.print("ALL " + Courses + " ");
                                    AttendanceList = AttendanceList + CoursesID + ": " + Course + ": " + Attendances + "<br/>";
                                    //latestfix2611END
                                }
                                emptyAttendanceRecord.setText("<html>" + AttendanceList + "</html>");
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }
                        }
                        //a specific course is selected and display attendance to that specific course only
                        else
                        {
                            //Student has chosen a specific course and that specific option is stored in variable 'AttCourse'
                            //Retrieve attendance record specific to that course only
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_ID, C.Course_Name, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.C.Course_ID = ?");
                                statement.setString(1, SID);
                                statement.setString(2, AttCourse);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    CourseID = rs.getString("Course_ID");
                                    Attendances = rs.getString("Attendance");
                                    //latestfix2611  checking null value
                                    if (Attendances == null){
                                        Attendances = "N/A";
                                    }

                                    System.out.print(Courses + " ");
                                    emptyAttendanceRecord.setText("");
                                    emptyAttendanceRecord.setText(emptyAttendanceRecord.getText() + CourseID + ": " + Course + ": " + Attendances);
                                    //latestfix2611END
                                }
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }

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
                Student SI = new Student();
                SI.DisplayUserGUI(usrN);
                dispose();
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {
            /*
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Return to Login Screen!");
            }
            else{
                Student SI = new Student();
                SI.DisplayUserGUI(usrN);
            }

             */
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
