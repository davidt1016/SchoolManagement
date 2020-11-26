package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //AccountGUI AGui = new AccountGUI();
         //AGui.Display();
        //TeacherGUI TGui = new TeacherGUI("AHAHAH");
        //AdminGUI AdGui = new AdminGUI();
       //StudentInterface SI= new StudentInterface("Test"); //Pass in username in this parameter to test

        //For new interface implementation methods
        //Teacher t = new Teacher();
        //t.DisplayUserGUI("teacher");
       Student s = new Student();
        s.DisplayUserGUI("Student");
        //Admin ad = new Admin();
        //ad.DisplayUserGUI("Admin");
/*
        table gui = new table();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(600, 200);
        gui.setVisible(true);
        gui.setTitle("TEST");*/

    }
}
