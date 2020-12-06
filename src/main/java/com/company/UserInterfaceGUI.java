package com.company;

public interface UserInterfaceGUI {
    //Setter function for setting username
    public void setusrN ( String userName );
    //Getter function for obtaining username
    public String getusrN();
    //Display corresponding user interface according to user type that links to the username
    public void DisplayUserGUI(String usersName);
}
