package com.example.myapplication;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder>{
    @NonNull
    Context context;
    public List<Food_element> food_elements;
    private final LayoutInflater inflater;

    
    public FoodListAdapter(Context context, List<Food_element> food_elements, String language){
        this.context = context;
        this.food_elements=food_elements;
        this.inflater = LayoutInflater.from(context);
        setApplicationLocale(language);
    }
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.test, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        String Name = food_elements.get(position).getName();
        int Kal = food_elements.get(position).getKal();
        holder.nameView.setText(Name);
        holder.kaloriesView.setText(Double.toString(Kal)+context.getString(R.string.kkal_na_sto_g));
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView kaloriesView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            kaloriesView = view.findViewById(R.id.kalories);

            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int x = getAdapterPosition();
                            Log.d("RRR",Integer.toString(x));
                            double [] b_g_u_kal_water = new double[] {food_elements.get(x).getB(), food_elements.get(x).getG(), food_elements.get(x).getU(), food_elements.get(x).getKal()};
                            Intent intent = new Intent(context, Food_selected.class);
                            intent.putExtra("1", b_g_u_kal_water);
                            intent.putExtra("name", food_elements.get(x).getName());
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
            );
        }
    }
    @Override
    public int getItemCount() {
        return food_elements.size();
    }
    public void setApplicationLocale(String locale) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
}
