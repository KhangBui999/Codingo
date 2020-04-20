package com.example.codingo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingo.Model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private final String TAG = "com.example.codingo.TopicAdapter";
    private List<Topic> mTopics;
    private RecyclerViewClickListener mListener;

    public TopicAdapter(List<Topic> topics, RecyclerViewClickListener listener) {
        mTopics = topics;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTopic;
        private RecyclerViewClickListener mListener;


        public TopicViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            mTopic = v.findViewById(R.id.tv_topic);
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
        Topic topic = mTopics.get(position);
        holder.mTopic.setText(topic.getName());
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public void setTopicList(List<Topic> list) {
        mTopics.clear();
        mTopics.addAll(list);
        notifyDataSetChanged();
    }

}
