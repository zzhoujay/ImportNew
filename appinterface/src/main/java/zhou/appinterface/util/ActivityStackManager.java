package zhou.appinterface.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by zhou on 15-10-23.
 * Activity栈管理
 */
public class ActivityStackManager {

    private static ActivityStackManager activityStackManager;

    public static ActivityStackManager getInstance() {
        if (activityStackManager == null) {
            activityStackManager = new ActivityStackManager();
        }
        return activityStackManager;
    }

    private ActivityStackManager() {
        activityStack = new Stack<>();
    }

    private Stack<Activity> activityStack;

    public void push(Activity activity) {
        activityStack.push(activity);
    }

    public Activity pop() {
        return activityStack.pop();
    }

    public void remove(Activity activity) {
        activityStack.remove(activity);
    }

    public void finishAll() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    public void finishUntilLast() {
        if (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            finishAll();
            push(activity);
        }
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }
}
