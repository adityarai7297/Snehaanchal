package org.snehaanchal.snehaanchal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Ref;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class addPatient extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference patients;
    private StorageReference mStorageRef;

    private long count;
    int id1 = -1, id2 = -1;
    private long finalcount;
    private DatabaseReference patient_id;
    EditText e_name;
    EditText e_age;
    EditText e_phone;
    EditText e_address;
    EditText e_aadhar;
    EditText e_dob;
    EditText e_religion;
    EditText e_caste;
    RadioGroup rg;
    RadioGroup gender;
    String name;
    String age;
    String selectedDept;
    String selectedGender;
    String phone;
    String address;
    String aadhar;
    String dob;
    String religion;
    String caste;
    String date_of_reg;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        gender = (RadioGroup) findViewById(R.id.gendergroup);
        e_name = (EditText) findViewById(R.id.name);
        e_age = (EditText) findViewById(R.id.age);
        e_phone = (EditText) findViewById(R.id.phone);
        e_address = (EditText) findViewById(R.id.address);
        e_aadhar = (EditText) findViewById(R.id.aadhar);
        e_dob = (EditText) findViewById(R.id.date_of_birth);
        e_religion = (EditText) findViewById(R.id.religion);
        e_caste = (EditText) findViewById(R.id.caste);
        patients = mDatabase.child("Patients");
        addcountListner();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                id1 = group.getCheckedRadioButtonId();
                RadioButton depbt = (RadioButton) findViewById(checkedId);
                selectedDept = depbt.getText().toString();
            }
        });


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gengroup, int genId) {
                id2 = gengroup.getCheckedRadioButtonId();
                RadioButton genbt = (RadioButton) findViewById(genId);
                selectedGender = genbt.getText().toString();
            }
        });

        date_of_reg = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        String years = new SimpleDateFormat("yy").format(Calendar.getInstance().getTime());
        int n = Integer.parseInt(years);
        n = n * 100 + (n + 1);
        year = Integer.toString(n);
    }


    public void submit(View v) {
        name = e_name.getText().toString();
        age = e_age.getText().toString();
        phone = e_phone.getText().toString();
        aadhar = e_aadhar.getText().toString();
        address = e_address.getText().toString();
        dob = e_dob.getText().toString();
        religion = e_religion.getText().toString();
        caste = e_caste.getText().toString();
        if (name.equals("") || age.equals("") || phone.equals("") || aadhar.equals("") || address.equals("") || id1 == -1 || id2 == -1) {
            Toast.makeText(this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            String id = selectedDept + "-" + count + "-" + year;
            patient_id = patients.child(id).child("Personal Details");
            patient_id.child("Name").setValue(name);
            patient_id.child("Age").setValue(age);
            patient_id.child("Phone").setValue(phone);
            patient_id.child("Department").setValue(selectedDept);
            patient_id.child("Address").setValue(address);
            patient_id.child("Aadhar").setValue(aadhar);
            patient_id.child("Date Of Birth").setValue(dob);
            patient_id.child("Gender").setValue(selectedGender);
            patient_id.child("Religion").setValue(religion);
            patient_id.child("Caste").setValue(caste);
            patient_id.child("Date Of Registration").setValue(date_of_reg);
            Toast.makeText(this, "Patient ID " + id + " Added!", Toast.LENGTH_SHORT).show();
        }
    }

    private long addcountListner() {
        patients.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return count;
    }
}