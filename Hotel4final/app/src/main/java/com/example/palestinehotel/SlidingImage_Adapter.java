package com.example.palestinehotel;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

public class SlidingImage_Adapter extends PagerAdapter{

    private String[] urls;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, String[] urls) {
        this.context = context;
        this.urls = urls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimage_layout, view, false);
// يوجد داخل ال slidingimage_layout --> imageView
        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);

//  class Glide context=activity to shown it in.
//        load= import from url the image.
//        into= shown the image in the specific imageView.
        Glide.with(context).load(urls[position]).into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}