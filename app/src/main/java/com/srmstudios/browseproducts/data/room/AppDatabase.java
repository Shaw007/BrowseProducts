package com.srmstudios.browseproducts.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.srmstudios.browseproducts.data.room.dao.ProductDao;
import com.srmstudios.browseproducts.data.room.dao.UserDao;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.data.room.model.User;

@Database(entities = {
        User.class,
        Product.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

    public abstract ProductDao getProductDao();
}
