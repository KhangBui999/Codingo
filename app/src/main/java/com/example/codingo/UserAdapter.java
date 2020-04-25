/*
 * INFS3634 Group Assignment 2020 T1 - Team 31
 *
 * This is an Android mobile application that showcases the use of functional Android building blocks
 * and the implementation of other features such as Google Firebase and API calls. Submitted as part of
 * a group assignment for the course, INFS3634.
 *
 * Authors:
 * Shara Bakal, Khang Bui, Laurence Truong & Brian Vu
 *
 */

package com.example.codingo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codingo.Entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final String TAG = "com.example.codingo.UserAdapter";
    private List<User> mUsers;
    private RecyclerViewClickListener mListener;

    public UserAdapter (List<User> users, RecyclerViewClickListener listener) {
        mUsers = users;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPos, mName, mPoints;
        private ImageView mImage;
        private RecyclerViewClickListener mListener;

        public UserViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            mPos = v.findViewById(R.id.tv_pos);
            mName = v.findViewById(R.id.tv_name);
            mPoints = v.findViewById(R.id.tv_points);
            mImage = v.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row, parent, false);
        return new UserViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.mPos.setText(Integer.toString(position+1));
        holder.mName.setText(user.getName());
        holder.mPoints.setText(Integer.toString(user.getPoints()));
        Glide.with(holder.itemView.getContext())
                .load(user.getProfilePicUrl())
                .placeholder(R.mipmap.ic_logo)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void updateUserList(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

}
