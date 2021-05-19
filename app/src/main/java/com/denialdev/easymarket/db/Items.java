package com.denialdev.easymarket.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Items {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "categoryId")

    public int categoryId;

    @ColumnInfo(name = "completed")
    public boolean completed;
}
