package zhou.app.importnew.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import zhou.app.importnew.App;
import zhou.app.importnew.R;
import zhou.app.importnew.data.PostProvider;
import zhou.app.importnew.model.Post;
import zhou.appinterface.data.DataManager;
import zhou.widget.RichText;

/**
 * Created by zhou on 16-1-4.
 */
public class PostDetailActivity extends AppCompatActivity {

    public static final String URL = "url";

    private PostProvider provider;
    private RichText richText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;
    private String url;

    private MenuItem replce;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        richText = (RichText) findViewById(R.id.rich_text);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        url = intent.getStringExtra(URL);

        provider = new PostProvider(App.getApp().crawler, url);

        DataManager.get(provider, this::setUpData);

        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(() -> DataManager.update(provider, PostDetailActivity.this::setUpData));

        richText.setImageFixListener(holder -> {
            if (holder.getWidth() > 100 || holder.getHeight() > 100) {
                int width = getScreenWidth(getApplicationContext());
                int height = (int) (holder.getHeight() * 1f * width / holder.getWidth()) - 300;
                holder.setWidth(width);
                holder.setHeight(height);
                holder.setScaleType(RichText.ImageHolder.CENTER_INSIDE);
            }
        });

    }

    private void setUpData(Post post) {
        if (post == null) {
            return;
        }
        swipeRefreshLayout.setRefreshing(false);
        setTitle(post.title);
        if (flag) {
            webView.loadDataWithBaseURL("http://www.importnew.com/", post.content, null, "gbk", null);
        } else {
            richText.setRichText(post.content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        replce = menu.add(0, 10010, 0, R.string.menu_replce_web);
        menu.add(0, 10086, 0, R.string.menu_open_in_browser);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case 10010:
                webView.setVisibility(flag ? View.GONE : View.VISIBLE);
                richText.setVisibility(flag ? View.VISIBLE : View.GONE);
                replce.setTitle(getString(!flag ? R.string.menu_replce_text : R.string.menu_replce_web));
                flag = !flag;
                DataManager.get(provider, this::setUpData);
                return true;
            case 10086:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

}
