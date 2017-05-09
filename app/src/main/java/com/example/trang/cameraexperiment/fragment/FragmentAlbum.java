package com.example.trang.cameraexperiment.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.activity.MainActivity;
import com.example.trang.cameraexperiment.activity.PagerActivity;
import com.example.trang.cameraexperiment.adapter.CameraAdapter;
import com.example.trang.cameraexperiment.adapter.CameraViewPager;
import com.example.trang.cameraexperiment.manager.ImageManager;
import com.example.trang.cameraexperiment.model.Image;

import java.util.ArrayList;

/**
 * Created by Trang on 4/9/2017.
 */

public class FragmentAlbum extends Fragment implements View.OnClickListener, CameraAdapter.OnItemClickListner {
    public static final String POSITION = "position";
    private View view;
    private ImageManager manager;
    private ArrayList<Image> arrayList;
    private RecyclerView recyclerview;
    private Button textView;
    private CameraAdapter adapter;
    private ViewPager viewPager;
    private CameraViewPager cameraViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentalbum, container, false);

        initData();
        initView();
        return view;
    }

    private void initData() {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        manager = new ImageManager();
        arrayList = manager.getAll(getActivity());
        adapter = new CameraAdapter(arrayList, getActivity());
        GridLayoutManager gis = new GridLayoutManager(getActivity(), 4);
        gis.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(gis);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListner(this);
    }

    private void initView() {
        textView = (Button) view.findViewById(R.id.tv_edit_ablum);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit_ablum:
                ((MainActivity) getActivity()).showFragmentTakePhoto();
                break;
        }
    }

    @Override
    public void ClickListner(int posisson) {
        Intent intent = new Intent(getActivity(), PagerActivity.class);
        intent.putExtra(POSITION, posisson);
        getActivity().startActivity(intent);
    }


}
