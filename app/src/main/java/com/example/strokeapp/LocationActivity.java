package com.example.strokeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    List<EditText> objEditTexts = new ArrayList<>();
    List<EditText> locEditTexts = new ArrayList<>();
    private int numRows;
    private Button editButton;
    private RelativeLayout relLayout;
    SharedPreferences items;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        items = getSharedPreferences("list", Context.MODE_PRIVATE);
        editor = this.items.edit();

        relLayout = findViewById(R.id.locationLayout);

        int numO = items.getInt("num o", 0);
        int numL = items.getInt("num l", 0);

        objEditTexts.add(findViewById(R.id.objET1));
        locEditTexts.add(findViewById(R.id.locET1));

        for(int i = 1; i < 3 || i < Math.max(numO, numL); i++) {
            addRow(i);
            numRows++;
        }

        for(int i = 0; i < numO; i++) {
            objEditTexts.get(i).setText(items.getString("obj"+i, ""));
        }

        for(int i = 0; i < numL; i++) {
            locEditTexts.get(i).setText(items.getString("loc"+i, ""));
        }

        editButton = (Button) findViewById(R.id.editB);
        setEdit(false);
        editButton.setOnClickListener(v -> {
            setEdit(true);
        });

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(v -> {
            int count1 = 0;
            for(int i = 0; i < objEditTexts.size(); i++) {
                String obj = getText(objEditTexts.get(i));
                if(obj.length() > 0) {
                    editor.putString("obj" + i, obj);
                    count1++;
                }
            }
            editor.putInt("num o", count1);
            int count2 = 0;
            for(int i = 0; i < locEditTexts.size(); i++) {
                String loc = getText(locEditTexts.get(i));
                if(loc.length() > 0) {
                    editor.putString("loc" + i, loc);
                    count2++;
                }
            }
            editor.putInt("num l", count2);
            editor.apply();
            setEdit(false);
        });

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            addRow(numRows);
            numRows++;
        });

        ImageButton homeBtn = findViewById(R.id.homeButtonL);
        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void addRow(int i) {
        EditText obj = createEditText(true, i);
        EditText loc = createEditText(false, i);
        objEditTexts.add(obj);
        locEditTexts.add(loc);
        relLayout.addView(obj);
        relLayout.addView(loc);
    }

    private EditText createEditText(boolean obj, int index) {
        EditText et = new EditText(this);
        et.setBackgroundColor(getResources().getColor(R.color.lightPink));
        int p = toPixel(8, getApplicationContext());
        et.setPadding(p, p, p, p);
        et.setTextSize(20);
        et.setTextColor(getResources().getColor(R.color.black));
        et.setTypeface(ResourcesCompat.getFont(this, R.font.sarabun));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int m = toPixel(10, getApplicationContext());
        params.setMargins(0, m, 0, 0);
        EditText prev = objEditTexts.get(index-1);
        if(index != 1) prev.setId(index);
        params.addRule(RelativeLayout.BELOW, prev.getId());
        if(obj) {
            params.addRule(RelativeLayout.ALIGN_START, R.id.objTV);
            params.addRule(RelativeLayout.ALIGN_END, R.id.objTV);
        }
        else {
            params.addRule(RelativeLayout.ALIGN_END, R.id.locTV);
            params.addRule(RelativeLayout.ALIGN_START, R.id.locTV);
            et.setId(index+1);
            TextView line = findViewById(R.id.line2);
            RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(
                    toPixel(7, getApplicationContext()),
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            lineParams.addRule(RelativeLayout.BELOW, R.id.line1);
            lineParams.addRule(RelativeLayout.END_OF, R.id.objET1);
            lineParams.addRule(RelativeLayout.ALIGN_BOTTOM, et.getId());
            line.setLayoutParams(lineParams);
        }
        et.setLayoutParams(params);
        return et;
    }


    private void setEdit(boolean edit) {
        LinearLayout btnLayout = findViewById(R.id.locEditLayout);
        if(edit) {
            for(int i = 0; i < objEditTexts.size(); i++) {
                objEditTexts.get(i).setEnabled(true);
                locEditTexts.get(i).setEnabled(true);
                btnLayout.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
            }
        }
        else {
            for(int i = 0; i < objEditTexts.size(); i++) {
                objEditTexts.get(i).setEnabled(false);
                locEditTexts.get(i).setEnabled(false);
                btnLayout.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getText (EditText editText) {
        return editText.getText().toString().trim();
    }

    private static int toPixel(float dp, Context context){
        float px = dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }
}