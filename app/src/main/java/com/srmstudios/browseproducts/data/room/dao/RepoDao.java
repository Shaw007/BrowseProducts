package com.srmstudios.browseproducts.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.srmstudios.browseproducts.data.room.model.Repo;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert
    void addRepo(Repo repo);

    @Query("select * from repo")
    List<Repo> getAllRepo();
}
