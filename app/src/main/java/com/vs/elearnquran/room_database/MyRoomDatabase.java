package com.vs.elearnquran.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vs.elearnquran.dao.SurahDAO;
import com.vs.elearnquran.room_model.SurahRoom;

@Database(entities = {SurahRoom.class},exportSchema = false, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static MyRoomDatabase instance;

    public abstract SurahDAO surahDAO();

    public static synchronized MyRoomDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyRoomDatabase.class,"vs_e_learn_quran_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
