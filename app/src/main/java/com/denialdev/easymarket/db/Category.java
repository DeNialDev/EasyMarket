package com.denialdev.easymarket.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "CategoryName")
    public String categoryName;
}
