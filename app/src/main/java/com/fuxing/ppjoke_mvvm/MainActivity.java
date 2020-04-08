package com.fuxing.ppjoke_mvvm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.fuxing.ppjoke_mvvm.model.Destination;
import com.fuxing.ppjoke_mvvm.utils.AppConfig;
import com.fuxing.ppjoke_mvvm.utils.NavGraphBuilder;
import com.fuxing.ppjoke_mvvm.view.AppBottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private AppBottomBar navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = NavHostFragment.findNavController(fragment);

        navView.setOnNavigationItemSelectedListener(this);
        NavGraphBuilder.build(this, navController, fragment.getId());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();

        Iterator<Map.Entry<String, Destination>> iterable = destConfig.entrySet().iterator();

        while (iterable.hasNext()) {
            Map.Entry<String, Destination> entry = iterable.next();
            Destination value = entry.getValue();
//            if (value != null)
        }

        navController.navigate(menuItem.getItemId());
        return !TextUtils.isEmpty(menuItem.getTitle());
    }
}
