package com.leanhquan.notemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.leanhquan.notemanagementsystem.Common.Common;
import com.leanhquan.notemanagementsystem.Model.Piority;
import com.leanhquan.notemanagementsystem.UI.CategoryFragment;
import com.leanhquan.notemanagementsystem.UI.NoteFragment;
import com.leanhquan.notemanagementsystem.UI.PiorityFragment;
import com.leanhquan.notemanagementsystem.UI.StatusFragment;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout        drawer;
    private NavigationView      navigationView;
    private TextView            txtFullname, txtNameToolbar;
    private Toolbar             toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer,toolbar,
                R.string.navigation_drawer_opne,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View HeaderView = navigationView.getHeaderView(0);
        txtFullname = HeaderView.findViewById(R.id.txtFullName);
        txtFullname.setText(Common.currentUser.getUsername());



    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void showSignOutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Log out");
        alertDialog.setIcon(getDrawable(R.drawable.ic_log_out));
        alertDialog.setMessage("You wanna logout?");
        LayoutInflater inflater = LayoutInflater.from(this);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Paper.book().destroy();
                Intent signIn = new Intent(HomeActivity.this, MainActivity.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signIn);
            }
        });
        alertDialog.show();
    }

    private void init() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        txtNameToolbar = findViewById(R.id.nameToolbar);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragmentSelected = null;

            switch (id){
                case R.id.page_home:
                    Intent intentHome = new Intent(this, HomeActivity.class);
                    startActivity(intentHome);
                case R.id.page_category:
                    fragmentSelected = new CategoryFragment();
                    txtNameToolbar.setText("Category Management");
                    Toast.makeText(this, "Go to category", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_piority:
                    fragmentSelected = new PiorityFragment();
                    txtNameToolbar.setText("Piority Management");
                    Toast.makeText(this, "Go to piority", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_status:
                    fragmentSelected = new StatusFragment();
                    txtNameToolbar.setText("Status Management");
                    Toast.makeText(this, "Go to status", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_note:
                    fragmentSelected = new NoteFragment();
                    txtNameToolbar.setText("Notes Management");
                    Toast.makeText(this, "Go to note", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_edit_profile:
                    Toast.makeText(this, "Go to edti profile", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_change_password:
                    Toast.makeText(this, "Go to change password", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.page_logout:
                    showSignOutDialog();
                    break;
            }

        if (fragmentSelected != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_home, fragmentSelected);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
