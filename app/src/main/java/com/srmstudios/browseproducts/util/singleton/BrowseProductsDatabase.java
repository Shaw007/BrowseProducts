package com.srmstudios.browseproducts.util.singleton;

import android.content.Context;

import androidx.room.Room;

import com.srmstudios.browseproducts.data.room.AppDatabase;
import com.srmstudios.browseproducts.util.AppConstants;

public class BrowseProductsDatabase {
    private static AppDatabase appDatabase;

    private BrowseProductsDatabase() {

    }

    public synchronized static AppDatabase getInstance(Context context){
        if(appDatabase == null){
            return Room.databaseBuilder(context, AppDatabase.class, AppConstants.DATABASE_NAME)
                    //.addMigrations(MIGRATION_1_2)
                    .build();
            /*final Migration MIGRATION_1_2 = new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("");
                }
            };*/
        }
        return appDatabase;
    }
}













