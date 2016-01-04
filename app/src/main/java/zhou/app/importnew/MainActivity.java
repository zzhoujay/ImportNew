package zhou.app.importnew;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.Arrays;

import zhou.app.importnew.common.Config;
import zhou.app.importnew.ui.fragment.PostListFragment;
import zhou.app.importnew.util.ColorKit;
import zhou.appinterface.util.LogKit;

/**
 * Created by zhou on 16-1-1.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialViewPager materialViewPager;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Toolbar toolbar = materialViewPager.getToolbar();


        if (toolbar != null) {

            toolbar.setNavigationIcon(null);

            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return PostListFragment.newInstance(Config.javaTypes.get(position));
            }

            @Override
            public int getCount() {
                return Config.javaTypes.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Config.javaTypes.get(position).name;
            }

        });

        materialViewPager.setMaterialViewPagerListener(page -> HeaderDesign.fromColorAndDrawable(ColorKit.getRandomColor(), new ColorDrawable(ColorKit.getRandomColor(Config.javaTypes.get(page).tag))));

        materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());


        TextView title = (TextView) findViewById(R.id.title);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

        title.setTypeface(typeface);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //    private void addFragment(Fragment fragment) {
//        currFragment = fragment;
//        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
//    }
//
//    private void replace(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().remove(currFragment).add(R.id.container, fragment).commit();
//        currFragment = fragment;
//    }
}
