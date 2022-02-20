package com.example.palestinehotel;
// RoomNum  totalPrice   Standard ocubancy

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;


public class CaptionedImagesAdapter
        extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<String> captions2;
    private String [] captions;
    private int[] imageIds;
    private ImageView imageViewi;
    Context context;
    private int RoomNumber;
    ArrayList <String> url ;


    public CaptionedImagesAdapter(Context context, ArrayList<String> url,ArrayList<String> captions2){
        this.context = context;
        this.url = url;
        this.captions2=captions2;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,
                parent,
                false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.image);
        imageViewi=cardView.findViewById(R.id.image);
//        set images in recycler view
            Glide.with(cardView.getContext())
                    .load(url.get(position))
                    .into(imageView);
//      set the captions in recycler view
        TextView txt = cardView.findViewById(R.id.txtName);
        txt.setText(captions2.get(position)+"");
        String RoomNumberString=url.get(position).toString();
        String RoomNumberSplit [] = RoomNumberString.split("/");
        String RoomNumberSplit2 []=RoomNumberSplit[RoomNumberSplit.length-1].split(".jpg");
        RoomNumber = Integer.parseInt(RoomNumberSplit2[0]);

//        Log.d("ErrorRoom",RoomNumber+" ");
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                int pos=201;
                if (context.toString().contains("Admin")) {
                    Intent intent = new Intent(context, AdminDetailSpecificRoom.class);
//
                    intent.putExtra("RoomNumberAdmin",RoomNumber+"");
                    context.startActivity(intent);

                }else {
                    Intent intent = new Intent(context, DetailSpecificRoom.class);

                    intent.putExtra("RoomNumber",RoomNumber+"");
                    context.startActivity(intent);
                }



            }
        });
    }




    @Override
    public int getItemCount() {
        return url.size();
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;

        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;

        }


        @Override
        public void onClick(View v) {

        }
    }



}
