package com.example.maru;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maru.databinding.ActivityAddMeetingBinding;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;
import com.example.maru.service.MeetingGenerator;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.getInstance;

public class AddMeetingActivity<Guest> extends AppCompatActivity implements View.OnClickListener{

    ActivityAddMeetingBinding binding;
    private MeetingApiService mMeetingApiService = DI.getApiService();
    ImageView imageView;

    Button dateButton;
    Date meetingDate;
    Date mMeetingTime;
    String meetingTime;
    Button timeButton;
    int hour, minute;

    List<String> mGuest = Arrays.asList("maxime@lamzone.com", "alex@lamzone.com", "paul@lamzone.com", "viviane@lamzone.com", "amandine@lamzone.com", "luc@lamzone.com", "gérard@lamzone.com", "mathieu@lamzone.com", "alphonse@lamzone.com");
    MultiAutoCompleteTextView multiAutoCompleteTextView;




    final int color = MeetingGenerator.generateRandomColor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        initUI();

        dateButton = findViewById(R.id.datePickerButton);
        timeButton = findViewById(R.id.timeButton);
        Calendar calender = getInstance(TimeZone.getTimeZone("UTC"));
        calender.getTime();




        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Choisissez une date");

        final MaterialDatePicker materialDatePicker = builder.build();



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                dateButton.setText(materialDatePicker.getHeaderText());
                Calendar calendar = getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                meetingDate = calendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate  = format.format(calendar.getTime());

                System.out.println("DATE = " + formattedDate);
            }
        });

        imageView = findViewById(R.id.addMeetingColor);
        imageView.setColorFilter(color);
        imageView.setOnClickListener(x -> imageView.setColorFilter(MeetingGenerator.generateRandomColor()));


        multiAutoCompleteTextView = findViewById(R.id.emailsInput);
        ArrayAdapter<String> randomArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mGuest);
        multiAutoCompleteTextView.setAdapter(randomArrayAdapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }



    private void initUI() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        setButton();
        getSupportActionBar().setTitle("New Meeting");
    }

    private void setButton() {
        binding.submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.submitButton) {
            try {
                onSubmit();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener; onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute, true));

                Calendar calendar = getInstance();
                calendar.set(HOUR_OF_DAY, selectedHour);
                calendar.set(MINUTE, selectedMinute);
                mMeetingTime = calendar.getTime();

                Date date = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String DateDebut = dateFormat.format(date);

                System.out.println("HEURE = " + DateDebut);

                meetingTime = DateDebut;

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Choisissez une heure");
        timePickerDialog.show();
    }





    private void onSubmit() throws ParseException {


        String subject = binding.subjectInput.getText().toString();
        String place = binding.placeInput.getText().toString();
        String emails = binding.emailsInput.getText().toString();

        if (subject.isEmpty()) {
            binding.subjectInput.setError("Merci d'indiquer le sujet de la réunion");
            return;
        }
        if (place.isEmpty()) {
            binding.placeInput.setError("Merci d'indiquer le lieu de la réunion");
            return;
        }
        if (emails.isEmpty()) {
            binding.emailsInput.setError("Merci d'indiquer les participants de la réunion");
            return;
        }
        mMeetingApiService.createMeeting(new Meeting(+ 1,
                MeetingGenerator.getColor(),
                subject,
                place,
                meetingDate,
                meetingTime,
                emails));
        Toast.makeText(this, "Réunion ajoutée !", Toast.LENGTH_SHORT).show();
        finish();
        }






}
