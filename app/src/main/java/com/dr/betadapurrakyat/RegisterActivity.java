package com.dr.betadapurrakyat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dr.betadapurrakyat.Model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ASUS on 2/14/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText rFullname, rUsername, rPassword, rCpassword, rPhone, rAlamat, rKodepos, rNoID;
    private String Fullname, Username, Password, srCpassword, srPhone, Alamat, Kodepos, NoID;
    private Button button_register;

    private String sPhoneKey;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //init Register
        rFullname = findViewById(R.id.fullName_textdetails);
        rPhone = findViewById(R.id.phone_textdetails);
        rPassword = findViewById(R.id.password_textdetails);
        rCpassword = findViewById(R.id.cPassword_textdetails);
        rUsername = findViewById(R.id.userName_text);
        rNoID = findViewById(R.id.noID_text);
        rAlamat = findViewById(R.id.alamat_textdetails);
        rKodepos = findViewById(R.id.kodepos_textdetails);
        button_register = findViewById(R.id.button_register);
        registerDataUser();

    }

    private void registerDataUser() {
        setEditText();
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignNewUser();

            }
        });

    }

    private void setEditText() {
        sPhoneKey = user.getPhoneNumber();
        rPhone.setText(sPhoneKey);
        rPhone.setEnabled(false);

        myRef.child("User").child(sPhoneKey).child("fullname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fullname = dataSnapshot.getValue(String.class);
                rFullname.setText(fullname);
                rFullname.setEnabled(false);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void assignNewUser() {

        Fullname = rFullname.getText().toString();
        srPhone = rPhone.getText().toString();
        Password = rPassword.getText().toString();
        srCpassword = rCpassword.getText().toString();
        if (TextUtils.isEmpty(srCpassword) && !srCpassword.equals(Password)) {
            rCpassword.setError("Password anda tidak sesuai");
            return;
        }
        Username = rUsername.getText().toString();
        if (TextUtils.isEmpty(Username) || Username.length() < 3) {
            rUsername.setError("Masukan Nama User Yang Anda Inginkan");
            return;
        }
        NoID = rNoID.getText().toString();
        if (TextUtils.isEmpty(NoID) || NoID.length() < 10) {
            rUsername.setError("Masukan No Identitas Anda (KTP)");
            return;
        }
        Alamat = rAlamat.getText().toString();
        Kodepos = rKodepos.getText().toString();

        UserData newUser = new UserData(Fullname, Username, Password,NoID, Alamat, Kodepos);
        myRef.child("User").child(sPhoneKey).setValue(newUser);
        Toast.makeText(RegisterActivity.this,
                " Registrasi Berhasil", Toast.LENGTH_SHORT).show();
        Intent homeActivity = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }


}
