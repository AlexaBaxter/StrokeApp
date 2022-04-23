package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout btnLayout;
    private Button editButton;
    private EditText editName, editDrName, editEmail, editDrType;
    private String myName;
    private TextView nameText;
    private AlertDialog dialog;
    private List<Doctor> doctors;
    private SharedPreferences.Editor editor;
    private String emailSubject, emailText;
    private ProgressManager progress;
    private boolean sent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeButtons();

        SharedPreferences data = this.getSharedPreferences("ProfileData", 0);
        editor = data.edit();
        doctors = new ArrayList<>();
        int number = data.getInt("number", 0);
        for(int i = 0; i < number; i++) {
            Doctor d = new Doctor(data.getString("drName" + i, ""),
                    data.getString("email" + i, ""),
                    data.getString("type" + i, ""));
            doctors.add(d);
        }
        showDoctors();

        editButton = findViewById(R.id.editNameBtn);
        btnLayout = findViewById(R.id.profileBtnLayout);
        editName = findViewById(R.id.editNameProfile);
        editButton.setOnClickListener(v -> {
            btnLayout.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            editName.setVisibility(View.VISIBLE);
            nameText.setText("Name: ");
            editName.setText(myName);
        });

        Button saveBtn = findViewById(R.id.saveProfileButton);
        nameText = findViewById(R.id.profileNameText);
        myName = data.getString("my name", "");
        nameText.setText("Name: " + myName);

        saveBtn.setOnClickListener(v -> {
            btnLayout.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            editName.setVisibility(View.GONE);
            myName = getText(editName);
            editor.putString("my name", myName);
            editor.commit();
            nameText.setText("Name: " + myName);
        });

        Button cancelBtn = findViewById(R.id.cancelProfileButton);
        cancelBtn.setOnClickListener(v -> {
            btnLayout.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            editName.setVisibility(View.GONE);
            nameText.setText("Name: " + myName);
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String n = getText(editName);
                saveBtn.setEnabled(n.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button addDrBtn = findViewById(R.id.addDoctorBtn);
        addDrBtn.setOnClickListener(v -> {
            createAddDoctorDialog();
        });

        progress = ProgressManager.getInstance();
        progress.initialize(getApplicationContext());
        ImageView[] stars = new ImageView[]{findViewById(R.id.w_star1), findViewById(R.id.w_star2), findViewById(R.id.w_star3),
                findViewById(R.id.w_star4), findViewById(R.id.w_star5), findViewById(R.id.w_star6),
                findViewById(R.id.a_star1), findViewById(R.id.a_star2), findViewById(R.id.a_star3),
                findViewById(R.id.a_star4), findViewById(R.id.a_star5), findViewById(R.id.a_star6)};

        for(ImageView star : stars) star.setImageResource(R.drawable.emptystar);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                if(progress.starIsFilled(i, j)) stars[j+3*i].setImageResource(R.drawable.filledstar);
            }
        }

        Button emailBtn = findViewById(R.id.emailBtn);
        emailBtn.setOnClickListener(v -> {
            createEmailDialog();
        });
    }

    public void initializeButtons()
    {
        ImageButton homeButton = findViewById(R.id.HomeButtonP);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton rehabButton = findViewById(R.id.RehabButtonP);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.ProfileButtonP);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        ImageButton calendarButton = findViewById(R.id.CalendarButtonP);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    private void showDoctors() {
        LinearLayout doctorsLayout = findViewById(R.id.doctorsLayout);
        doctorsLayout.removeAllViews();

        if(doctors.size() == 0) {
            Button btn = createDoctorText("None");
            btn.setEnabled(false);
            doctorsLayout.addView(btn);
        }

        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            String text = "Dr. " + d.getName();
            if(!d.getType().equals("")) text += " (" + d.getType() + ")";
            Button btn = createDoctorText(text);
            doctorsLayout.addView(btn);
            btn.setOnClickListener(v -> {
                createDoctorDialog(d);
            });
        }
    }

    private Button createDoctorText(String text) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setBackgroundColor(getResources().getColor(R.color.profile_bg));
        btn.setPadding(30, 0, 30, 0);
        btn.setTextSize(20);
        btn.setTextColor(getResources().getColor(R.color.black));
        btn.setGravity(Gravity.CENTER_VERTICAL);
        btn.setAllCaps(false);
        btn.setTypeface(ResourcesCompat.getFont(this, R.font.sarabun));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        btn.setLayoutParams(params);
        return btn;
    }

    private void createAddDoctorDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.add_doctor_popup, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        editDrName = popUpView.findViewById(R.id.editDocName);
        editEmail = popUpView.findViewById(R.id.editEmail);
        editDrType = popUpView.findViewById(R.id.editType);

        Button addBtn = popUpView.findViewById(R.id.addDrBtn);
        addBtn.setOnClickListener(v -> {
            dialog.dismiss();
            Doctor newDoctor = new Doctor(getText(editDrName), getText(editEmail), getText(editDrType));
            doctors.add(newDoctor);
            editor.putString("drName" + (doctors.size()-1), newDoctor.getName());
            editor.putString("email" + (doctors.size()-1), newDoctor.getEmail());
            editor.putString("type" + (doctors.size()-1), newDoctor.getType());
            editor.putInt("number", doctors.size());
            editor.commit();
            showDoctors();
        });

        Button cancelBtn = popUpView.findViewById(R.id.addDrCancelBtn);
        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        editDrName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(getText(editDrName).length() != 0 && getText(editEmail).length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(getText(editEmail).length() != 0 && getText(editDrName).length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private String getText (EditText editText) {
        return editText.getText().toString().trim();
    }

    private void createEmailDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.email_popup, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        List<CheckBox> checkboxes = new ArrayList<>();
        LinearLayout doctorsList = popUpView.findViewById(R.id.doctorsList);
        doctorsList.removeAllViews();
        for(Doctor d : doctors) {
            CheckBox cb = new CheckBox(this);
            cb.setText(d.getName());
            cb.setBackgroundColor(getResources().getColor(R.color.profile_bg));
            cb.setTextSize(20);
            cb.setTextColor(getResources().getColor(R.color.black));
            cb.setTypeface(ResourcesCompat.getFont(this, R.font.sarabun));
            checkboxes.add(cb);
            doctorsList.addView(cb);
        }

        List<Doctor> sendTo = new ArrayList<>();
        for(int i = 0; i < checkboxes.size(); i++) {
            CheckBox cb = checkboxes.get(i);
            int x = i;
            cb.setOnClickListener(v -> {
                if(cb.isChecked()) sendTo.add(doctors.get(x));
                else sendTo.remove(doctors.get(x));
            });
        }

        emailText = "Your patient, " + myName + ", has sent you a progress report." +
                "\n\nPHYSICAL REHAB" + "\nCompleted Walking Exercises: ";
        String [] walkingExercises = new String [] {"Knee Extensions", "Seated Marching", "Ankle Stretch",
                "Forward Walking", "Forward Step-Overs", "Sideways Step-Overs"};
        String walking = makeString(0, walkingExercises);
        walking += makeString(1, walkingExercises);
        if(walking.contains(","))
            emailText += walking.substring(0, walking.lastIndexOf(","));
        else emailText += "None";
        emailText += "\nCompleted Arm Exercises: ";
        String [] armExercises = new String [] {"Tabletop Circles", "Unweighted Bicep Curls", "Open Arm",
                "Weight Lean", "Sideways Push", "Forward Push"};
        String arm = makeString(2, armExercises);
        arm += makeString(3, armExercises);
        if(arm.length() > 0)
            emailText += arm.substring(0, arm.lastIndexOf(","));
        else emailText += "None";
        emailText += "\n\nProvided by HomeHeal\n";

        EditText editMessage = popUpView.findViewById(R.id.emailMessage);
        editMessage.setText(emailText);
        EditText editSubject = popUpView.findViewById(R.id.subjectText);
        emailSubject = "HomeHeal: Progress Report for " + myName;
        editSubject.setText(emailSubject);

        Button sendBtn = popUpView.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(v -> {
            emailText = getText(editMessage);
            emailSubject = getText(editSubject);
            for(Doctor d : sendTo) {
                new Thread(() -> {
                    try {
                        MailSender sender = new MailSender("app.homeheal@gmail.com",
                                "squishy_tree123");
                        sender.sendMail(emailSubject, "Dear Dr. " + d.getName() + ",\n" + emailText,
                                "app.homeheal@gmail.com", d.getEmail());
                        sent = true;
                    }
                    catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }).start();
            }
            if(sent) Toast.makeText(getApplicationContext(), "Email sent!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private String makeString(int index, String [] exercises) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            int j = i;
            if(index % 2 != 0) j += 3;
            String s = exercises[j] + ", ";
            if(progress.starIsFilled(index, i))
                str.append(s);
        }
        return str.toString();
    }

    private void createDoctorDialog(Doctor d) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.doctor_popup, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        TextView confirmDelete = popUpView.findViewById(R.id.confirmDelete);
        LinearLayout btnLayout = popUpView.findViewById(R.id.delBtnLayout);

        Button deleteBtn = popUpView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(v -> {
            confirmDelete.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.GONE);
        });

        Button noBtn = popUpView.findViewById(R.id.noDelBtn);
        noBtn.setOnClickListener(v -> {
            confirmDelete.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
        });

        Button yesBtn = popUpView.findViewById(R.id.yesDelBtn);
        yesBtn.setOnClickListener(v -> {
            doctors.remove(d);
            for(int i = 0; i < doctors.size(); i++) {
                editor.putString("drName" + i, doctors.get(i).getName());
                editor.putString("email" + i, doctors.get(i).getEmail());
                editor.putString("type" + i, doctors.get(i).getType());
                editor.putInt("number", doctors.size());
                editor.commit();
            }
            showDoctors();
            dialog.dismiss();
        });

        String text = "Name: " + d.getName() + "\n" + "Email: " + d.getEmail();
        if(!d.getType().equals("")) text += "\n" + d.getType();
        TextView textView = popUpView.findViewById(R.id.drDetailsText);
        textView.setText(text);
    }
}