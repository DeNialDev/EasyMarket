package com.denialdev.easymarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denialdev.easymarket.db.Category;
import com.denialdev.easymarket.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements CategoryListAdapter.HandlerCategoryClick {
    DrawerLayout drawerLayout;
    private TextView noResultTextView;
    private MainActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private CategoryListAdapter categoryListAdapter;
    private Category categoryForEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noResultTextView =  findViewById(R.id.noResult);
        recyclerView = findViewById(R.id.recycleView);
        drawerLayout = findViewById(R.id.drawerLayout);
        ImageView addNew = findViewById(R.id.addNewCategoryImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog(false);
            }
        });

        initViewModel();
        initRecyclerView();
        viewModel.getAllCategoryList();
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(DrawerLayout drawerLayout){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void CLickHome(View view){
        recreate();
    }
    public void ClickDashboard(View view){
        redirectActivity(this, DashBoard.class);
    }
    public void ClickAboutUs(View view){
        redirectActivity(this, AboutUs.class);

    }
    public void ClickLogout(View view){
        Logout(this);
    }

    public static void Logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter = new CategoryListAdapter(this, this);
        recyclerView.setAdapter(categoryListAdapter);
    }
    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getCategortyListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories == null){
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    categoryListAdapter.setCategoryList(categories);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }



    private void showAddCategoryDialog(boolean isForEdit){
        AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
        View dialogView = getLayoutInflater().inflate( R.layout.add_category_layout, null);
        EditText enterCategoryInput = dialogView.findViewById(R.id.enterCategoryInput);
        TextView createButton = dialogView.findViewById(R.id.createButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);
         if (isForEdit){
             createButton.setText("Update");
             enterCategoryInput.setText(categoryForEdit.categoryName);
         }
         cancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogBuilder.dismiss();
             }
         });
         createButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 dialogBuilder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                 String name = enterCategoryInput.getText().toString();
                 if(TextUtils.isEmpty(name)) {
                     Toast.makeText(MainActivity.this, "Enter category name", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 if(isForEdit){
                     categoryForEdit.categoryName = name;
                     viewModel.updateCategory(categoryForEdit);
                 } else {
                     //here we need to call view model.
                     viewModel.insertCategory(name);
                 }
                 dialogBuilder.dismiss();
             }
         });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }



    @Override
    public void itemClick(Category category) {

    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }

    @Override
    public void editItem(Category category) {
        this.categoryForEdit = category;
        showAddCategoryDialog(true);
    }
}