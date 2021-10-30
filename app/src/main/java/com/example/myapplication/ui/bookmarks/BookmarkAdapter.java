package com.example.myapplication.ui.bookmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.bookmark.Bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private LiveData<List<Bookmark>> data;
    private Context context;

    public BookmarkAdapter(Context context, LiveData<List<Bookmark>> data) {
        this.context=context;
        this.data = data;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bookmark_item, parent, false);

        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.carparkName.setText(Objects.requireNonNull(data.getValue()).get(position).nickname);
    }

    @Override
    public int getItemCount() {
        if (data.getValue() == null){
            return 0;
        }
        return data.getValue().size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private TextView carparkName;
        private Button removeBtn;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            carparkName= itemView.findViewById(R.id.carpark_name);
            removeBtn= itemView.findViewById(R.id.fav_remove_btn);

        }
    }
}