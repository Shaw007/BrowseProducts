package com.srmstudios.browseproducts.ui.customer.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeItem> homeItems;
    private IHomeAdapter iHomeAdapter;

    public HomeAdapter(List<HomeItem> homeItems, IHomeAdapter iHomeAdapter) {
        this.homeItems = homeItems;
        this.iHomeAdapter = iHomeAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home,parent,false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(v);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeItem homeItem = homeItems.get(position);
        if(holder instanceof HomeViewHolder){
            HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
            homeViewHolder.imgHomeItem.setImageResource(homeItem.getImageDrawable());
            homeViewHolder.txtHomeItemName.setText(homeItem.getName());
        }
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imgHomeItem)
        protected ImageView imgHomeItem;
        @BindView(R.id.txtHomeItemName)
        protected TextView txtHomeItemName;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iHomeAdapter.onItemClick(homeItems.get(getLayoutPosition()));
        }
    }

    public interface IHomeAdapter{
        void onItemClick(HomeItem homeItem);
    }
}














