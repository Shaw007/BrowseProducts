package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from user where email=:email")
    User getUser(String email);


}
