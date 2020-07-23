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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button registerbutton;
    private EditText Inputname,InputPassword,InputPhoneNo;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerbutton = (Button) findViewById(R.id.register_button);
        Inputname = (EditText) findViewById(R.id.register_name);
        InputPassword = (EditText) findViewById(R.id.register_password);
        InputPhoneNo = (EditText) findViewById(R.id.register_phone);
        loadingBar = new ProgressDialog(this);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();

            }
        });




    }

    private void createAccount() {
        String name = Inputname.getText().toString();
        String phone = InputPhoneNo.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            Inputname.setError("Cannot Be Empty");
        }
        if(TextUtils.isEmpty(password)){
            InputPassword.setError("Cannot Be Empty");
        }
        if(TextUtils.isEmpty(phone)){
            InputPhoneNo.setError("Cannot Be Empty");
        }
        else {
            loadingBar.setTitle("Create Acccount");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatephonenumber(name,phone,password);
        }

    }

    private void validatephonenumber(final String name, final String phone, final String password) {

        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){

                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);

                    rootref.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Congrats.ACcc createde", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: ", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "This"+phone+ " Exists already", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try agani new number", Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}