package com.denialdev.easymarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.denialdev.easymarket.db.AppData;
import com.denialdev.easymarket.db.Category;

import java.util.List;

public class MainActivityViewModel  extends AndroidViewModel {
    private MutableLiveData<List<Category>> listOfCategory;
    private AppData appDataBase;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        listOfCategory = new MutableLiveData<>();
        appDataBase = AppData.getDBInstance(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Category>> getCategortyListObserver() {
        return listOfCategory;
    }

    public void getAllCategoryList(){
        List<Category>  categoryList = appDataBase.shoppingListDao().getAllCategoriesList();
        if (categoryList.size() > 0){
            listOfCategory.postValue(categoryList);
        }else {
            listOfCategory.postValue(null);
        }
    }

    public void insertCategory(String catName){

        Category category = new Category();
        category.categoryName = catName;
        appDataBase.shoppingListDao().insertCategory(category);
        getAllCategoryList();
    }
    public void updateCategory(Category category){

        appDataBase.shoppingListDao().updateCategory(category);
        getAllCategoryList();
    }

    public void deleteCategory(Category category){

        appDataBase.shoppingListDao().deleteCategory(category);
        getAllCategoryList();
    }
}
