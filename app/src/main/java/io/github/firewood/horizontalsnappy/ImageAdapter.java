package io.github.firewood.horizontalsnappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Integer> mDataList;

    public ImageAdapter(Context context, List<Integer> dataList) {
        super();
        mDataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.list_image, parent, false);

        // resize
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = parent.getMeasuredWidth();
        params.height = parent.getMeasuredHeight();
        v.setLayoutParams(params);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(mDataList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.imageView);
        }
    }
}
