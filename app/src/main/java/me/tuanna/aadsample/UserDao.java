package me.tuanna.aadsample;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user")
    DataSource.Factory<Integer, User> getAllPaged();

    @Insert
    void insertAll(List<User> users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
