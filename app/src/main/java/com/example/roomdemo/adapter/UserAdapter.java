package com.example.roomdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.R;
import com.example.roomdemo.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<User> users;
    private Context context;

    private OnClickItemListener onClickItemListener;

    public UserAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onClickItemListener){
                    onClickItemListener.onClickItem((User) view.getTag());
                }
            }
        });
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        holder.tvId.setText("id = " +  user.userId);
        holder.tvName.setText(user.name);
        holder.tvAge.setText("年龄 = " + user.age);
        holder.itemView.setTag(user);

    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public interface OnClickItemListener{
        void onClickItem(User user);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }
}
