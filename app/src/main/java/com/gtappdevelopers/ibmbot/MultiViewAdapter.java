package com.gtappdevelopers.ibmbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MultiViewAdapter extends RecyclerView.Adapter  {
// 1 for bot, 2 for user..
    private ArrayList<MessageModel> dataSet;
    Context mContext;
    int total_types;

    public MultiViewAdapter(ArrayList<MessageModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
        total_types = dataSet.size();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_layout, parent, false);
                return new BotTypeViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
                return new UserTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {

                case 1:
                    ((BotTypeViewHolder) holder).bot_msg.setText(object.getMessage());

                    break;
                case 2:
                    ((UserTypeViewHolder) holder).user_msg.setText(object.getMessage());

                    break;


            }
            }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type) {
            case 1:
                return 1;
            case 2:
                return 2;

            default:
                return -1;
        }
    }

    public static class BotTypeViewHolder extends RecyclerView.ViewHolder {

        TextView bot_msg;
        CardView cardView;

        public BotTypeViewHolder(View itemView) {
            super(itemView);

            this.bot_msg = (TextView) itemView.findViewById(R.id.bot_msg);
            this.cardView = (CardView) itemView.findViewById(R.id.card);
        }
    }


    public static class UserTypeViewHolder extends RecyclerView.ViewHolder {

        TextView user_msg;
        CardView cardView;

        public UserTypeViewHolder(View itemView) {
            super(itemView);

            this.user_msg = (TextView) itemView.findViewById(R.id.user_msg);
            this.cardView = (CardView) itemView.findViewById(R.id.user_card);
        }
    }

}
