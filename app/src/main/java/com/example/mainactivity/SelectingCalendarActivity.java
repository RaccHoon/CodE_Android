package com.example.mainactivity;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SelectingCalendarActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initViews();

        Button nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Calendar> days = calendarView.getSelectedDates();

                Calendar calendar = days.get(0);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);
                String week = new SimpleDateFormat("EE").format(calendar.getTime());
                String startDate = year + "년 "+ (month+1)  + "월 " + day + "일 " + "(" + week + ")";

                Calendar calendar2 = days.get(days.size()-1);
                final int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                final int month2 = calendar2.get(Calendar.MONTH);
                final int year2 = calendar2.get(Calendar.YEAR);
                String week2 = new SimpleDateFormat("EE").format(calendar2.getTime());
                String endDate = year2 + "년 "+ (month2+1)  + "월 " + day2 + "일 " + "(" + week2 + ")";

                System.out.println(startDate + " "+ endDate);
            }
        });
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        clearSelectionsMenuClick();
        calendarView.setSelectionType(SelectionType.RANGE);
        //((RadioGroup) findViewById(R.id.rg_selection_type)).setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.show_selections:
                List<Calendar> days = calendarView.getSelectedDates();

                String result="";
                for( int i=0; i<days.size(); i++)
                {
                    Calendar calendar = days.get(i);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE").format(calendar.getTime());
                    String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 " + "(" + week + ")";
                    result += (day_full + "\n");
                }
                Toast.makeText(SelectingCalendarActivity.this, result, Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        clearSelectionsMenuClick();
        calendarView.setSelectionType(SelectionType.RANGE);
    }
}