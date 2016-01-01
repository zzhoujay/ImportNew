package zhou.appinterface.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import zhou.appinterface.R;

/**
 * Created by zhou on 15-12-14.
 */
public class ToolbarActivity extends StackActivity {

    protected Toolbar toolbar;
    protected boolean noToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_layout_toolbar);

        toolbar = (Toolbar) findViewById(R.id.interface_toolbar);

        if (noToolbar) {
            toolbar.setVisibility(View.GONE);
        } else {
            setSupportActionBar(toolbar);
        }
    }

    protected void setContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.interface_content, fragment).commit();
    }
}
