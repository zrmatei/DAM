package eu.ase.ro.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.databinding.ActivityMainBinding;
import eu.ase.ro.damapp.databinding.FragmentAboutBinding;
import eu.ase.ro.damapp.fragments.AboutFragment;
import eu.ase.ro.damapp.fragments.HomeFragment;
import eu.ase.ro.damapp.fragments.ProfileFragment;
import eu.ase.ro.damapp.model.Expense;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private FloatingActionButton fabAdd;
    private ActivityResultLauncher<Intent> launcher;

    private List<Expense> expenses = new ArrayList<>();

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navView = findViewById(R.id.main_nav_view);
        navView.setNavigationItemSelectedListener(
                getItemSelected());
        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(getAddEvent());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                getAddExpenseCallback());

    }

    private ActivityResultCallback<ActivityResult> getAddExpenseCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Expense expense = (Expense) result.getData().getSerializableExtra(AddExpenseActivity.EXPENSE_KEY);
                expenses.add(expense);
                Log.i("MainActivity", "expenses = " + expenses.toString());
            }
        };
    }

    private View.OnClickListener getAddEvent() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
            launcher.launch(intent);
        };
    }

    private NavigationView.OnNavigationItemSelectedListener getItemSelected() {
        return item -> {
            if (item.getItemId() == R.id.nav_home){
                Toast.makeText(getApplicationContext(),
                        R.string.main_home_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_profile){
                Toast.makeText(getApplicationContext(),
                        R.string.main_profile_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = new ProfileFragment();
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.main_about_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = new AboutFragment();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            openFragment();
            return true;
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fl_container, currentFragment)
                .commit();
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
    }
}