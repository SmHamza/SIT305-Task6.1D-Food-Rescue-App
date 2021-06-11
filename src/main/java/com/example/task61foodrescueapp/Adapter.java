package com.example.task61foodrescueapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task61foodrescueapp.model.Food;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.task61foodrescueapp.Adapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<Food> foodList;

    public Adapter(Context context, Activity activity, List<Food> foodList) {
        this.context = context;
        this.activity = activity;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public com.example.task61foodrescueapp.Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.task61foodrescueapp.Adapter.MyViewHolder holder, int position) {

        holder.image.setImageBitmap(foodList.get(position).getImage());
        holder.title.setText(foodList.get(position).getFood_title());
        holder.description.setText(foodList.get(position).getFood_description());
        holder.date.setText(foodList.get(position).getDate_added());
        holder.time.setText(foodList.get(position).getPick_up_times());
        holder.quantity.setText(foodList.get(position).getQuantity());
        holder.location.setText(foodList.get(position).getLocation());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = foodList.get(position).getFood_title();
                String description = foodList.get(position).getFood_description();
                String quantity = foodList.get(position).getQuantity();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,title);
                intent.putExtra(Intent.EXTRA_TEXT,description);

                Intent shareIntent = Intent.createChooser(intent,"Share using");
                activity.startActivity(shareIntent);

                //activity.startActivityForResult(intent,1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,date,time,quantity,location;
        ImageView image;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            quantity = itemView.findViewById(R.id.quantity);
            location = itemView.findViewById(R.id.location);
            layout = itemView.findViewById(R.id.note_layout);
        }
    }
}
