package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;



    private FirebaseAuth.AuthStateListener mAutL = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        }


    };
    private static final String TAG = "LogIn";

    private EditText email;
    private EditText pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        email = (EditText) findViewById(R.id.EtEmail);
        pass = (EditText) findViewById(R.id.EtPass);
        login = (Button) findViewById(R.id.LogIn);

        // Forgot Password
        TextView textViewF = (TextView) findViewById(R.id.ForgotPasswrod);



        textViewF.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentF = new Intent(LogIn.this, ForgotPassword.class);
                startActivity(intentF);
                Toast.makeText(LogIn.this,"Forgot Password", Toast.LENGTH_LONG).show();
            }

        });


        mAuth = FirebaseAuth.getInstance();
        mAutL = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }


            //Create New Account

        };
        // create Account Intent
        TextView signup = (TextView) findViewById(R.id.CreateAccount);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentS = new Intent(LogIn.this, SignUp.class);
                startActivity(intentS);
            }
        });
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("message");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAutL = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "User Signed IN");
                } else {
                    //user is signed out
                    Log.d(TAG, "User Signed out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAutL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAutL != null) {
            mAuth.removeAuthStateListener(mAutL);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailt = email.getText().toString();
                String passt = pass.getText().toString();

                if (!emailt.equals("") && !pass.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailt, passt)
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LogIn.this, "Wrong Email or Password", Toast.LENGTH_LONG).show();
                                        //ref.setValue("NOOOOOOO");
                                    } else {
                                        // Toast.makeText(LogIn.this, "Welcome", Toast.LENGTH_LONG).show();
                                        //ref.setValue("Hello");
                                        checkIfEmailVerified();
                                    }
                                }
                            });

                }
            }
        });


    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            finish();
            Toast.makeText(LogIn.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this,SignIn.class);

                    startActivity(intent);

        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(LogIn.this, "Verify your Email First", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }



    }

}



    //Forgot Password



      /*  // Log In Button
        Button buttonL = (Button) findViewById(R.id.LogIn);

        buttonL.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentL = new Intent(LogIn.this, SignIn.class);
                startActivity(intentL);
            }

        });



    }
}
*/