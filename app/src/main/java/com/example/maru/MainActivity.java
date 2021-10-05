package com.example.maru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.example.maru.databinding.ActivityMainBinding;
import com.example.maru.di.DI;
import com.example.maru.events.DeleteMeetingEvent;
import com.example.maru.model.Meeting;
import com.example.maru.service.DummyMeetingApiService;
import com.example.maru.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyMeetingRecyclerViewAdapter.OnMeetingClickListener {

    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter mMyMeetingRecyclerViewAdapter;

    private List<Meeting> mMeetingList = new ArrayList<>();

    private FloatingActionButton button;
    private MeetingApiService mMeetingApiService = DI.getApiService();



    private Toolbar mToolBar;
    private ActivityMainBinding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        mRecyclerView = findViewById(R.id.recycler_view_list);

        initUI();

        button = (FloatingActionButton) findViewById(R.id.meetingAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

        mToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_date:
                dateDialog();
                return true;
            case R.id.filter_reset:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void resetFilter() {
        mMeetingList.clear();
        mMeetingList.addAll(mMeetingApiService.generateMeeting());
        System.out.println("GET DONE");
        mRecyclerView.getAdapter().notifyDataSetChanged();
        System.out.println("NOTIFY DONE");

    }



    private void dateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 8;
        int selectedDay = 30;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mMeetingList.clear();
                mMeetingApiService.generateMeeting();
                mMeetingList.addAll(mMeetingApiService.getMeetingsFilteredByDate(cal.getTime()));
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }



    @Override
    public void onStart() {
        super.onStart();
        initUI();
    }

    private void initUI() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMyMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter((ArrayList<Meeting>) mMeetingList, this);
        mRecyclerView.setAdapter(mMyMeetingRecyclerViewAdapter);
        mMeetingList = mMeetingApiService.getMeetings();


    }






    @Override
    public void onMeetingClick(Meeting meeting) {
        mMeetingApiService.deleteMeeting(meeting);
        initUI();
    }
}