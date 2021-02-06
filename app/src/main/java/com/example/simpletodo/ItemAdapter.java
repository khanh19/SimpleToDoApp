package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface OnLongClickedListener{
        void onLongClicked(int position);

    }
    List<String> list;
    OnLongClickedListener onLong;
    public ItemAdapter(List<String> itemList, OnLongClickedListener onLong){
        this.list = itemList;
        this.onLong = onLong;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            text.setText(item);
            text.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    //Notify where the adapter was pressed
                    onLong.onLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View appView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(appView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
