package com.example.crudrealm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private Button btnUpdateProfesor;
    private Button btnDeleteId, btnDeleteName;
    private Button btnCursosProfesor;
    private Profesor profesor;
    private CheckBox miIngrediente;
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
        btnUpdateProfesor = findViewById(R.id.mainActivityBtnUpdate);
        btnDeleteId = findViewById(R.id.mainActivityBtnDeleteId);
        btnDeleteName = findViewById(R.id.mainActivityBtnDeleteName);
        btnCursosProfesor = findViewById(R.id.mainActivityBtnCursosActivity);
        miIngrediente = findViewById(R.id.mainActivityChkIngrediente);


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
                Profesor activityProfesor =  CRUDProfesor.getProfesorByName(nombreEt.getText().toString());
                if(activityProfesor != null){

                    emailEt.setText(activityProfesor.getEmail());
                }else{
                    Toast.makeText(MainActivity.this, "ERROR: No existe ningún profesor con ese nombre"
                            , Toast.LENGTH_SHORT).show();
                }
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
                    Profesor activityProfesor = CRUDProfesor.getProfesorById(Integer.parseInt(searchIdEt.getText().toString()));
                    if(activityProfesor != null){
                        nombreEt.setText(activityProfesor.getName());
                        emailEt.setText(activityProfesor.getEmail());
                    }else {
                        Toast.makeText(MainActivity.this, "ERROR: No existe ningún profesor con ese Id"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Listener para actualizar información de profesor
        btnUpdateProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nombreEt.getText().toString();
                String email = emailEt.getText().toString();

                if(name == "" || email == ""){
                    Toast.makeText(MainActivity.this,
                            "ERROR: Revisa los campos nombre e Email",
                            Toast.LENGTH_SHORT).show();
                }else{
                    int id = Integer.parseInt(searchIdEt.getText().toString());
                    CRUDProfesor.updateProfesorById(id, name, email);
                    Toast.makeText(MainActivity.this,
                            "Profesor actualizado correctamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listener para borrado de profesor por ID
        btnDeleteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(searchIdEt.getText().toString());
                CRUDProfesor.deleteProfesorById(id);
                Toast.makeText(MainActivity.this,
                        "Profesor eliminado correctamente",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Listener para borrado de profesor por Nombre
        btnDeleteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombreEt.getText().toString();
                CRUDProfesor.deleteProfesorByName(name);
            }
        });

        //Listener para ir a la actividad de Cursos
        btnCursosProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mediante un Intent hacemos la llamada a nuestra activity
                startActivity(new Intent(getApplicationContext(), CursoActivity.class));
            }
        });

        //Listener del Checkbox
        miIngrediente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    Profesor activityProfesor = CRUDProfesor.getProfesorById(Integer.parseInt(searchIdEt.getText().toString()));
                    if(activityProfesor != null){
                        nombreEt.setText(activityProfesor.getName());
                        emailEt.setText(activityProfesor.getEmail());
                    }else {
                        Toast.makeText(MainActivity.this, "ERROR: No existe ningún profesor con ese Id"
                                , Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(MainActivity.this, "Me acabas de clickar", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Estoy desmarcado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}