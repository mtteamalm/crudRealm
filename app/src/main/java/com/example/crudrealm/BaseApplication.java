package com.example.crudrealm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME) //Nombre de la base de datos, usamos la que ponga por defecto Realm
                .schemaVersion(0) //Versión de nuestra BBDD
                .deleteRealmIfMigrationNeeded()
                .build();

        //Establecemos la configuración por defecto que acabamos de crear
        Realm.setDefaultConfiguration(configuration);
    }
}
