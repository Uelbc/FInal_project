package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder>{
    @NonNull
    Context context;
    List<String> Names;
    List<Double> Kalories;
    private final LayoutInflater inflater;

    public FoodListAdapter(Context context, List<String> Names, List<Double> Kalories){
        this.context = context;
        this.Names = Names;
        this.Kalories = Kalories;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.test, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        String Name = Names.get(position);
        Double Kal = Kalories.get(position);
        holder.nameView.setText(Name);
        holder.kaloriesView.setText(Double.toString(Kal)+" ккал на 100г");
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, kaloriesView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            kaloriesView = view.findViewById(R.id.kalories);
        }
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }
}
