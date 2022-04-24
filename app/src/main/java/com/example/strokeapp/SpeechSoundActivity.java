package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class SpeechSoundActivity extends AppCompatActivity {

    private Button speakButton, nextButton, settingsButton, backButton;
    private ImageButton homeButton, calendarButton, rehabButton, profileButton;
    private TextToSpeech t1;
    private TextView title;
    private ArrayList<String> sounds;
    private String [] rSound, sSound, thSound,shSound, chSound, lSound;
    private CheckBox rCheck, sCheck, thCheck, shCheck, chCheck;
    private int num;
    private boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_sound);
        visible = false;


        sounds = new ArrayList<>();
        rSound = new String []{"run", "carrot", "rice", "rat", "bear", "four", "bird", "earring", "rake", "red", "arm", "iron", "rabbit", "lizard", "doctor", "rich", "party", "ladder"};
        sSound = new String []{"sit", "soup", "face", "bus", "fossil", "save", "city", "beside", "nice", "dress", "soft", "son", "eraser", "lettuce"};
        thSound = new String []{"think", "that", "the", "feather", "breathe", "scathe", "bathe", "mother", "either", "though", "thee", "themselves", "soothing"};
        chSound = new String []{"chat", "chair", "chase", "check", "beach", "catcher", "couch", "pitch", "inches", "reach", "kitchen", "chin", "watch", "picture"};
        shSound = new String []{"sharp", "shirt", "dishes", "brush", "addition", "bush", "short", "show", "flush", "washing", "rush", "tissue", "bush", "shake"};
        num = 0;

        title = (TextView) findViewById(R.id.soundTitle);
        speakButton = (Button) findViewById(R.id.speakB);
        nextButton = (Button) findViewById(R.id.nextB);
        settingsButton = (Button) findViewById(R.id.settingsB);
        rCheck = (CheckBox) findViewById(R.id.checkBoxR);
        sCheck = (CheckBox) findViewById(R.id.checkBoxS);
        thCheck = (CheckBox) findViewById(R.id.checkBoxTh);
        chCheck = (CheckBox) findViewById(R.id.checkBoxCh);
        shCheck = (CheckBox) findViewById(R.id.checkBoxSh);

        rCheck.setChecked(true);
        sCheck.setChecked(true);
        thCheck.setChecked(true);
        shCheck.setChecked(true);
        chCheck.setChecked(true);
        createCollections();
        Collections.shuffle(sounds);
        settingsVisibility();

        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(SpeechSoundActivity.this, MainActivity.class);
            startActivity(intent);
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(SpeechSoundActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(SpeechSoundActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(SpeechSoundActivity.this, CalendarActivity.class);
            startActivity(intent);
        });


        t1=new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS) {
                t1.setLanguage(Locale.US);
            }
        });

        speakButton.setOnClickListener(v -> {
            String toSpeak = title.getText().toString();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });

        nextButton.setOnClickListener(v -> {
            if(num<sounds.size()) {
                title.setText(sounds.get(num));
                num++;
            }
            else{
                Collections.shuffle(sounds);
                title.setText(sounds.get(0));
                num=1;

            }
        });

        settingsButton.setOnClickListener(v -> {
            visible = !visible;
            settingsVisibility();
        });

        backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(SpeechSoundActivity.this, CognitiveActivity.class);
            startActivity(intent);
        });

        rCheck.setOnClickListener(v -> createCollections());
        sCheck.setOnClickListener(v -> createCollections());
        thCheck.setOnClickListener(v -> createCollections());
        chCheck.setOnClickListener(v -> createCollections());
        shCheck.setOnClickListener(v -> createCollections());

    }

    public void createCollections()
    {
        sounds.clear();
        if(rCheck.isChecked()){
            for(int i=0;i<rSound.length;i++){
                sounds.add(rSound[i]);
            }
        }
        if(sCheck.isChecked()){
            for(int i=0;i<sSound.length;i++){
                sounds.add(sSound[i]);
            }
        }
        if(thCheck.isChecked()){
            for(int i=0;i<thSound.length;i++){
                sounds.add(thSound[i]);
            }
        }
        if(shCheck.isChecked()){
            for(int i=0;i<shSound.length;i++){
                sounds.add(shSound[i]);
            }
        }
        if(chCheck.isChecked()){
            for(int i=0;i<chSound.length;i++){
                sounds.add(chSound[i]);
            }
        }
    }

    public void settingsVisibility(){
        if(visible){
            rCheck.setVisibility(View.VISIBLE);
            sCheck.setVisibility(View.VISIBLE);
            thCheck.setVisibility(View.VISIBLE);
            shCheck.setVisibility(View.VISIBLE);
            chCheck.setVisibility(View.VISIBLE);
        }
        else{
            rCheck.setVisibility(View.INVISIBLE);
            sCheck.setVisibility(View.INVISIBLE);
            thCheck.setVisibility(View.INVISIBLE);
            shCheck.setVisibility(View.INVISIBLE);
            chCheck.setVisibility(View.INVISIBLE);
        }
    }

   /* public void addWords(String file){
        Scanner input = null;
        input = new Scanner(file);
        String temp="";
        while(input.hasNext()){
            temp += input.next()+" ";
            homeButton.setText(temp);
            sounds.add(temp);
        }

    }*/

    /*public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }*/
}