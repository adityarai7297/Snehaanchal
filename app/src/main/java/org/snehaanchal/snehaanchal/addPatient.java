package org.snehaanchal.snehaanchal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class addPatient extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference patients;
    private long count;

    private long finalcount;
    private DatabaseReference patient_id;
    EditText e_name;
    EditText e_age;
    EditText e_phone;
    RadioButton selected;
    RadioGroup rg;
    String name;
    String age;
    String selectedDept;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        e_name = (EditText) findViewById(R.id.name);
        e_age = (EditText) findViewById(R.id.age);
        e_phone = (EditText) findViewById(R.id.phone);
        patients = mDatabase.child("Patients");

        addcountListner();


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(checkedId);
                selectedDept = rb.getText().toString();
            }
        });
    }


    public void submit(View v) {


        Toast.makeText(getApplicationContext(), "before listner count : "+count, Toast.LENGTH_SHORT).show();

        name = e_name.getText().toString();
        age = e_age.getText().toString();
        phone = e_phone.getText().toString();
        String id = selectedDept + "-" + count ;
        patient_id = patients.child(id);
        patient_id.child("Name").setValue(name);
        patient_id.child("Age").setValue(age);
        patient_id.child("Phone").setValue(phone);
        patient_id.child("Department").setValue(selectedDept);



        Toast.makeText(getApplicationContext(), "after listner count : "+count, Toast.LENGTH_SHORT).show();


    }

    private long addcountListner() {
        patients.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
                Toast.makeText(getApplicationContext(), "in listner count : "+count, Toast.LENGTH_SHORT).show();



            }
            public void onCancelled(DatabaseError databaseError) { }
        });

        return count;
    }
}
