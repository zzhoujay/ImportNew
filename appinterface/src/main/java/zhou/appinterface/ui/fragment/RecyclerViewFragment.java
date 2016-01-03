package zhou.appinterface.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhou.appinterface.R;

/**
 * Created by zhou on 15-12-14.
 */
public class RecyclerViewFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected View rootView;

    private Snackbar loadMoreSnackbar, errorSnackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.interface_layout_recycler_view, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.interface_swipeRefreshLayout);
        recyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.interface_recycler_view);

        afterInitView();

        return rootView;
    }

    protected void afterInitView() {

    }

    protected void onCancelLoadMore() {

    }

    protected void onRepeat(int reason) {

    }

    protected void onRefresh() {

    }

    protected void onLoadMore() {

    }

    protected void onSuccess() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadMore() {
        if (loadMoreSnackbar == null) {
            loadMoreSnackbar = Snackbar.make(rootView, R.string.interface_load_more, Snackbar.LENGTH_INDEFINITE);
            loadMoreSnackbar.setAction(R.string.interface_cancel, v -> {
                loadMoreSnackbar.dismiss();
                onCancelLoadMore();
            });
        }
        loadMoreSnackbar.show();
        onLoadMore();
    }

    public void error(int reason) {
        swipeRefreshLayout.setRefreshing(false);
        if (errorSnackbar == null) {
            errorSnackbar = Snackbar.make(rootView, R.string.interface_error, Snackbar.LENGTH_INDEFINITE);
            errorSnackbar.setAction(R.string.interface_repeat, v -> {
                errorSnackbar.dismiss();
                onRepeat(reason);
            });
        }
        errorSnackbar.show();
    }

    public void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (loadMoreSnackbar != null) {
            loadMoreSnackbar.dismiss();
        }
        if (errorSnackbar != null) {
            errorSnackbar.dismiss();
        }
        onRefresh();
    }

}
