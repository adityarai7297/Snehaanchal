package org.snehaanchal.snehaanchal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    DatabaseReference database;
    DatabaseReference patient;
    DatabaseReference personal_details;
    String patient_id;
    TextView aadhar, address, age, caste, dob, dept, gender, name, phone, religion, id, regdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle extras = getIntent().getExtras();
        patient_id = extras.getString("ID");

        name = (TextView) findViewById(R.id.name);
        aadhar = (TextView) findViewById(R.id.aadhar);
        address = (TextView) findViewById(R.id.address);
        age = (TextView) findViewById(R.id.age);
        caste = (TextView) findViewById(R.id.caste);
        dob = (TextView) findViewById(R.id.dob);
        gender = (TextView) findViewById(R.id.gender);
        religion = (TextView) findViewById(R.id.religion);
        phone = (TextView) findViewById(R.id.phone);
        dept = (TextView) findViewById(R.id.department);
        id = (TextView) findViewById(R.id.patient_id);
        regdate = (TextView) findViewById(R.id.date_of_reg);
        id.setText(patient_id);


        database = FirebaseDatabase.getInstance().getReference();
        patient = database.child("Patients").child(patient_id);
        personal_details = patient.child("Personal Details");
        personal_details.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("Name").getValue().toString());
                aadhar.setText(dataSnapshot.child("Aadhar").getValue().toString());
                address.setText(dataSnapshot.child("Address").getValue().toString());
                age.setText(dataSnapshot.child("Age").getValue().toString());
                caste.setText(dataSnapshot.child("Caste").getValue().toString());
                dob.setText(dataSnapshot.child("Date Of Birth").getValue().toString());
                if (dataSnapshot.child("Department").getValue().toString().equals("HC"))
                    dept.setText("Home Care");
                else if (dataSnapshot.child("Department").getValue().toString().equals("IPD"))
                    dept.setText("In-Patient");
                else if (dataSnapshot.child("Department").getValue().toString().equals("OPD"))
                    dept.setText("Out-Patient");
                gender.setText(dataSnapshot.child("Gender").getValue().toString());
                phone.setText(dataSnapshot.child("Phone").getValue().toString());
                religion.setText(dataSnapshot.child("Religion").getValue().toString());
                regdate.setText(dataSnapshot.child("Date Of Registration").getValue().toString());
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
