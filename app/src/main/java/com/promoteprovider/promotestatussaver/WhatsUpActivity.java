package com.promoteprovider.promotestatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayoutMediator;
import com.promoteprovider.promotestatussaver.Fragments.ImageFragment;
import com.promoteprovider.promotestatussaver.Fragments.VideoFragment;
import com.promoteprovider.promotestatussaver.databinding.ActivityMainBinding;
import com.promoteprovider.promotestatussaver.databinding.ActivityWhatsUpBinding;

import java.util.ArrayList;
import java.util.List;

public class WhatsUpActivity extends AppCompatActivity {
    private ActivityWhatsUpBinding binding;
    private WhatsUpActivity activity;
    private viewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_whats_up);
        activity = this;

        intiView();

    }

    private void intiView() {
        adapter = new viewPagerAdapter(activity.getSupportFragmentManager(),
                activity.getLifecycle());
        adapter.addFragment(new ImageFragment(),"Images");
        adapter.addFragment(new VideoFragment(),"Videos");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout,binding.viewPager,
                ((tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
                } )).attach();

        for (int i = 0;i<binding.tabLayout.getTabCount();i++){
            TextView tv = (TextView) LayoutInflater.from(activity)
                    .inflate(R.layout.custom_tab,null);

            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }


    }

    class viewPagerAdapter extends FragmentStateAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public viewPagerAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fragmentManager, @NonNull @org.jetbrains.annotations.NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment,String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}