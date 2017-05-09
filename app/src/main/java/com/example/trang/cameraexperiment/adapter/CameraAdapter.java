package com.example.trang.cameraexperiment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.model.Image;

import java.util.ArrayList;

/**
 * Created by Trang on 4/10/2017.
 */

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraHolder> {
    private ArrayList<Image> arrayList;
    private Context mContext;
    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner ClickListner) {
        this.onItemClickListner = ClickListner;
    }

    public CameraAdapter(ArrayList<Image> arrayList, Context context) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @Override
    public CameraHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CameraHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(final CameraHolder holder, int position) {
        Image image = arrayList.get(position);
        holder.imageView.setImageBitmap(image.getData());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.ClickListner(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public class CameraHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public CameraHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_image_recycler);
        }
    }

    public interface OnItemClickListner {
        void ClickListner(int posisson);
    }
}
