package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CardGameActivity extends AppCompatActivity {

    private ImageButton[] buttons;
    private ImageButton homeButton, calendarButton, rehabButton, profileButton;
    private boolean [] flipped;
    private int count;
    private ArrayList<Integer> nums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_game);
        /*buttons = new ImageButton [12];
        flipped = new boolean[12];


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
        //reset();

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
/*
        for(int i=0;i<12;i++) {
            int a = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nums.get(a) != 0 && count<2) {
                        if(a<2)
                            buttons[a].setImageResource(R.drawable.card0);
                        else if(a<4)
                            buttons[a].setImageResource(R.drawable.card1);
                        else if(a<6)
                            buttons[a].setImageResource(R.drawable.card2);
                        else if(a<8)
                            buttons[a].setImageResource(R.drawable.card3);
                        else if(a<10)
                            buttons[a].setImageResource(R.drawable.card4);
                        else
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
                                }, 1000);
                            }
                            count = 0;
                            if (checkIfComplete()) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        reset();
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            });
        }*/
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
    }
}