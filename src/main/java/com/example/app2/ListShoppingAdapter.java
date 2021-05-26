package com.example.app2;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Document;

public class ListShoppingAdapter extends FirestoreRecyclerAdapter<Shopping , ListShoppingAdapter.ShoppingViewHolder > {

    private OnListItemClick onListItemClick;

    public ListShoppingAdapter(@NonNull FirestoreRecyclerOptions<Shopping> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListShoppingAdapter.ShoppingViewHolder holder, int position, @NonNull  Shopping model) {
        holder.the_shooping_single.setText(model.getShopNeed());
        holder.the_username_single.setText(model.getFirstName());
        Log.d("username", "onBindViewHolder: adapter " + model.getFirstName());

        holder.the_count_item_single.setText(String.valueOf(model.getCount()));
        String timeAgo;
        if (model.getTimeAdded() != null) {
            timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                    model.getTimeAdded().getSeconds() * 1000);
        }else{
            timeAgo = "IDK";
        }

        Log.d("timeago", "onBindViewHolder: " +timeAgo);
        holder.the_time_single.setText(timeAgo);

    }

    @NonNull
    @Override
    public  ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_single, parent, false);
        return new ShoppingViewHolder(view);
    }


    public class ShoppingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView the_username_single;
        private TextView the_time_single;
        private TextView the_shooping_single;
        private TextView the_count_item_single;



        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);

            the_shooping_single = itemView.findViewById(R.id.single_need_shooping);
            the_time_single = itemView.findViewById(R.id.single_time);
            the_username_single = itemView.findViewById(R.id.single_username);
            the_count_item_single = itemView.findViewById(R.id.single_count);



            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick((int) getItemId());
        }
    }

    public interface OnListItemClick{
        void onItemClick(int position);
    }

}

