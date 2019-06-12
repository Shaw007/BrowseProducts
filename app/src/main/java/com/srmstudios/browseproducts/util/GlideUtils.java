package com.srmstudios.browseproducts.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtils {

    public static void loadImage(Context context, ImageView imageView,String imageUrl){
        Glide.with(context)
                .load(Utils.getUriFromFile(context,imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
