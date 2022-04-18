package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CardGameActivity extends AppCompatActivity {

    private ImageButton[] buttons;
    private ImageButton homeButton, calendarButton, rehabButton, profileButton, infoButton;
    private boolean [] flipped;
    private boolean info;
    private int count;
    private TextView howTo;
    private ArrayList<Integer> nums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_game);
        buttons = new ImageButton [12];
        flipped = new boolean[12];
        info = false;

        buttons[0] = (ImageButton) findViewById(R.id.b1);
        buttons[1] = (ImageButton) findViewById(R.id.b2);
        buttons[2] = (ImageButton) findViewById(R.id.b3);
        buttons[3] = (ImageButton) findViewById(R.id.b4);
        buttons[4] = (ImageButton) findViewById(R.id.b5);
        buttons[5] = (ImageButton) findViewById(R.id.b6);
        buttons[6] = (ImageButton) findViewById(R.id.b7);
        buttons[7] = (ImageButton) findViewById(R.id.b8);
        buttons[8] = (ImageButton) findViewById(R.id.b9);
        buttons[9] = (ImageButton) findViewById(R.id.b10);
        buttons[10] = (ImageButton) findViewById(R.id.b11);
        buttons[11] = (ImageButton) findViewById(R.id.b12);
        howTo = (TextView) findViewById(R.id.howTo);
        reset();

        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardGameActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardGameActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardGameActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        infoButton = (ImageButton) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               info=!info;
               for(int i=0;i<12;i++) {
                   if(info)
                       buttons[i].setEnabled(false);
                   else
                      buttons[i].setEnabled(true);
               }
               if(info)
                   howTo.setVisibility(View.VISIBLE);
               else
                   howTo.setVisibility(View.INVISIBLE);
            }
        });

        for(int i=0;i<12;i++) {
            int a = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nums.get(a) != 0 && count<2) {
                        if(nums.get(a)==1)
                            buttons[a].setImageResource(R.drawable.card0);
                        else if(nums.get(a)==2)
                            buttons[a].setImageResource(R.drawable.card1);
                        else if(nums.get(a)==3)
                            buttons[a].setImageResource(R.drawable.card2);
                        else if(nums.get(a)==4)
                            buttons[a].setImageResource(R.drawable.card3);
                        else if(nums.get(a)==5)
                            buttons[a].setImageResource(R.drawable.card4);
                        else if(nums.get(a)==6)
                            buttons[a].setImageResource(R.drawable.card5);
                        flipped[a] = true;
                        if (count == 0) {
                            count++;
                        } else {
                            count++;
                            if (checkIfCorrect()) {
                                keepFlip();
                                unflip();
                            } else {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        unflip();
                                    }
                                }, 500);
                            }
                            count = 0;
                            if (checkIfComplete()) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {

                                        Intent intent = new Intent(CardGameActivity.this, WinCardActivity.class);
                                        startActivity(intent);
                                       // reset();
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            });
        }
    }

    public boolean checkIfCorrect(){
        int temp = -1;
        for(int i=0;i<nums.size();i++)
        {
            if(flipped[i]) {
                if (nums.get(i)==temp)
                    return true;
                temp = nums.get(i);
            }
        }
        return false;
    }

    public void unflip(){
        for(int i=0;i<nums.size();i++)
        {
            if(flipped[i] && nums.get(i)!=0) {
                buttons[i].setImageResource(R.drawable.cardback);
            }
            flipped[i]=false;
        }
    }

    public void keepFlip() {
        for(int i=0;i<nums.size();i++) {
            if (flipped[i]) {
                nums.set(i,0);
                flipped[i]=false;
            }
        }
    }

    public boolean checkIfComplete(){
        for(int i=0;i<nums.size();i++){
            if(nums.get(i)!=0)
                return false;
        }
        return true;
    }

    public void reset()
    {
        count = 0;
        nums = new ArrayList<Integer> (Arrays.asList(1,1,2,2,3,3,4,4,5,5,6,6));
        Collections.shuffle(nums);
        for(int i=0;i<nums.size();i++) {
            buttons[i].setImageResource(R.drawable.cardback);
        }
        howTo.setVisibility(View.INVISIBLE);
        info = false;
    }
}