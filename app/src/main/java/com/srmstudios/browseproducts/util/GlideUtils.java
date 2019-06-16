package com.srmstudios.browseproducts.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.srmstudios.browseproducts.R;

public class GlideUtils {

    public static void loadImageProductDetail(Context context, ImageView imageView,String imageUrl){
        try {
            Glide.with(context)
                    //.load(Utils.getUriFromFile(context,imageUrl))
                    .load(Utils.getUriFromFile(context,imageUrl))
                    .placeholder(R.drawable.image_placeholder_list)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void loadImageListItems(Context context, ImageView imageView,String imageUrl){
        try {
            Glide.with(context)
                    //.load(Utils.getUriFromFile(context,imageUrl))
                    .load(Utils.getUriFromFile(context,imageUrl))
                    .placeholder(R.drawable.image_placeholder_list)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
