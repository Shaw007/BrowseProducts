package com.srmstudios.browseproducts.util.singleton;

import android.content.Context;

import androidx.room.Room;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.util.AppConstants;

public class BrowseProductsDatabase {
    private static BrowseProductsDatabase browseProductsDatabase;
    private AppDatabase appDatabase;

    private BrowseProductsDatabase(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, AppConstants.DATABASE_NAME)
                //.addMigrations(MIGRATION_1_2)
                .build();
        /*final Migration MIGRATION_1_2 = new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("");
                }
            };*/
    }

    public synchronized static BrowseProductsDatabase getInstance(Context context){
        if(browseProductsDatabase == null){
            browseProductsDatabase = new BrowseProductsDatabase(context);
        }
        return browseProductsDatabase;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}













