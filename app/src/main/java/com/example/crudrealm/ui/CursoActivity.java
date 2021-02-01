package com.example.crudrealm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crudrealm.R;
import com.example.crudrealm.crud.CRUDCurso;
import com.example.crudrealm.model.Curso;

public class CursoActivity extends AppCompatActivity {

        private EditText idProfesor, nombreCurso, duracionCurso;
        private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        configView();
    }

    private void configView() {

        idProfesor = findViewById(R.id.cursoActivityEtIdProfesor);
        nombreCurso = findViewById(R.id.cursoActivityEtNombreCurso);
        duracionCurso = findViewById(R.id.cursoActivityEtDuracionCurso);
        btnGuardar = findViewById(R.id.cursoActivityBtnGuardarCurso);

        //Generamos Listener para guardar los cursos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Curso newCurso = new Curso();
                int id = Integer.parseInt(idProfesor.getText().toString());
                newCurso.setName(nombreCurso.getText().toString());
                newCurso.setDuration(duracionCurso.getText().toString());
                CRUDCurso.addCurso(id,newCurso);
            }
        });

    }
}