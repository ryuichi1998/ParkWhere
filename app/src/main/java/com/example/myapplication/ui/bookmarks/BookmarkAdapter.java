package com.example.myapplication.ui.bookmarks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.bookmark.Bookmark;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private List<Bookmark> data;
    private BookmarksFragment context;
    private HandleBookmarkClick clickListener;

    public BookmarkAdapter(BookmarksFragment context, HandleBookmarkClick clickListener) {

        this.context=context;
        this.clickListener=clickListener;

    }


    public void setBookmarkList(List<Bookmark> bookmarks){
        this.data = bookmarks;
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
        Bookmark currentBm=data.get(position);
        holder.carparkName.setText(data.get(position).nickname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(currentBm);
            }
        });
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeBookmark(currentBm);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(data == null ||data.size()==0){
            return 0;
        }else{
            return data.size();
        }

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

    public interface HandleBookmarkClick{
        void removeBookmark(Bookmark bookmark);
        void itemClick(Bookmark bookmark);

    }
}
