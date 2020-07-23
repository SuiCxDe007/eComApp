package com.suicxde.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import com.suicxde.ecommerceapp.Model.Users;
import com.suicxde.ecommerceapp.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingbar;
    private TextView AdminLink,NotAdminLink;

    private String parentDbDame = "Users";
    private CheckBox chkBoxRememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_button);
        InputNumber = (EditText) findViewById(R.id.login_phone);
        InputPassword = (EditText) findViewById(R.id.login_password);
        loadingbar = new ProgressDialog(this);
        AdminLink = (TextView) findViewById(R.id.admin_panel);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel);

        chkBoxRememberme = (CheckBox) findViewById(R.id.remember_checkbox);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbDame = "Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbDame = "Users";


            }
        });

    }

    private void loginUser() {


        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            InputNumber.setError("Cannot Be Empty");
        }
        else  if(TextUtils.isEmpty(password)){
            InputPassword.setError("Cannot Be Empty");
        }
        else {
            loadingbar.setTitle("login account Acccount");
            loadingbar.setMessage("Please wait");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if(chkBoxRememberme.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);


        }


        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbDame).child(phone).exists()){

                    Users userdata = dataSnapshot.child(parentDbDame).child(phone).getValue(Users.class);

                    if (userdata.getPhone().equals(phone)){

                        if (userdata.getPassword().equals(password))
                        {
                            if(parentDbDame.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "welcome bro admin", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if(parentDbDame.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in succ", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }

                        }
                        else {
                            loadingbar.dismiss();
                            Toast.makeText(LoginActivity.this, "Passwrod wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else{

                    loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this, "Accoiunt Does not exist", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(LoginActivity.this, "Create new one", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
