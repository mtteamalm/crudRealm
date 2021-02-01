package com.example.crudrealm.crud;

import com.example.crudrealm.model.Curso;
import com.example.crudrealm.model.Profesor;

import io.realm.Realm;

public class CRUDCurso {

    /**Método para añadir un nuevo curso*/
    public final static void addCurso(final int profesorId, final Curso curso){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Obtenemos el profesor al que vamos a asignar el curso
                Profesor profesor = realm.where(Profesor.class)
                        .equalTo("id", profesorId)
                        .findFirst();
                //Añadimos este curso a través del método que nos devolvía una lista
                // de Realm con todos los cursos asociados. Con esto queda creada la relación 1:N
                profesor.getCursos().add(curso);
                //Por último llamamos al método de Insertar o Actualizar Profesor
                realm.insertOrUpdate(profesor);
            }
        });
    }
}
