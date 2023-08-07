/*
This method allows the user to input student information and save it to a database.
The user can add, update, delete, and view all database records.

Created by: Lily Mekeel
Date: April 23, 2023
 */
package com.example.sqlapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText name, surname, marks, id; //Edit texts
    private ListView studentList;  //List View
    private DatabaseHelper databaseHelper; //DatabaseHelper
    private StudentModel selectedStudent; //Selected student object for updates a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Changing the action bar color
        ActionBar ab = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(getColor(R.color.black));
        assert ab != null;
        ab.setBackgroundDrawable(cd);

        //List reference
        studentList = findViewById(R.id.studentList);
        //EditTexts references
        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        marks = findViewById(R.id.marksEditText);
        id = findViewById(R.id.idEditText);

        //Button references
        Button addData = findViewById(R.id.addDataButton);
        Button viewAll = findViewById(R.id.viewAllButton);
        Button update = findViewById(R.id.updateButton);
        Button delete = findViewById(R.id.deleteButton);

        //OnClickListeners

        //add data button on click listener
        addData.setOnClickListener(new View.OnClickListener() {
            StudentModel student; //student object declaration
            @Override
            public void onClick(View view) {
                //on try statement - student object initialize to Edit Text view values using class constructor
                try{
                    student = new StudentModel(name.getText().toString(), surname.getText().toString(), Integer.parseInt(marks.getText().toString()), Integer.parseInt(id.getText().toString()));
                    Toast.makeText(MainActivity.this, student.toString(), Toast.LENGTH_SHORT).show();

                //If issue occurs, toast message states error and values are set to default values
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
                    student = new StudentModel("n/a", "n/a", 0,-1);
                }

                //Database helper object
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                //student object is added to database
                boolean success = databaseHelper.addStudent(student);
                Toast.makeText(MainActivity.this, "success = " + success, Toast.LENGTH_SHORT).show(); //prints true if addStudent method returns true

            }
        });

        //View All button on click listener
        viewAll.setOnClickListener(view -> {
           databaseHelper = new DatabaseHelper(MainActivity.this);
            List<StudentModel> allStudents = databaseHelper.showAll(); //all students list equals database helper's showAll() list

            //Array adapter is set to the layout listView and allStudents list is displayed
            ArrayAdapter<StudentModel> studentArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allStudents);
            studentList.setAdapter((studentArrayAdapter));
        });

        //update button on click listener
        update.setOnClickListener(view -> {
            //Student object is initialized to updated student information
            StudentModel updatedStudent = new StudentModel(name.getText().toString(), surname.getText().toString(), Integer.parseInt(marks.getText().toString()), Integer.parseInt(id.getText().toString()));
            //Updated student object is passed into the database helper update method
            databaseHelper.updateOne(updatedStudent);
            Toast.makeText(MainActivity.this, "Updated " + updatedStudent, Toast.LENGTH_SHORT).show();
        });

        //list view on item click listener
        studentList.setOnItemClickListener((adapterView, view, i, l) -> {
                view.setBackgroundColor(getColor(R.color.teal_200)); //changes color of clicked item
                selectedStudent = (StudentModel) adapterView.getItemAtPosition(i); //sets selectedStudent variable to the clicked list item
        });

        //Delete button on click listener
        delete.setOnClickListener(view -> {
            databaseHelper.deleteOne(selectedStudent); //Takes the selected student from the list item click listener and calls the database helper delete method
            Toast.makeText(MainActivity.this, "Deleted " + selectedStudent.toString(), Toast.LENGTH_SHORT).show(); //Toast shows deleted student
        });



    }
}