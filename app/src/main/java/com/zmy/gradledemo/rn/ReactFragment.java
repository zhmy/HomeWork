package com.zmy.gradledemo.rn;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.zmy.gradledemo.MainApplication;

/**
 * Created by zmy on 2016/10/11.
 */

public abstract class ReactFragment extends Fragment {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    public abstract String getMainComponentName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("zmy", getClass().getSimpleName() + " onAttach");
        mReactRootView = new ReactRootView(context);
        mReactInstanceManager = ((MainApplication)getActivity().getApplication()).getReactNativeHost().getReactInstanceManager();

    }

    @Nullable
    @Override
    public ReactRootView onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("zmy", getClass().getSimpleName() + " onCreateView");
        return mReactRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("zmy", getClass().getSimpleName() + " onActivityCreated");
        mReactRootView.startReactApplication(mReactInstanceManager, getMainComponentName(), null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mReactRootView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
