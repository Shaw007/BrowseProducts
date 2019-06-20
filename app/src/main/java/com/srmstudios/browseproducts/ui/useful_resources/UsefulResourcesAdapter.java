package com.srmstudios.browseproducts.ui.useful_resources;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Repo;
import com.srmstudios.browseproducts.util.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsefulResourcesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Repo> repoList;
    private IUsefulResourcesAdapter iUsefulResourcesAdapter;

    public UsefulResourcesAdapter(List<Repo> repoList, IUsefulResourcesAdapter iUsefulResourcesAdapter) {
        this.repoList = repoList;
        this.iUsefulResourcesAdapter = iUsefulResourcesAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_useful_resources,parent,false);
        UsefulResourcesViewHolder usefulResourcesViewHolder = new UsefulResourcesViewHolder(v);
        return usefulResourcesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Repo repo = repoList.get(position);
        if(holder instanceof UsefulResourcesViewHolder){
            UsefulResourcesViewHolder usefulResourcesViewHolder = (UsefulResourcesViewHolder) holder;
            if(repo.getType().equals(AppConstants.REPO_TYPE_LINK)){
                usefulResourcesViewHolder.txtItemType.setText("Link");
                usefulResourcesViewHolder.txtItemName.setText(repo.getName());
                usefulResourcesViewHolder.txtItemUrl.setText(repo.getUrl());
                usefulResourcesViewHolder.txtItemUrl.setTextColor(ContextCompat.getColor(usefulResourcesViewHolder.txtItemUrl.getContext(),R.color.blue_gradient_color_2));
                usefulResourcesViewHolder.txtItemUrl.setPaintFlags(usefulResourcesViewHolder.txtItemUrl.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
                usefulResourcesViewHolder.txtItemUrl.setVisibility(View.VISIBLE);
            }else if(repo.getType().equals(AppConstants.REPO_TYPE_PDF)){
                usefulResourcesViewHolder.txtItemType.setText("PDF");
                usefulResourcesViewHolder.txtItemName.setText(repo.getName());
                usefulResourcesViewHolder.txtItemUrl.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public void addResource(Repo repo){
        this.repoList.add(repo);
        //notifyItemInserted(repoList.size());
        notifyDataSetChanged();
    }

    class UsefulResourcesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtItemType)
        protected TextView txtItemType;
        @BindView(R.id.txtItemName)
        protected TextView txtItemName;
        @BindView(R.id.txtItemUrl)
        protected TextView txtItemUrl;

        public UsefulResourcesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iUsefulResourcesAdapter.onItemClick(repoList.get(getLayoutPosition()));
                }
            });
        }
    }

    public interface IUsefulResourcesAdapter{
        void onItemClick(Repo repo);
    }
}









