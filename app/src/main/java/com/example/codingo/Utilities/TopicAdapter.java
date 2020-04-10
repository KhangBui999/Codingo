package com.example.codingo.Utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingo.Model.Topics;
import com.example.codingo.R;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private ArrayList<Topics> mTopics;
    private RecyclerViewClickListener mListener;

    public TopicAdapter(ArrayList<Topics> topics, RecyclerViewClickListener listener) {
        mTopics = topics;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTopic, mProgress;
        private ImageView mBadge;
        private RecyclerViewClickListener mListener;


        public TopicViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            mTopic = v.findViewById(R.id.tv_topic);
            mProgress = v.findViewById(R.id.tv_progress);
            mBadge = v.findViewById(R.id.iv_badge);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public TopicAdapter.TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_row, parent, false);
        return new TopicViewHolder(v, mListener);
    }

    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Topics topic = mTopics.get(position);
        holder.mTopic.setText(topic.getName());
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

}
