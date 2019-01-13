package com.bojeg.module.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bojeg.Database;
import com.bojeg.R;
import com.bojeg.model.Job;
import com.bojeg.model.User;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;

    @BindView(R.id.tv_lat_lon)
    TextView tvLatLon;

    @BindView(R.id.tv_start)
    TextView tvStart;

    @BindView(R.id.tv_end)
    TextView tvEnd;

    @BindView(R.id.btn_start_end)
    Button btnStartEnd;

    @BindView(R.id.btn_send_location)
    Button btnSendLocation;

    private Job job;

    private User user;

    public JobViewHolder(@NonNull View itemView, User user) {
        super(itemView);
        this.user = user;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Job job) {
        this.job = job;
        User customer = job.getCustomer();
        User driver = job.getDriver();

        itemView.setTag(job);

        tvUserName.setText(customer.getUserName());

        if (driver == null) {
            tvDriverName.setText("Waiting");
        }
        else {
            tvDriverName.setText(driver.getUserName());
        }

        tvLatLon.setText("Lat: " + job.getLat() + ", Lon: " + job.getLon());

        if (!job.isStarted()) {
            tvStart.setVisibility(View.GONE);
        }
        else {
            tvStart.setVisibility(View.VISIBLE);
            tvStart.setText(job.getStartTime().toString());
        }

        if (!job.isEnded()) {
            tvEnd.setVisibility(View.GONE);
        }
        else {
            tvEnd.setVisibility(View.VISIBLE);
            tvEnd.setText(job.getEndTime().toString());
        }

        if (!job.isStarted() || job.isEnded() || job.getCustomer().getUserName().equals(user.getUserName())) {
            btnSendLocation.setVisibility(View.GONE);
        }
        else {
            btnSendLocation.setVisibility(View.VISIBLE);
        }

        if (job.isEnded() || job.getCustomer().getUserName().equals(user.getUserName())) {
            btnStartEnd.setVisibility(View.GONE);
        }
        else {
            btnStartEnd.setVisibility(View.VISIBLE);
            if (!job.isStarted()) {
                btnStartEnd.setText("Start");
            }
            else if (!job.isEnded()) {
                btnStartEnd.setText("End");
            }
        }
    }

    @OnClick(R.id.btn_start_end)
    public void startEnd() {
        if (!job.isStarted()) {
            job.setDriver(user);
            job.setStartTime(new Date());
        }
        else if (!job.isEnded()) {
            job.setEndTime(new Date());
        }
        Database.updateJob(job);
    }

    @OnClick(R.id.btn_send_location)
    public void sendLocation() {
        job.setLat(Math.random());
        job.setLon(Math.random());
        Database.updateJob(job);
    }
}
