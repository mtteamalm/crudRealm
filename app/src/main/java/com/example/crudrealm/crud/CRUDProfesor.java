package com.example.crudrealm.crud;

import android.util.Log;

import com.example.crudrealm.model.Profesor;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

    /**Listado de todos los profesores*/
    public static final List<Profesor> getAllProfesor(){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();

        //Con esta línea le dedimos a Realm que queremos todos los regitros de Profesor
        RealmResults<Profesor> profesors = realm.where(Profesor.class).findAll();

        //Lo recorremos para mostrarlo en un Log y ver que funciona
        for(Profesor profesor: profesors){
            Log.d("ITEM", "Id: " + profesor.getId() + " Name: "
            + profesor.getName() + " Email: " + profesor.getEmail());
        }
        //En realidad devolvemos esto para montarlo en una Activity con un ListView o un ReciclerView
        return profesors;
    }

    /** Obtener Profesor por nombre*/
    public static final Profesor getProfesorByName(String name){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();

        //Usamos de Real where, equalto y findFirst para obtener la primera ocurrencia en nuestra BBDD
        Profesor profesor = realm.where(Profesor.class).equalTo("name", name).findFirst();

        if(profesor != null){
            //Lo mostramos en el Log
            Log.d("BUSCAR_NOMBRE", "Id: " + profesor.getId() + " Name: "
                    + profesor.getName() + " Email: " + profesor.getEmail());
        }else{
            Log.d("BUSCAR_NOMBRE", "No existe ningún profesor con ese nombre");
        }

        return profesor;
    }

    /**Obtener Profesor por su ID*/
    public static final Profesor getProfesorById(Integer id){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();

        //Usamos de Real where, equalto y findFirst para obtener la primera ocurrencia en nuestra BBDD
        Profesor profesor = realm.where(Profesor.class).equalTo("id", id).findFirst();

        if(profesor != null){
            //Lo mostramos en el Log
            Log.d("BUSCAR_ID", "Id: " + profesor.getId() + " Name: "
                    + profesor.getName() + " Email: " + profesor.getEmail());
        }else{
            Log.d("BUSCAR_ID", "No existe ningún profesor con ese Id");
        }

        return profesor;
    }
}
