package com.denialdev.easymarket.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Locale;

@Database(entities = {Category.class, Items.class}, version =  1)
public abstract class AppData extends RoomDatabase {
    public static AppData INSTANCE;

    public static AppData getDBInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppData.class, "AppDB").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
