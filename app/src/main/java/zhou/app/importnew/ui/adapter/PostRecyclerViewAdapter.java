package zhou.app.importnew.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.mthli.slice.Slice;
import zhou.app.importnew.R;
import zhou.app.importnew.model.PostItem;

/**
 * Created by zhou on 16-1-2.
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.Holder> {

    private List<PostItem> postItems;
    private Context context;

    public PostRecyclerViewAdapter(List<PostItem> postItems, Context context) {
        this.postItems = postItems;
        this.context = context;
    }

    public PostRecyclerViewAdapter(List<PostItem> postItems) {
        this.postItems = postItems;
    }

    public PostRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public PostRecyclerViewAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.item_post, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PostItem postItem = postItems.get(position);

        Glide.with(context).load(postItem.thumb).crossFade(10).centerCrop().into(holder.icon);

        holder.title.setText(postItem.title);
        holder.content.setText(postItem.content);
        holder.time.setText(postItem.time);
        holder.type.setText(postItem.category);
        holder.reply.setText(postItem.reply);
    }

    @Override
    public int getItemCount() {
        return postItems == null ? 0 : postItems.size();
    }

    public void setPostItems(List<PostItem> postItems) {
        this.postItems = postItems;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title, content, time, type, reply;

        public Holder(View itemView) {
            super(itemView);

            Slice slice = new Slice(itemView.findViewById(R.id.parentPanel));

            slice.setElevation(4);
            slice.setRadius(4);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);
            type = (TextView) itemView.findViewById(R.id.type);
            reply = (TextView) itemView.findViewById(R.id.reply);
        }
    }
}
