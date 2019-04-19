package com.darcos.julie.mynews.Activities;


import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.darcos.julie.mynews.R;
import com.darcos.julie.mynews.Fragments.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.search_query_term)
    EditText editText;
    @BindView(R.id.activity_search_toolbar)
    Toolbar toolbarSearch;
    private static String beginDate = "01/01/2019";
    private static String endDate = dateToday();
    private static Button beginDateButton;
    private static Button endDateButton;
    private List<String> listChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.configureToolBar();

        //initializes list
        this.listChecked = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.listChecked.add(null);
        }

        beginDateButton = findViewById(R.id.begin_date_button);
        endDateButton = findViewById(R.id.end_date_button);
        beginDateButton.setText(beginDate);
        endDateButton.setText(endDate);

        //disable button (no checkbox checked)
        searchButton.setOnClickListener(this);
        searchButton.setEnabled(false);

    }

    //start Result activity
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SearchActivity.this, ResultSearch.class);
        //information for api shearch
        intent.putExtra("beginDate", beginDate);
        intent.putExtra("endDate", endDate);
        intent.putExtra("query", editText.getText().toString());
        intent.putExtra("newsDesk", newsDesk());
        startActivity(intent);
    }

    //button edit begin date (default: 01/01/2019)
    public void showDatePickerDialogBegin(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "begin");
    }

    //button edit end date (default: date of the day)
    public void showDatePickerDialogEnd(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "end");
    }

    //configure toolbar
    private void configureToolBar() {
        setSupportActionBar(toolbarSearch);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Search Articles");
    }

    //button return
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    //edit lsitChecked and disable or enable button search
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked)
                    this.listChecked.set(0, "arts");
                else
                    this.listChecked.set(0, null);
                break;

            case R.id.checkbox_politics:
                if (checked)
                    this.listChecked.set(1, "politics");
                else
                    this.listChecked.set(1, null);
                break;

            case R.id.checkbox_business:
                if (checked)
                    this.listChecked.set(2, "business");
                else
                    this.listChecked.set(2, null);
                break;
            case R.id.checkbox_sports:
                if (checked)
                    this.listChecked.set(3, "sports");
                else
                    this.listChecked.set(3, null);
                break;
            case R.id.checkbox_entrepreneurs:
                if (checked)
                    this.listChecked.set(4, "entrepreneurs");
                else
                    this.listChecked.set(4, null);
                break;
            case R.id.checkbox_travel:
                if (checked)
                    this.listChecked.set(5, "travel");
                else
                    this.listChecked.set(5, null);
                break;
        }
        if (buttonenable()) {
            searchButton.setEnabled(true);
        } else {
            searchButton.setEnabled(false);
        }

    }

    /**
     * formating newDesk for api Search
     *
     * @return newDesk for api Search
     */
    public String newsDesk() {
        StringBuilder q;
        q = new StringBuilder("news_desk:(");

        for (int i = 0; i < this.listChecked.size(); i++) {
            if (this.listChecked.get(i) == null) {

            } else {
                q.append("\"").append(this.listChecked.get(i)).append("\" ");
            }
        }

        q.append(")");
        return q.toString();
    }

    /**
     * check if there is at least one category selected
     *
     * @return true if button enable or else if disable
     */
    public boolean buttonenable() {
        int j = 0;
        for (int i = 0; i < this.listChecked.size(); i++) {

            if (this.listChecked.get(i) == null) {
                j = j + 1;
            }
        }
        return j != 6;
    }

    /**
     * date of the day
     *
     * @return dd/mm/yyyy
     */
    public static String dateToday() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String y = Integer.toString(year);
        String m = Integer.toString(month + 1);
        String d = Integer.toString(day);

        if (d.length() == 1) {
            d = "0" + d;
        }
        if (m.length() == 1) {
            m = "0" + m;
        }

        return (d + "/" + m + "/" + y);

    }


    /**
     * update begindate and change text of button beginddate
     *
     * @param date chosen by user
     */
    public static void setBeginDate(String date) {
        beginDate = date;
        beginDateButton.setText(beginDate);
    }

    /**
     * update enddate and change text of button endddate
     *
     * @param date chosen by user
     */
    public static void setEndDate(String date) {
        endDate = date;
        endDateButton.setText(endDate);
    }


}
