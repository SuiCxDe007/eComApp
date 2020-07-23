package com.suicxde.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suicxde.ecommerceapp.Model.Users;
import com.suicxde.ecommerceapp.Prevalent.Prevalent;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private Button signup,login;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = (Button) findViewById(R.id.register_button);
        login = (Button) findViewById(R.id.login_button);
        loadingbar = new ProgressDialog(this);

        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        String phonekey = Paper.book().read(Prevalent.UserPhoneKey);
        String passkey = Paper.book().read(Prevalent.UserPasswordKey);

        if(phonekey != "" && passkey !="" ){

            if(!TextUtils.isEmpty(phonekey) && !TextUtils.isEmpty(passkey)){

                AllowAccess(phonekey,passkey);
                loadingbar.setTitle("a;ready logged om Acccount");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }

    }

    private void AllowAccess(final String phonekey, final String passkey) {

        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phonekey).exists()){

                    Users userdata = dataSnapshot.child("Users").child(phonekey).getValue(Users.class);
                    if (userdata.getPhone().equals(phonekey)){
                        if (userdata.getPassword().equals(passkey)){
                            Toast.makeText(MainActivity.this, "lyou are alreaddy loged", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            loadingbar.dismiss();
                            Toast.makeText(MainActivity.this, "Passwrod wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Accoiunt Does not exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(MainActivity.this, "Create new one", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
