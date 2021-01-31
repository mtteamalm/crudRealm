package com.example.crudrealm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudrealm.R;
import com.example.crudrealm.crud.CRUDProfesor;
import com.example.crudrealm.model.Profesor;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private EditText nombreEt, emailEt;
    private Button btnGuardar;
    private Button btnListado;
    private Button btnSearchName;
    private EditText searchIdEt;
    private Button btnSearchId;
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
        btnListado = findViewById(R.id.mainActivityBtnListar);
        btnSearchName = findViewById(R.id.mainActivityBtnSearchName);
        searchIdEt = findViewById(R.id.mainActivityEtSearchId);
        btnSearchId = findViewById(R.id.mainActivityBtnSearchId);

        //Listener para guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profesor.setName(nombreEt.getText().toString());
                profesor.setEmail(emailEt.getText().toString());
                CRUDProfesor.addProfesor(profesor);
            }
        });

        //Listener para listar los profesores
        btnListado.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CRUDProfesor.getAllProfesor();
            }
        });

        //Listener para buscar profesor por nombre
        btnSearchName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CRUDProfesor.getProfesorByName(nombreEt.getText().toString());
            }
        });

        //Listener para buscar profesor por Id
        btnSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchIdEt.getText().toString() == ""){
                    Toast.makeText(MainActivity.this, "Debes indicar un número de Id para buscar",
                            Toast.LENGTH_SHORT).show();
                }else{
                    CRUDProfesor.getProfesorById(Integer.parseInt(searchIdEt.getText().toString()));
                }
            }
        });

    }
}