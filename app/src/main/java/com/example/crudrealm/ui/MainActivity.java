package com.example.crudrealm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crudrealm.R;
import com.example.crudrealm.crud.CRUDProfesor;
import com.example.crudrealm.model.Profesor;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private EditText nombreEt, emailEt;
    private Button btnGuardar;
    private Profesor profesor;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        configView();
    }

    private void configView(){
        profesor = new Profesor();
        nombreEt = findViewById(R.id.mainActivityEtNombre);
        emailEt = findViewById(R.id.mainActivityEtEmail);
        btnGuardar = findViewById(R.id.mainActivityBtnSave);

        //Listener para guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profesor.setName(nombreEt.getText().toString());
                profesor.setEmail(emailEt.getText().toString());
                CRUDProfesor.addProfesor(profesor);
            }
        });
    }
}