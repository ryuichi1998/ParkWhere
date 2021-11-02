package com.example.myapplication.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Bookmark;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private LiveData<List<Bookmark>> data;
    private Context context;
    private OnItemClickedListener onItemClickedListener;

    public BookmarkAdapter(Context context, LiveData<List<Bookmark>> data, OnItemClickedListener onItemClickedListener) {
        this.context=context;
        this.data = data;
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bookmark_item, parent, false);

        return new BookmarkViewHolder(view, onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.carparkName.setText(Objects.requireNonNull(data.getValue()).get(position).getNickname());
        holder.avail_lots.setText(Objects.requireNonNull(data.getValue()).get(position).getAvail_lots());
    }

    @Override
    public int getItemCount() {
        if (data.getValue() == null){
            return 0;
        }
        return data.getValue().size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView carparkName;
        private TextView avail_lots;
        OnItemClickedListener itemClickedListener;

        public BookmarkViewHolder(@NonNull View itemView, OnItemClickedListener itemClickedListener) {
            super(itemView);
            carparkName= itemView.findViewById(R.id.carpark_name);
            avail_lots = itemView.findViewById(R.id.bookmark_avail_slots);

            this.itemClickedListener = itemClickedListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                itemClickedListener.itemClicked(getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClickedListener{
        void itemClicked(int position) throws JSONException;
    }
}