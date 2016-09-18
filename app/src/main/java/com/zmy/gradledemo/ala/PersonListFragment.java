package com.zmy.gradledemo.ala;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zmy.gradledemo.R;
import com.zmy.gradledemo.ala.adapter.PersonListAdapter;
import com.zmy.gradledemo.ala.data.PersonFansData;
import com.zmy.gradledemo.ala.data.PersonFollowsData;
import com.zmy.gradledemo.ala.data.PersonRecordsData;
import com.zmy.gradledemo.ala.data.PersonUserData;
import com.zmy.gradledemo.ala.data.ServerCommonData;
import com.zmy.library.BdListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by zmy on 16/9/15.
 */
public class PersonListFragment extends Fragment {

    private PersonListAdapter adapter;
    private BdListView listview;
    private int position;
    private AssetManager assetManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager = getActivity().getAssets();
        position = getArguments().getInt("position", 1);
        Log.e("zmy", "position = " + position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_list, null);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initData() {
        Object data = getData();
        switch (position) {
            case 0:
                PersonRecordsData.Data recordData = ((PersonRecordsData) data).getData();
                adapter.setData(recordData.getFeed_list(), PersonUserData.PERSON_RECORDS_TYPE);
                break;
            case 1:
                PersonFansData.Data fansData = ((PersonFansData) data).getData();
                adapter.setData(fansData.getList(), PersonUserData.PERSON_FANS_TYPE);
                break;
            case 2:
                PersonFollowsData.Data followsData = ((PersonFollowsData) data).getData();
                adapter.setData(followsData.getList(), PersonUserData.PERSON_FOLLOWS_TYPE);
                break;
            default:
                break;
        }
    }

    private void initView(View rootView) {
        listview = (BdListView) rootView.findViewById(R.id.listview);
        adapter = new PersonListAdapter(getContext());
        listview.setAdapter(adapter);
    }

    private Object getData() {
        String filename = "";
        Gson gson = new Gson();
        Object data = null;
        switch (position) {
            case 0:
                filename = "getRecord.txt";
//             Type   listType = new TypeToken<List<PersonUserData>>() {
//        }.getType();
                break;
            case 1:
                filename = "getFans.txt";
                break;
            case 2:
                filename = "getFollows.txt";
                break;
        }
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String local = readTextFile(inputStream);

        switch (position) {
            case 0:
                 data = gson.fromJson(local, PersonRecordsData.class);
                break;
            case 1:
                data = gson.fromJson(local, PersonFansData.class);
                break;
            case 2:
                data = gson.fromJson(local, PersonFollowsData.class);
                break;
        }
        return data;
    }

    private String readTextFile(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
    }
}
