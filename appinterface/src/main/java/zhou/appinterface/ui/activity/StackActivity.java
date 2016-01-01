package zhou.appinterface.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import zhou.appinterface.util.ActivityStackManager;

/**
 * Created by zhou on 15-10-23.
 * 添加了栈管理的Activity
 */
public class StackActivity extends AppCompatActivity {

    private boolean hasInit = false;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (!hasInit) {
            ActivityStackManager.getInstance().push(this);
            hasInit = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().push(this);
        if (!hasInit) {
            ActivityStackManager.getInstance().push(this);
            hasInit = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().remove(this);
    }

}
