package zhou.app.importnew.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by zhou on 16-1-2.
 */
public class BaseFragment extends Fragment {

    protected AppCompatActivity getAppCompatActivity() {
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            return (AppCompatActivity) activity;
        }
        return null;
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        AppCompatActivity appCompatActivity = getAppCompatActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
    }

    protected ActionBar getSupportActionBar() {
        AppCompatActivity appCompatActivity = getAppCompatActivity();
        if (appCompatActivity != null) {
            return appCompatActivity.getSupportActionBar();
        }
        return null;
    }

}
