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

            //Añadimos nueva funcionalidad para que muestre listado de cursos que tiene asociados
            if(profesor.getCursos().size() > 0){

                for(int i=0; i<profesor.getCursos().size(); i++){
                    Log.d("CURSOS_PROFESOR", "Nombre_curso: "
                            + profesor.getCursos().get(i).getName() + " Duración: " +
                            profesor.getCursos().get(i).getDuration());
                }
            }else{
                Log.d("CURSOS_PROFESOR", "Sin cursos asociados");
            }
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

    /**Actualizar información de profesor por Id*/
    public final static void updateProfesorById(int id, String name, String email){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();

        //Esta es otra manera de hacer la transacción de lectura/escritura la
        //otra forma la vimos en la creación de profesores.
        realm.beginTransaction();
        Profesor profesor = realm.where(Profesor.class).equalTo("id", id).findFirst();
        //Asignamos los valores que traemos de profesor
        profesor.setName(name);
        profesor.setEmail(email);
        realm.insertOrUpdate(profesor);
        realm.commitTransaction();

        //Mostramos info en el Log para comprobar resultado
        Log.d("UPDATE", "Id: " + profesor.getId() + " Name: "
                + profesor.getName() + " Email: " + profesor.getEmail());
    }

    /**Borrar profesor por Id*/
    public final static void deleteProfesorById(int id){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();
        //Volvemos al método de transacción que vimos incialmente
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Profesor profesor = realm.where(Profesor.class)
                        .equalTo("id", id)
                        .findFirst();
                //Método que permite el borrado
                profesor.deleteFromRealm();
            }
        });
    }

    /**Borrar profesor por Nombre*/
    public static final void deleteProfesorByName(String name){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();
        //Volvemos al método de transacción que vimos incialmente
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Profesor profesor = realm.where(Profesor.class)
                        .equalTo("name", name)
                        .findFirst();
                if(profesor != null){
                    //Método que permite el borrado
                    profesor.deleteFromRealm();
                    //Lo mostramos en el Log
                    Log.d("BORRAR_BY_NAME", "Profesor con nombre " + name + " eliminado");
                }else{
                    Log.d("BORRAR_BY_NAME", "No existe ningún profesor con ese Nombre");
                }

            }
        });
    }

    /**Borrado de todos los registros de la clase Profesor*/
    public static final void deleteAllProfesor(){
        //Como siempre obtenemos la instancia de Realm
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

}
