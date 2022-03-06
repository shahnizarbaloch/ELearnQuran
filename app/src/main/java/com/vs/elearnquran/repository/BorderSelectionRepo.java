package com.vs.elearnquran.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vs.elearnquran.R;

import java.util.ArrayList;
import java.util.List;

public class BorderSelectionRepo {


    public LiveData<List<Integer>> getBorderList(){
        MutableLiveData<List<Integer>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(setBorders());
        return mutableLiveData;
    };

    private List<Integer> setBorders(){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.border_1);
        list.add(R.drawable.border_2);
        list.add(R.drawable.border_3);
        list.add(R.drawable.border_4);

        return list;
    }
}
