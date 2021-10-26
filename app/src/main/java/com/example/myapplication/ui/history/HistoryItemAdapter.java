package com.example.myapplication.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.history.History;

import java.util.ArrayList;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>{
    ArrayList<History> history_list;
    Context context;

    public HistoryItemAdapter(ArrayList<History> history_data, Context context) {
        this.history_list = history_data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carpark_name.setText(history_list.get(position).getCarpark_name());
        //TODO: Implement others
    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView carpark_name;
        TextView date;
        TextView start_time;
        TextView duration;
        public ViewHolder(@NonNull View view){
            super(view);
            carpark_name = view.findViewById(R.id.history_carpark_name);
            date = view.findViewById(R.id.history_date);
            start_time = view.findViewById(R.id.history_start_time);
            duration = view.findViewById(R.id.history_duration);
        }
    }
}
