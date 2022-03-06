package com.vs.elearnquran.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vs.elearnquran.model.HomeMenu;
import com.vs.elearnquran.repository.HomeMenuRepo;
import java.util.List;

public class HomeMenuViewModel extends ViewModel {

    private MutableLiveData<List<HomeMenu>> homeMenuList;
    private HomeMenuRepo repo;

    public void init(Context context){
        if(repo==null){
            repo = new HomeMenuRepo(context);
            homeMenuList = repo.setHomeMenuItems();
        }
    }

    public LiveData<List<HomeMenu>> getHomeMenuList(){
        return homeMenuList;
    }
}
