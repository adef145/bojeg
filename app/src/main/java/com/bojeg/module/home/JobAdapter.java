package com.bojeg.module.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bojeg.R;
import com.bojeg.model.Job;
import com.bojeg.model.User;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {

    @NonNull
    private List<Job> jobs = new ArrayList<>();

    private User user;

    public JobAdapter(User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new JobViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_job, viewGroup, false), user);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder viewHolder, int i) {
        Job job = jobs.get(i);
        viewHolder.onBind(job);
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setJobs(@NonNull List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }

    public void clear() {
        this.jobs.clear();
        notifyDataSetChanged();
    }
}
