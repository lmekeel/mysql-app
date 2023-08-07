//This is class is the database helper class which holds the methods for adding, viewing, updating, and deleting records

package com.example.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Declaring the database table and column names
    public static final String STUDENT_TABLE = "STUDENT_TABLE";  //Student table
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME"; //name column
    public static final String COLUMN_STUDENT_SURNAME = "STUDENT_SURNAME"; //surname column
    public static final String COLUMN_STUDENT_MARKS = "STUDENT_MARKS"; //marks column
    public static final String COLUMN_ID = "ID"; //id column

    //Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    //Creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + " (" + COLUMN_ID + " INT," + COLUMN_STUDENT_NAME + " TEXT, " + COLUMN_STUDENT_SURNAME + " TEXT, " + COLUMN_STUDENT_MARKS + " INT)";
        db.execSQL(createTableStatement);
    }

    //Empty method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Add student method
    public boolean addStudent(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //Getting content values from the passed in object's getter methods
        cv.put(COLUMN_STUDENT_NAME, studentModel.getStudentName());
        cv.put(COLUMN_STUDENT_SURNAME, studentModel.getStudentSurname());
        cv.put(COLUMN_STUDENT_MARKS, studentModel.getStudentMarks());
        cv.put(COLUMN_ID, studentModel.getStudentID());

        //inserts the student into the database student table
        long insert = db.insert(STUDENT_TABLE,null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    //View all method
    public List<StudentModel> showAll(){
        List<StudentModel> returnList = new ArrayList<>(); //List to return from method

        //query to get data from the database
        String queryString = "SELECT * FROM " + STUDENT_TABLE;


        SQLiteDatabase db = this.getReadableDatabase(); //only reads from database
        Cursor cursor = db.rawQuery(queryString, null); //uses query on the database object
        if(cursor.moveToFirst()){
            //
            do{
                //Gets values from the database based on the column number
                int studentID = cursor.getInt(0);
                String studentName = cursor.getString(1);
                String studentSurname = cursor.getString(2);
                int studentMarks = cursor.getInt(3);

                //Creates student object to store the values
                StudentModel student = new StudentModel(studentName, studentSurname, studentMarks, studentID);
                //Adds student to the return list
                returnList.add(student);

            }while(cursor.moveToNext());
        }else{
            //Empty
        }
        cursor.close();
        db.close();
        return returnList; //returns list of students from the database
    }


    //Delete record method
    public boolean deleteOne(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete record query
        String queryString = "DELETE FROM " + STUDENT_TABLE + " WHERE " + COLUMN_ID + " = " + studentModel.getStudentID();
        //Executes query in the database
        Cursor cursor = db.rawQuery(queryString, null);
        //If student is deleted returns true, else false
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    //Update record method
    public void updateOne(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        //Sets values to the user's new input
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME, studentModel.getStudentName());
        cv.put(COLUMN_STUDENT_SURNAME, studentModel.getStudentSurname());
        cv.put(COLUMN_STUDENT_MARKS, studentModel.getStudentMarks());
        cv.put(COLUMN_ID, studentModel.getStudentID());

        //updates the student with matching ID to the cv values
        db.update(STUDENT_TABLE, cv, COLUMN_ID + " = " + studentModel.studentID, null);
        db.close();
    }
}
