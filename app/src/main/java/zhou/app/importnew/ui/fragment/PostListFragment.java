package zhou.app.importnew.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;

import zhou.app.importnew.App;
import zhou.app.importnew.R;
import zhou.app.importnew.data.PostItemProvider;
import zhou.app.importnew.data.Type;
import zhou.app.importnew.model.PostItem;
import zhou.app.importnew.ui.adapter.PostRecyclerViewAdapter;
import zhou.appinterface.data.DataManager;
import zhou.appinterface.ui.fragment.RecyclerViewFragment;
import zhou.appinterface.util.LogKit;

/**
 * Created by zhou on 16-1-3.
 */
public class PostListFragment extends RecyclerViewFragment {

    private PostItemProvider provider;
    private PostRecyclerViewAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        Type type = bundle.getParcelable(Type.TYPE);

        provider = new PostItemProvider(type, App.getApp().crawler);
        adapter = new PostRecyclerViewAdapter(getContext());

        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new RecyclerViewMaterialAdapter(adapter));

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (manager.findLastVisibleItemPosition() >= adapter.getItemCount() - 1) {
                        loadMore();
                    }
                }
            }
        });

        requestData();
    }

    private void setUpData(List<PostItem> postItems) {
        swipeRefreshLayout.setRefreshing(false);
        if (postItems == null) {
            error(0);
        } else {
            onSuccess();
            adapter.setPostItems(postItems);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void requestData() {
        swipeRefreshLayout.setRefreshing(true);
        DataManager.get(provider, this::setUpData);
    }

    @Override
    protected void onCancelLoadMore() {
        super.onCancelLoadMore();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        DataManager.more(provider, postItems -> {
            setUpData(postItems);
            Toast.makeText(getContext(), R.string.toast_load_success, Toast.LENGTH_SHORT).show();
            loadMoreSnackbar.dismiss();
        });
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        DataManager.update(provider, this::setUpData);
    }

    @Override
    protected void onRepeat(int reason) {
        super.onRepeat(reason);
    }

    @Override
    protected void onSuccess() {
        super.onSuccess();
    }

    public static PostListFragment newInstance(Type type) {
        PostListFragment postListFragment = new PostListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Type.TYPE, type);
        postListFragment.setArguments(bundle);
        return postListFragment;
    }
}
