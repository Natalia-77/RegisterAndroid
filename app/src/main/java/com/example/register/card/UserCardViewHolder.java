package com.example.register.card;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import lombok.NonNull;

public class UserCardViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public ImageView userImage;
    public TextView userName;
    public TextView userEmail;
    public TextView userId;
    public Button btnDelete, btnEdit;

    public UserCardViewHolder(@NonNull View itemView) {

        super(itemView);
        this.view = itemView;
        userImage = itemView.findViewById(R.id.avatarImageView);
        userName = itemView.findViewById(R.id.nameTextView);
        userEmail = itemView.findViewById(R.id.emailTextView);
        userId = itemView.findViewById(R.id.userId);
        btnDelete = itemView.findViewById(R.id.button_delete);
        btnEdit = itemView.findViewById(R.id.button_edit);
    }

    public View getView() {
        return view;
    }

}
