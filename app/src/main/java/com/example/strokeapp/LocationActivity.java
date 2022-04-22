package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private ImageButton homeButton, calendarButton, rehabButton, profileButton;
    private TextView [] locTV, objTV;
    private EditText [] locET, objET;
    private Button editButton;
    private boolean edit;
    SharedPreferences items;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locTV = new TextView[3];
        objTV = new TextView[3];
        locET = new EditText[3];
        objET = new EditText[3];
        items = getSharedPreferences("list", Context.MODE_PRIVATE);
        editor = this.items.edit();

        locTV[0] = (TextView) findViewById(R.id.loc1);
        objTV[0] = (TextView) findViewById(R.id.obj1);
        locTV[1] = (TextView) findViewById(R.id.loc2);
        objTV[1] = (TextView) findViewById(R.id.obj2);
        locTV[2] = (TextView) findViewById(R.id.loc3);
        objTV[2] = (TextView) findViewById(R.id.obj3);
        objET[0] = (EditText) findViewById(R.id.objET1);
        locET[0] = (EditText) findViewById(R.id.locET1);
        objET[1] = (EditText) findViewById(R.id.objET2);
        locET[1] = (EditText) findViewById(R.id.locET2);
        objET[2] = (EditText) findViewById(R.id.objET3);
        locET[2] = (EditText) findViewById(R.id.locET3);
        editButton = (Button) findViewById(R.id.editB);
        edit = false;
        setEdit(edit);
        setEditTexts();
        setText();

        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEdit(edit);
                if(edit){
                    editButton.setText("Edit");
                    setEdit(false);
                    editMem();
                    setText();
                }
                else{
                    setEdit(true);
                    editButton.setText("Done");
                }
                edit = !edit;
            }
        });
    }

    public void setEdit(boolean choice){
        if(choice){
            for(int i=0;i<locTV.length;i++){
                locTV[i].setVisibility(View.INVISIBLE);
                objTV[i].setVisibility(View.INVISIBLE);
                locET[i].setEnabled(true);
                objET[i].setEnabled(true);
                locET[i].setVisibility(View.VISIBLE);
                objET[i].setVisibility(View.VISIBLE);
            }
        }
        else{
            for(int i=0;i<locTV.length;i++){
                locTV[i].setVisibility(View.VISIBLE);
                objTV[i].setVisibility(View.VISIBLE);
                locET[i].setEnabled(false);
                objET[i].setEnabled(false);
                locET[i].setVisibility(View.INVISIBLE);
                objET[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setText() {
        for(int i=0;i<locTV.length;i++){
            String obj = items.getString("item"+i, "");
            String loc = items.getString("loc"+i, "");
            locTV[i].setText(loc);
            objTV[i].setText(obj);
        }
    }

    public void editMem(){
        for(int i=0;i<locTV.length;i++){
            String obj = objET[i].getText().toString();
            if(obj.equals(""))
                obj = items.getString("item"+i, "");
            String loc = locET[i].getText().toString();
            if(loc.equals(""))
                loc = items.getString("loc"+i, "");
            editor.putString("item"+i, obj);
            editor.putString("loc"+i, loc);
            editor.apply();
        }
    }

    public void setEditTexts(){
        for(int i=0;i<locTV.length;i++){
            String obj = items.getString("item"+i, "");
            String loc = items.getString("loc"+i, "");
            locET[i].setText(loc);
            objET[i].setText(obj);
        }
    }
}