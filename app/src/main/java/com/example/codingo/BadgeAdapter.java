package com.example.codingo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingo.Entities.Badge;

import java.util.List;

/**
 * A simple Adapter class for the Badge class. Used in the UserProfileFragment class to store
 * badges a user has obtained.
 */
public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {

    private final String TAG = "com.example.codingo.BadgeAdapter";
    private List<Badge> mBadges;
    private RecyclerViewClickListener mListener;

    public BadgeAdapter(List<Badge> badges, RecyclerViewClickListener listener) {
        mBadges = badges;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class BadgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImage;
        private TextView mName;
        private RecyclerViewClickListener mListener;

        public BadgeViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            mImage = v.findViewById(R.id.iv_badge);
            mName = v.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public BadgeAdapter.BadgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_list_cell, parent, false);
        return new BadgeViewHolder(v, mListener);
    }

    public void onBindViewHolder(BadgeAdapter.BadgeViewHolder holder, int position) {
        Badge badge = mBadges.get(position);
        holder.mImage.setImageResource(badge.getImageId());
        holder.mName.setText(badge.getName());
    }

    @Override
    public int getItemCount() {
        return mBadges.size();
    }

    public void setBadgeList(List<Badge> badges) {
        mBadges.clear();
        mBadges.addAll(badges);
        notifyDataSetChanged();
    }

}
