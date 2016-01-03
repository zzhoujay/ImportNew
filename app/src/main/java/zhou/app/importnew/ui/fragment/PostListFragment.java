package zhou.app.importnew.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;

import zhou.app.importnew.App;
import zhou.app.importnew.data.PostProvider;
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

    private PostProvider provider;
    private PostRecyclerViewAdapter adapter;
    private Type type;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        type = bundle.getParcelable(Type.TYPE);

        provider = new PostProvider(type, App.getApp().crawler);
        adapter = new PostRecyclerViewAdapter(getContext());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewMaterialAdapter(adapter));

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);

        requestData();
    }

    private void setUpData(List<PostItem> postItems) {
        LogKit.i("post", postItems);
        if (postItems == null) {
            error(0);
        } else if (postItems.isEmpty()) {

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
