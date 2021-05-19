package com.denialdev.easymarket.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingList {

    @Query("Select * from Category")
    List<Category> getAllCategoriesList();

    @Insert
    void InsertCategory(Category...categories);

    @Update
    void UpdateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("Select * from Items where categoryId = :catId ")
    List<Items> getAllItemsList(int catId);

    @Insert
    void insertItems(Items items);

    @Update
    void updateItems(Items items);

    @Delete
    void deleteItems(Items items);
}
