package com.example.project3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.Item;
import com.example.project3.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> list;

    /**
     * a constructor for MyAdpter class that will hold a class' context and Arraylist
     *
     * @param context the context of a class
     * @param list an ArryList that will be passed through
     */
    public MyAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return MyViewHolder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    /**
     * This class will be used to hold values retrieved from the database and will also have an onClickListener
     * to open a new class
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Item user = list.get(position);
        holder.name.setText(user.getName());
        holder.brand.setText(user.getBrand());
        holder.quantity.setText(user.getQuantity());

        // will the the data for an item as a string separated by ',' to be split later
        final String a = user.getName() + "," + user.getBrand() + "," + user.getQuantity() + "," + user.getPrice() + "," + user.getColor() + "," + user.getCondition()
                + "," + user.getComments();
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                // creates an extra intent that will be gotten from the Details class to get the data above
                intent.putExtra("key", a);
                context.startActivity(intent);

            }
        });

    }


    /**
     * will get an item count
     *
     * @return the size of the ArrayList list
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Initialize values
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, brand, quantity, price, color, condition, comments;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            brand = itemView.findViewById(R.id.brand);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }

}
