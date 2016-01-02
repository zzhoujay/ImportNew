package zhou.app.importnew;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zhou.app.importnew.ui.TabFragment;

/**
 * Created by zhou on 16-1-1.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(new TabFragment());
    }

    private void initView(View v) {
        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) v.findViewById(R.id.nav_view);
    }

    private void addFragment(Fragment fragment) {
        currFragment = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(currFragment).add(R.id.container, fragment).commit();
        currFragment = fragment;
    }
}
