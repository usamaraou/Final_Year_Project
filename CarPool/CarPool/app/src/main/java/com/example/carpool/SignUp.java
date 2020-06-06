package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText emailId, passwd,name,id,number;
    Button btnSignUp;

    FirebaseAuth firebaseAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        //Fire Base COde

        firebaseAuth =  FirebaseAuth.getInstance();
        emailId = (EditText) findViewById(R.id.EtEmailS);
        passwd = (EditText) findViewById(R.id.EtPassS);
        name = (EditText) findViewById(R.id.editText3);
        id = (EditText) findViewById(R.id.editText5);
        number = (EditText) findViewById(R.id.editText7);
        btnSignUp = findViewById(R.id.Submit);
        final CreateAccount account = new CreateAccount();
        ref= FirebaseDatabase.getInstance().getReference().child("User");



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String names = name.getText().toString();
                //int  ids = Integer.parseInt(id.getText().toString().trim());
                //long numbers = Long.parseLong(number.getText().toString().trim());
                String emailID = emailId.getText().toString().trim();
                String paswd = passwd.getText().toString();
                String emailPattern = "[0-9]+@[s]+[t]+[u]+[d]+[e]+[n]+[t][s]+\\.+[a]+[u]+\\.+[e]+[d]+[u]+\\.+[p]+[k]+";
                account.setName(name.getText().toString().trim());
                account.setId(id.getText().toString().trim());
                account.setNumber(number.getText().toString().trim());
                account.setEmail(emailID);
              //  ref.push().setValue(account);
             //   Toast.makeText(SignUp.this,"SignUP Succesfull",Toast.LENGTH_LONG).show();
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(SignUp.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty()) && emailID.matches(emailPattern)) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(SignUp.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                            if (!task.isSuccessful()) {

                                Toast.makeText(SignUp.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }
                            else {
                                sendVerificationEmail();

                                startActivity(new Intent(SignUp.this, LogIn.class));
                                Toast.makeText(SignUp.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                // ref.   .setValue(account);
                                ref.child(user.getUid()).setValue(account);

                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Error: Enter University Email ", Toast.LENGTH_SHORT).show();
                }
            }
        });

      // Read Agreement Button
        TextView textView = (TextView) findViewById(R.id.ReadAgreement);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,ReadAgreement.class);
                startActivity(intent);
                Toast.makeText(SignUp.this,"Read Agreement", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendVerificationEmail() {
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // email sent


                                // after email is sent just logout the user and finish this activity
                                FirebaseAuth.getInstance().signOut();
                            //    startActivity(new Intent(SignUp.this, SignIn.class));
                                finish();
                            }
                            else
                            {
                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                //restart this activity
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());

                            }
                        }
                    });
        }

    }
}
