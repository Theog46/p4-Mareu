package com.example.maru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.MyViewHolder> {

    List<Meeting> mMeetingList;

    OnMeetingClickListener meetingClickListener;
    MeetingApiService meetingApiService;



    public interface OnMeetingClickListener {
        void onMeetingClick(Meeting meeting);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView meetingName;
        TextView meetingHour;
        TextView meetingEmails;
        TextView date;
        ImageView deleteButton;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            meetingName = itemView.findViewById(R.id.meetingName);
            meetingHour = itemView.findViewById(R.id.meetingName);
            date = itemView.findViewById(R.id.meetingEmails);
            deleteButton = itemView.findViewById(R.id.delete_btn);

        }


    }



    public MyMeetingRecyclerViewAdapter(ArrayList<Meeting> meetingList, OnMeetingClickListener activity) {
        mMeetingList = meetingList;
        meetingClickListener = activity;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meetings, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Meeting meeting = mMeetingList.get(position);
        holder.imageView.setColorFilter(meeting.getColor());
        holder.meetingHour.setText(meeting.getPlace() + " - " + meeting.getHour() + " - " + meeting.getSubject());
        holder.date.setText(meeting.getEmails());
        holder.deleteButton.setOnClickListener(v -> {
            meetingClickListener.onMeetingClick(meeting);
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }



}

