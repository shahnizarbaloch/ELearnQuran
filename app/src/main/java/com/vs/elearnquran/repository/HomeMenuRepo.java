package com.vs.elearnquran.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.vs.elearnquran.R;
import com.vs.elearnquran.model.HomeMenu;
import java.util.ArrayList;
import java.util.List;

public class HomeMenuRepo {

    private final Context context;
    public HomeMenuRepo(Context context){
        this.context = context;
    }

    public MutableLiveData<List<HomeMenu>> setHomeMenuItems(){
        MutableLiveData<List<HomeMenu>> data = new MutableLiveData<>();
        List<HomeMenu> homeMenuList = new ArrayList<>();
        homeMenuList.add(new HomeMenu(context.getString(R.string.home_menu_start_reading),
                R.drawable.ic_home_menu_start_reading));
        homeMenuList.add(new HomeMenu(context.getString(R.string.home_menu_bookmarks),
                R.drawable.ic_home_menu_bookmarked));
        homeMenuList.add(new HomeMenu(context.getString(R.string.home_menu_qari_selection),
                R.drawable.ic_home_menu_selection_qari));
        homeMenuList.add(new HomeMenu(context.getString(R.string.home_menu_settings),
                R.drawable.ic_home_menu_settings));
        data.setValue(homeMenuList);
        return data;
    }
}
