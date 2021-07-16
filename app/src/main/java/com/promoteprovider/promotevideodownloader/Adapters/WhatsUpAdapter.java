package com.promoteprovider.promotevideodownloader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.promoteprovider.promotevideodownloader.Models.WhatsUpStatusModel;
import com.promoteprovider.promotevideodownloader.R;
import com.promoteprovider.promotevideodownloader.Util;
import com.promoteprovider.promotevideodownloader.databinding.WhatsUpItemLayoutBinding;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsUpAdapter extends RecyclerView.Adapter<WhatsUpAdapter.ViewHolder> {
    private ArrayList<WhatsUpStatusModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String saveFilePath = Util.RootDirectoryWhatsUp+"/";

    public WhatsUpAdapter(ArrayList<WhatsUpStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }


        return new ViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.whats_up_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WhatsUpAdapter.ViewHolder holder, int position) {
            WhatsUpStatusModel item = list.get(position);
            if (item.getUri().toString().endsWith(".mp4"))
                holder.binding.playButton.setVisibility(View.VISIBLE);
            else
                holder.binding.playButton.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.binding.download.setOnClickListener(view -> {
            Util.createFileFolder();
            final String path = item.getPath();
            final File file = new File(path);
            File destFile =  new File(saveFilePath);


            try {
                FileUtils.copyFileToDirectory(file,destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(context, "Save to : "+saveFilePath, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        WhatsUpItemLayoutBinding binding;
        public ViewHolder(WhatsUpItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
