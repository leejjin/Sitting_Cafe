package com.example.hyunm.sittingcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {
    Button btn_location;
    ArrayAdapter<CharSequence> schoolSpin;
    EditText edit_school;
    String schoolText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final Spinner spinner = (Spinner)findViewById(R.id.spin_school);
        edit_school = (EditText)findViewById(R.id.edit_school);
        btn_location = (Button)findViewById(R.id.btn_location);
        schoolSpin = ArrayAdapter.createFromResource(this, R.array.spinner_school, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(schoolSpin);

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_school.getText().toString().isEmpty()){
                    String selectedSchool = spinner.getSelectedItem().toString();
                    if(selectedSchool.equals("선택 안함")) {
                        Toast.makeText(LocationActivity.this, "학교명을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v("TAG", "school= " + selectedSchool);
                        Intent intent = new Intent(LocationActivity.this, CafeDataActivity.class);
                        intent.putExtra("school", selectedSchool);
                        startActivity(intent);
                    }
                }
                else {
                    schoolText = edit_school.getText().toString();
                    Log.v("TAG", "school= " + schoolText);
                    Intent intent = new Intent(LocationActivity.this, CafeDataActivity.class );
                    intent.putExtra("school", schoolText);
                    startActivity(intent);
                }
            }
        });
    }
}
