package com.example.crudrealm.crud;

import com.example.crudrealm.model.Profesor;

import io.realm.Realm;

public class CRUDProfesor {

    /**Método para generar ID autoincremental*/
    private final static int calculateId(){
        int nextId;

        Realm realm = Realm.getDefaultInstance(); //Obtenemos la instancia por defecto

        /*Guardamos el valor máximo de id de todos los objetos de la clase profesor*/
        Number currentId = realm.where(Profesor.class).max("id");

        //Analizamos el currentId
        if(currentId == null){
            nextId = 0;
        }else{
            nextId = currentId.intValue() + 1;
        }
        return nextId;
    }

    /**Método para guardar un Profesor*/
    public final static void addProfesor(final Profesor profesor){
        Realm realm = Realm.getDefaultInstance();
        //Generamos una transacción donde generamos nuevo id y llamamos
        // al método createObject de realm.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = CRUDProfesor.calculateId();

                Profesor realmProfesor = realm.createObject(Profesor.class, index);

                //Establecemos los valores del objeto Profesor que nos llega por parámetro
                realmProfesor.setName(profesor.getName());
                realmProfesor.setEmail(profesor.getEmail());
            }
        });
    }
}
