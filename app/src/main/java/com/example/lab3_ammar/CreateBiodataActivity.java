package com.example.lab3_ammar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateBiodataActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    EditText textId, textName, textDob, textGender, textAddress;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_biodata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataHelper(this);

        textId = findViewById(R.id.editTextId);
        textName = findViewById(R.id.editTextName);
        textDob = findViewById(R.id.editTextDob);
        textGender = findViewById(R.id.editTextGender);
        textAddress = findViewById(R.id.editTextAddress);
        btnSave = findViewById(R.id.buttonSave);
        btnBack = findViewById(R.id.buttonBack);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO biodata(no, name, dob, gender, address) VALUES('" +
                        textId.getText().toString() + "','" +
                        textName.getText().toString() + "','" +
                        textDob.getText().toString() + "','" +
                        textGender.getText().toString() + "','" +
                        textAddress.getText().toString() + "')");

                Toast.makeText(getApplicationContext(), "Data Successfully Added", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}