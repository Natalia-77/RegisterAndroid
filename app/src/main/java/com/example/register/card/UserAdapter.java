package com.example.register.card;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.register.ProfileActivity;
import com.example.register.RegisterActivity;
import com.example.register.SuccessActivity;
import com.example.register.app.HomeApplication;
import com.example.register.constants.Url;
import com.example.register.network.account.dto.UserDto;
import com.example.register.R;

import java.util.List;

import retrofit2.Callback;

// Роль адаптера полягає в перетворенні об’єкта в позиції в елемент рядка списку ,
// який потрібно вставити.
//Однак для RecyclerView-адаптера потрібне існування об’єкта UserCardViewHolder, який описує та
// надає доступ
// до всіх представлень у кожному рядку елементів
public class UserAdapter extends RecyclerView.Adapter<UserCardViewHolder> {

    // зміннa-член для списку контактів і передаємо список через наш конструктор
    private List<UserDto> listUsers;
    public static final String NAME = "n";

    public UserAdapter(List<UserDto> listUsers) {

        this.listUsers = listUsers;

    }

    @NonNull
    @Override
    //розширити макет елемента та створити holder
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        //отримала екземпляр LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(context);

        // завантажую файл-макет users_card
        View contactView = inflater.inflate(R.layout.users_card, parent, false);

        // повертаю новий instance UserCardViewHolder
        UserCardViewHolder viewHolder = new UserCardViewHolder(contactView);

        return viewHolder;
    }

    @Override
    //встановити атрибути перегляду на основі даних
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {

        if (listUsers != null && position < listUsers.size()) {
            UserDto user = listUsers.get(position);

//            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("Id"+"-->"+ user.getId());
//
//                }
//            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //this.selected(user);
                    Intent intent = new Intent(HomeApplication.getAppContext(), ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(NAME, user.getName());
                    HomeApplication.getAppContext().startActivity(intent);
                    System.out.println("+++++");
                }
            });
            holder.userId.setText(Integer.toString(user.getId()));
            holder.userName.setText(user.getName());
            holder.userEmail.setText(user.getEmail());
            String url = Url.urls + user.getPhoto();
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(400, 300))
                    .into(holder.userImage);
        }

    }

    //визначає кількість елементів,яку потрібно перебрати.
    @Override
    public int getItemCount() {
        return listUsers.size();
    }


}
