//This is the Student class that models student objects
package com.example.sqlapp;

public class StudentModel {

     //class attributes
     String studentName;
     String studentSurname;
     int studentMarks;
     int studentID;

    //constructor
    public StudentModel(String studentName, String studentSurname, int studentMarks, int studentID) {
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.studentMarks = studentMarks;
        this.studentID = studentID;
    }

    //toString method
    @Override
    public String toString() {
        return  " ID: " + studentID +
                " " + studentSurname +
                ", " + studentName + " " +
                "  marks: " + studentMarks

                ;
    }


    //Getters
    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public int getStudentMarks() {
        return studentMarks;
    }

    public int getStudentID() {
        return studentID;
    }

}
