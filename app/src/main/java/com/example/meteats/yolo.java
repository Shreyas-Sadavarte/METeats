package com.example.meteats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meteats.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.meteats.ui.data.model.LoggedInUser;

public class yolo extends AppCompatActivity {
    EditText emailId,password;
    Button signIn, signUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yolo);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId = (EditText)findViewById(R.id.Usernametxt);
        String email=emailId.toString();
        password =(EditText)findViewById(R.id.passwordtxt);
        signUp =(Button)findViewById(R.id.signUpbtn);
        signIn= (Button)findViewById(R.id.signInbtn);


        mAuthstateListener =new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
           FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
           if(mFirebaseUser !=null){
               Toast.makeText(yolo.this," Youu have Logged IN",Toast.LENGTH_SHORT).show();
               //startActivity(new Intent(yolo.this, FoodActivity.class));
               Intent homeIntent =new Intent(yolo.this,Home.class);
               startActivity(homeIntent);
           }
           else{
               Toast.makeText(yolo.this,"Please Log IN",Toast.LENGTH_SHORT).show();
           }
                    }
        };

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email =emailId.getText().toString();
                String pwrd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("EmailId Not Entered.");
                    emailId.requestFocus();
                }
                else if(pwrd.isEmpty()){
                    password.setError("Password Not Entered");
                    emailId.requestFocus();
                }
                else if(email.isEmpty() && pwrd.isEmpty()){
                    Toast.makeText(yolo.this,"Fields Empty ",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwrd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwrd).addOnCompleteListener(yolo.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(yolo.this,"Login Error...Try Again Later",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //startActivity(new Intent(yolo.this,FoodActivity.class));
                                Intent homeIntent =new Intent(yolo.this,Home.class);
                                Common.currentuser = email;
                                startActivity(homeIntent);
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(yolo.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
            });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yolo.this, NewUserActivity.class));
            }
        });

    }

  //@Override
    protected void OnStart(){
       super.onStart();
       mFirebaseAuth.addAuthStateListener(mAuthstateListener);
    }




}



