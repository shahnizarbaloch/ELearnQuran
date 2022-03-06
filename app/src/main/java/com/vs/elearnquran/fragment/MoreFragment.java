package com.vs.elearnquran.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
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
import com.vs.elearnquran.R;
import com.vs.elearnquran.activity.MainActivity;
import com.vs.elearnquran.adapter.MoreAdapter;
import com.vs.elearnquran.databinding.DialogConfirmationBinding;
import com.vs.elearnquran.databinding.FragmentMoreBinding;
import com.vs.elearnquran.model.MoreMenu;
import com.vs.elearnquran.repository.MoreRepo;
import com.vs.elearnquran.usage.ConstantVariables;
import com.vs.elearnquran.usage.DeviceInfo;
import com.vs.elearnquran.usage.MySharedPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoreFragment extends Fragment implements MoreAdapter.OnMyOwnClickListener {

    private FragmentMoreBinding binding;
    private NavController navController;
    MoreRepo repo;
    List<MoreMenu> list;
    private String versionName="1.0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater,container,false);

        binding.llToolbar.toolbarTitle.setText(getResources().getString(R.string.settings));
        binding.llToolbar.imgBackArrow.setOnClickListener(e->navController.popBackStack());

        initialize();

        return binding.getRoot();
    }

    /**
     * method to initialize components
     */
    @SuppressLint("SetTextI18n")
    private void initialize() {
        list = new ArrayList<>();
        repo = new MoreRepo(requireContext());
        repo.getMoreMenuItems().observe(getViewLifecycleOwner(),moreMenus -> {
            list = moreMenus;
            MoreAdapter adapter = new MoreAdapter(requireContext(),moreMenus,this);
            binding.rvMore.setAdapter(adapter);
            binding.rvMore.setLayoutManager(new GridLayoutManager(requireContext(),1));
        });

        int versionCode = 1;
        try{
            versionName = requireContext().getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0).versionName;

            versionCode = requireContext().getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        binding.tvAppVersion.setText("App version : "+versionName +" - Build "+versionCode);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onMyOwnClick(int position, View view) {
        MoreMenu obj = list.get(position);
        if (obj.getTitle().equalsIgnoreCase(getString(R.string.change_theme))){
            navController.navigate(MoreFragmentDirections.actionMoreFragmentToBorderSelectionFragment());
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.contact_us))){
            composeEmail();
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.privacy_policy))){
            navController.navigate(MoreFragmentDirections.actionMoreFragmentToPrivacyPolicyFragment());
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.home_menu_share))){
            shareApp(requireContext());
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.feedback))){
            gotoPlayStore(requireContext());
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.remove_ads))){
            removeAds(getString(R.string.for_one_year),getString(R.string.for_life_time));
        }
        else if (obj.getTitle().equalsIgnoreCase(getString(R.string.change_app_language))){
            showChangeLanguageDialog();
        }
    }

    String selectedLanguage="en";
    private void showChangeLanguageDialog() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.select_language));
        // add a list
        String[] animals = {"English", "Urdu", "Arabic", "Persian"};
        builder.setItems(animals, (dialog, which) -> {
            switch (which) {
                case 0: // English
                    selectedLanguage="en";
                    break;
                case 1: // Urdu
                    selectedLanguage="ur";
                    break;
                case 2: // Arabic
                    selectedLanguage="ar";
                    break;
                case 3: // Persian
                    selectedLanguage="fa";
                    break;

            }
            changeLanguage(selectedLanguage);
            dialog.dismiss();
        }
        );

    // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeLanguage(String selectedLanguage) {
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        config.setLayoutDirection(Locale.ENGLISH);
        getResources().updateConfiguration(config,
                requireContext().getResources().getDisplayMetrics());

        MySharedPreferences.setStringValue(requireContext(), ConstantVariables.APP_LANG,selectedLanguage);


        Intent refresh = new Intent(requireContext(), MainActivity.class);
        startActivity(refresh);
        requireActivity().finish();
    }

    /*
     * method to show the dialog where user writes something about contact us.
     */
    /*private void showContactUsDialog() {
        Dialog dialog = new Dialog(requireContext(), R.style.Dialog);*//*
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);*//*
        DialogContactUsWriteSomethingBinding dialogBinding = DialogContactUsWriteSomethingBinding.inflate(LayoutInflater.from(requireContext()));
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.btnSave.setOnClickListener(e -> {
            dialog.dismiss();
            composeEmail(dialogBinding.etText.getText().toString());
        });

        dialogBinding.imgClose.setOnClickListener(e -> dialog.dismiss());

        dialog.show();
    }*/

    /**
     * method to go to play store link of the app
     */
    private void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download "+getString(R.string.app_name)+"\n https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    private void gotoPlayStore(Context context){
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    /**
     * method to show dialog of remove ads
     * @param button1Text button 1 text
     * @param button2Text button 2 text
     */
    private void removeAds(String button1Text,String button2Text){
        final Dialog dialog = new Dialog(requireContext(),R.style.Dialog);
        DialogConfirmationBinding dialogBinding = DialogConfirmationBinding.inflate(LayoutInflater.from(requireContext()));
        dialog.setContentView(dialogBinding.getRoot());

        dialog.setCancelable(true);

        dialogBinding.heading.setText(getString(R.string.remove_ads));
        dialogBinding.textDetails.setText(getString(R.string.remove_ads_text));

        dialogBinding.btn1.setText(button1Text);
        dialogBinding.btn2.setText(button2Text);

        dialogBinding.btn1.setOnClickListener(e->dialog.dismiss());
        dialogBinding.btn2.setOnClickListener(e->dialog.dismiss());

        dialog.show();
    }

    /**
     * method to write email for contact with all the details of the device.
     */
    public void composeEmail() {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        Uri data = Uri.parse("mailto:?subject="
                + "App Support Android (Version : "+versionName+")"
                + "&body=" + "\n\n\n"+new DeviceInfo().getDeviceDetails()
                + "&to=" + "zubair.bsit@gmail.com");
        mailIntent.setData(data);
        startActivity(Intent.createChooser(mailIntent, "Contact Us"));
    }
}
