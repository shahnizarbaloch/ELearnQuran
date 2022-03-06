package com.vs.elearnquran.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import com.vs.elearnquran.adapter.HomeMenuAdapter;
import com.vs.elearnquran.databinding.FragmentHomeBinding;
import com.vs.elearnquran.model.HomeMenu;
import com.vs.elearnquran.viewmodel.HomeMenuViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements HomeMenuAdapter.OnMyOwnClickListener {

    private FragmentHomeBinding binding;
    private NavController navController;
    private HomeMenuViewModel viewModel;
    private HomeMenuAdapter adapter;
    private List<HomeMenu> list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        initialize();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    /**
     * method to initialize components
     */
    private void initialize(){
        list = new ArrayList<>();
        viewModel = new HomeMenuViewModel();
        viewModel.init(requireContext());

        viewModel.getHomeMenuList().observe(requireActivity(),menuItems->{
            list = menuItems;
            adapter = new HomeMenuAdapter(requireContext(),menuItems,this);
            binding.rvHomeMenu.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 1);
            binding.rvHomeMenu.setLayoutManager(layoutManager);

        });

        setGregorianAndIslamicDate();
        binding.cardViewMenu.setOnClickListener(e->navController.navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment()));
    }

    /**
     * method to set islamic date and english date
     */
    private void setGregorianAndIslamicDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d");
        String date = dateFormat.format(new Date());
        binding.tvDate.setText(date);
    }

    @Override
    public void onMyOwnClick(int position, View view) {
        HomeMenu obj = list.get(position);
        if (position==0){
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSurahFragment());
        }
        else if (position==3){
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment());
        }
    }
}
