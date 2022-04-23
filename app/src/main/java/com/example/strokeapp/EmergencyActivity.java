package com.example.strokeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class EmergencyActivity extends AppCompatActivity {


    private Button editButton, addButton, phoneB1, phoneB2, phoneB3, phoneB4, phoneB5, phoneB6;
    private EditText phone1, phone2, phone3, phone4, phone5, phone6, phoneT4, phoneT5, phoneT6;
    private TextView contact4, contact5, contact6 ;
    private ImageButton homeB;
    private boolean clicked=false;
    SharedPreferences phoneNums;
    SharedPreferences.Editor editor;
    private static final int REQUEST_CALL = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNums = getSharedPreferences("contacts", Context.MODE_PRIVATE);
        editor = this.phoneNums.edit();
        setContentView(R.layout.activity_emergency);
        addButton = findViewById(R.id.addButton2);
        editButton = findViewById(R.id.editButton);
        phoneB1 = findViewById(R.id.phoneB1);
        phoneB2 = findViewById(R.id.phoneB2);
        phoneB3 = findViewById(R.id.phoneB3);
        phoneB4 = findViewById(R.id.phoneB4);
        phoneB5 = findViewById(R.id.phoneB5);
        phoneB6 = findViewById(R.id.phoneB6);

        homeB = findViewById(R.id.homeButtonE);

        contact4 = findViewById(R.id.contactText4);
        contact5 = findViewById(R.id.contactText5);
        contact6 = findViewById(R.id.contactText6);
        phone1 = findViewById(R.id.phone1);
        phone2 = findViewById(R.id.phone2);
        phone3 = findViewById(R.id.phone3);
        phone4 = findViewById(R.id.phone4);
        phone5 = findViewById(R.id.phone5);
        phone6 = findViewById(R.id.phone6);
        phoneT4 = findViewById(R.id.phoneT4);
        phoneT5 = findViewById(R.id.phoneT5);
        phoneT6 = findViewById(R.id.phoneT6);
        phoneB1.setVisibility(View.INVISIBLE);
        phoneB2.setVisibility(View.INVISIBLE);
        phoneB3.setVisibility(View.INVISIBLE);
        phoneB4.setVisibility(View.INVISIBLE);
        setData(phoneNums);
        changeEdit(1);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked==false) {
                    editButton.setText("Done");
                    changeEdit(0);
                }
                else {
                    editButton.setText("Edit");
                    changeEdit(1);
                    setData(phoneNums);
                }
                clicked=!clicked;
            }
        });

        homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmergencyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String extraNum = phoneNums.getString("numExtra", "0");
                if (extraNum.equals("0")) {
                    editor.putString("numExtra", "1");
                    editor.apply();
                }
                else if (extraNum.equals("1")) {
                    editor.putString("numExtra", "2");
                    editor.apply();
                }
                else if (extraNum.equals("2")) {
                    editor.putString("numExtra", "3");
                    editor.apply();
                }
                changeEdit(0);
            }
        });

        phoneB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN1", "");
                makeCall(num);
            }
        });
        phoneB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN2", "");
                makeCall(num);
            }
        });
        phoneB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN3", "");
                makeCall(num);
            }
        });
        phoneB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN4", "");
                makeCall(num);
            }
        });
        phoneB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN5", "");
                makeCall(num);
            }
        });
        phoneB6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNums.getString("phoneN6", "");
                makeCall(num);
            }
        });

    }

    public void makeCall(String num){
        if (ContextCompat.checkSelfPermission( EmergencyActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EmergencyActivity.this,
                    new String [] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else {
            if (num.length() == 10) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                startActivity(callIntent);
            }
        }
    }




    public void changeEdit(int num){
        if(num==1){
            phone1.setEnabled(false);
            phone2.setEnabled(false);
            phone3.setEnabled(false);
            phoneB1.setEnabled(true);
            phoneB2.setEnabled(true);
            phoneB3.setEnabled(true);
            phoneB1.setVisibility(View.VISIBLE);
            phoneB2.setVisibility(View.VISIBLE);
            phoneB3.setVisibility(View.VISIBLE);
            if(phoneNums.getString("numExtra", "").equals("1")) {
                phoneB4.setVisibility(View.VISIBLE);
                phoneB4.setEnabled(true);
                phoneB5.setVisibility(View.INVISIBLE);
                phoneB5.setEnabled(false);
                phoneB6.setVisibility(View.INVISIBLE);
                phoneB6.setEnabled(false);
            }
            else if(phoneNums.getString("numExtra", "").equals("2")) {
                phoneB4.setVisibility(View.VISIBLE);
                phoneB4.setEnabled(true);
                phoneB5.setVisibility(View.VISIBLE);
                phoneB5.setEnabled(true);
                phoneB6.setVisibility(View.INVISIBLE);
                phoneB6.setEnabled(false);
            }
            else if(phoneNums.getString("numExtra", "").equals("3")) {
                phoneB4.setVisibility(View.VISIBLE);
                phoneB4.setEnabled(true);
                phoneB5.setVisibility(View.VISIBLE);
                phoneB5.setEnabled(true);
                phoneB6.setVisibility(View.VISIBLE);
                phoneB6.setEnabled(true);
            }
            if(phoneNums.getString("phoneN1", "").equals(""))
                phoneB1.setEnabled(false);
            if(phoneNums.getString("phoneN2", "").equals(""))
                phoneB2.setEnabled(false);
            if(phoneNums.getString("phoneN3", "").equals(""))
                phoneB3.setEnabled(false);
            if(phoneNums.getString("phoneN4", "").equals(""))
                phoneB4.setEnabled(false);
            if(phoneNums.getString("phoneN5", "").equals(""))
                phoneB5.setEnabled(false);
            if(phoneNums.getString("phoneN6", "").equals(""))
                phoneB6.setEnabled(false);
            phone1.setVisibility(View.INVISIBLE);
            phone2.setVisibility(View.INVISIBLE);
            phone3.setVisibility(View.INVISIBLE);
            phone4.setVisibility(View.INVISIBLE);
            phone4.setEnabled(false);
            phone5.setVisibility(View.INVISIBLE);
            phone5.setEnabled(false);
            phone6.setVisibility(View.INVISIBLE);
            phone6.setEnabled(false);
            addButton.setVisibility(View.INVISIBLE);
            contact4.setVisibility(View.VISIBLE);
            contact5.setVisibility(View.VISIBLE);
            contact6.setVisibility(View.VISIBLE);

            phoneT4.setEnabled(false);
            phoneT5.setEnabled(false);
            phoneT6.setEnabled(false);
            phoneT4.setVisibility(View.INVISIBLE);
            phoneT5.setVisibility(View.INVISIBLE);
            phoneT6.setVisibility(View.INVISIBLE);

        }
        else{
            phone1.setVisibility(View.VISIBLE);
            phone2.setVisibility(View.VISIBLE);
            phone3.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
            phoneB1.setVisibility(View.INVISIBLE);
            phoneB2.setVisibility(View.INVISIBLE);
            phoneB3.setVisibility(View.INVISIBLE);
            phoneB4.setVisibility(View.INVISIBLE);
            phoneB5.setVisibility(View.INVISIBLE);
            phoneB6.setVisibility(View.INVISIBLE);
            phone1.setEnabled(true);
            phone2.setEnabled(true);
            phone3.setEnabled(true);
            phoneT4.setVisibility(View.INVISIBLE);
            phoneT5.setVisibility(View.INVISIBLE);
            phoneT6.setVisibility(View.INVISIBLE);
            phoneT4.setEnabled(false);
            phoneT5.setEnabled(false);
            phoneT6.setEnabled(false);

            phone4.setVisibility(View.INVISIBLE);
            phone5.setVisibility(View.INVISIBLE);
            phone6.setVisibility(View.INVISIBLE);
            phone4.setEnabled(false);
            phone5.setEnabled(false);
            phone6.setEnabled(false);
            contact4.setVisibility(View.INVISIBLE);
            contact5.setVisibility(View.INVISIBLE);
            contact6.setVisibility(View.INVISIBLE);

            if(phoneNums.getString("numExtra", "").equals("1")) {
                phoneT4.setVisibility(View.VISIBLE);
                phoneT4.setEnabled(true);
                phone4.setVisibility(View.VISIBLE);
                phone4.setEnabled(true);
            }
            else if(phoneNums.getString("numExtra", "").equals("2")) {
                phoneT4.setVisibility(View.VISIBLE);
                phoneT4.setEnabled(true);
                phoneT5.setVisibility(View.VISIBLE);
                phoneT5.setEnabled(true);
                phone4.setVisibility(View.VISIBLE);
                phone4.setEnabled(true);
                phone5.setVisibility(View.VISIBLE);
                phone5.setEnabled(true);
            }
            else if(phoneNums.getString("numExtra", "").equals("3")) {
                phoneT4.setVisibility(View.VISIBLE);
                phoneT4.setEnabled(true);
                phoneT5.setVisibility(View.VISIBLE);
                phoneT5.setEnabled(true);
                phoneT6.setVisibility(View.VISIBLE);
                phoneT6.setEnabled(true);
                phone4.setVisibility(View.VISIBLE);
                phone4.setEnabled(true);
                phone5.setVisibility(View.VISIBLE);
                phone5.setEnabled(true);
                phone6.setVisibility(View.VISIBLE);
                phone6.setEnabled(true);
                addButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setData(SharedPreferences phoneNums){
        setNumber(1, phone1, phoneB1);
        setNumber(2, phone2, phoneB2);
        setNumber(3, phone3, phoneB3);
        setNumber(4, phone4, phoneB4);
        setNumber(5, phone5, phoneB5);
        setNumber(6, phone6, phoneB6);
        setText(4, phoneT4, contact4);
        setText(5, phoneT5, contact5);
        setText(6, phoneT6, contact6);
    }

    public void setText(int num, EditText phoneT, TextView contact){
        String name = phoneNums.getString("phoneT"+num, "");
        boolean notSame = true;
        String newName = phoneT.getText().toString();
        if(name.equals(newName) || name.equals("") || newName.equals(""))
            notSame = false;

        if(name.equals("") && newName.equals(""))
            contact.setText("Add name");
        else if(name.equals("") || notSame){
            editor.putString("phoneT" + num, newName);
            contact.setText(newName);
            editor.apply();
        }
        else{
            editor.putString("phoneT"+num, name);
            editor.apply();
            contact.setText(name);
        }
        name = phoneNums.getString("phoneT"+num, "");
        phoneT.setText(name);
        changeEdit(1);
    }

    public String fixText(String num, int phoneNum){
        String fixed;
        if(num.length()!=10 && !num.equals("911"))
        {
            if(phoneNum==1)
                phoneB1.setVisibility(View.INVISIBLE);
            else if(phoneNum==2)
                phoneB2.setVisibility(View.INVISIBLE);
            else if(phoneNum==3)
                phoneB3.setVisibility(View.INVISIBLE);
            else
                phoneB4.setVisibility(View.INVISIBLE);
            fixed = "";
        }
        else if(num.equals("911")) fixed = "911";
        else
            fixed = "("+num.substring(0,3)+")"+" "+num.substring(3,6)+"-"+num.substring(6);
        return fixed;
    }

    public void setNumber(int num, EditText phone, Button phoneB){
        String phoneNum = phoneNums.getString("phoneN"+num, "");
        String newNum = phone.getText().toString();
        boolean notSame = true;
        if(phoneNum.equals(newNum) || phoneNum.equals("") || newNum.equals(""))
            notSame = false;
        if(phoneNum.equals("") && newNum.equals("")) {
            phoneB.setText("Add Number");
        }
        else if (phoneNum.equals("")|| notSame) {
            editor.putString("phoneN" + num, newNum);
            phoneB.setText(fixText(newNum, num));
            editor.apply();
        }
        else{
            editor.putString("phoneN"+num, phoneNum);
            editor.apply();
            phoneB.setText(fixText(phoneNum,num));
        }
        phoneNum = phoneNums.getString("phoneN"+num, "");
        phone.setText(phoneNum);
        changeEdit(1);
    }

}