package com.promoteprovider.promotevideodownloader.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.promoteprovider.promotevideodownloader.Adapters.WhatsUpAdapter;
import com.promoteprovider.promotevideodownloader.Models.WhatsUpStatusModel;
import com.promoteprovider.promotevideodownloader.R;
import com.promoteprovider.promotevideodownloader.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment {
    private FragmentImageBinding binding;
    private ArrayList<WhatsUpStatusModel> list;
    private WhatsUpAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_image,container,false);
        list = new ArrayList<>();
        getData();

        binding.refresh.setOnRefreshListener(() ->{
            list = new ArrayList<>();
            getData();
            binding.refresh.setRefreshing(false);
        });

        return binding.getRoot();
    }
    private void getData() {
        WhatsUpStatusModel model;

        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp/Media/.statuses";
        File targetDirector = new File(targetPath);
        File[] allFiles = targetDirector.listFiles();

        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp Business/Media/.statuses";
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness = targetDirectorBusiness.listFiles();


        Arrays.sort(allFiles,((o1, o2) -> {
            if (o1.lastModified() > o2.lastModified()) return -1;
            else if (o1.lastModified() < o2.lastModified()) return +1;
            else return 0;
        }));

        for (int i =0; i < allFiles.length;i++){
            File file = allFiles[i];
            if (
                    Uri.fromFile(file).toString().endsWith(".mp4")){
                model = new WhatsUpStatusModel("joss"+i,
                        Uri.fromFile(file),
                        allFiles[i].getAbsolutePath(),
                        file.getName());
                list.add(model);
            }
        }
//        Arrays.sort(allFilesBusiness,((o1, o2) -> {
//            if (o1.lastModified() > o2.lastModified()) return -1;
//            else if (o1.lastModified() < o2.lastModified()) return +1;
//            else return 0;
//        }));
//
//        for (int i =0; i < allFilesBusiness.length;i++){
//            File file = allFilesBusiness[i];
//            if (
//                    Uri.fromFile(file).toString().endsWith(".mp4")){
//                model = new WhatsUpStatusModel("jossBusiness"+i,
//                        Uri.fromFile(file),
//                        allFilesBusiness[i].getAbsolutePath(),
//                        file.getName());
//                list.add(model);
//            }
//        }
        adapter = new WhatsUpAdapter(list,getActivity());
        binding.whatsUpRecyclerView.setAdapter(adapter);

    }
}