package com.example.trang.cameraexperiment.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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

public class CameraViewPager extends PagerAdapter{

    private Context mContext;
    private ArrayList<Image> arrayList;

    public CameraViewPager(Context mContext, ArrayList<Image> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view.equals(object)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_viewpager, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_view_pager);
        imageView.setImageBitmap(arrayList.get(position).getData());
        container.addView(view);
        return view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position).getTitle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
