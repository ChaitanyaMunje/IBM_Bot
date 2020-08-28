package com.gtappdevelopers.ibmbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private ArrayList<MessageModel>msgmodel;

    public MsgAdapter(ArrayList<MessageModel> msgmodel) {
        this.msgmodel = msgmodel;
    }

    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        MessageModel msg=msgmodel.get(position);
        holder.msg_txt.setText(msg.getMessage());


    }

    @Override
    public int getItemCount() {
        return msgmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        private TextView msg_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.card);
            msg_txt=itemView.findViewById(R.id.msg_txt);

        }
    }
}
