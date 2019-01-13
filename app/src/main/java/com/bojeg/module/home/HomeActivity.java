package com.bojeg.module.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bojeg.Database;
import com.bojeg.PreferencesManager;
import com.bojeg.R;
import com.bojeg.model.Job;
import com.bojeg.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rv_job)
    RecyclerView rvJob;

    @BindView(R.id.btn_add)
    Button btnAdd;

    private User user;

    private JobAdapter adapter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        unbinder = ButterKnife.bind(this);

        setUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_add)
    public void add() {
        Job job = new Job(user, null);
        Database.saveJob(job);
    }

    private void setUser() {
        String userId = PreferencesManager.with(this).getUserId();
        Database.getUser(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                adapter = new JobAdapter(user);
                rvJob.setAdapter(adapter);
                rvJob.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

                if (user.isCustomer()) {
                    btnAdd.setVisibility(View.VISIBLE);
                }
                setJob();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setJob() {
        Database.getJobList(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Job> myJobs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    if (job == null) {
                        continue;
                    }

                    if (user.isDriver()) {
                        if (job.getDriver() == null || job.getDriver().getUserName().equals(user.getUserName())) {
                            myJobs.add(job);
                        }
                    }
                    else if (user.isCustomer() && job.getCustomer().getUserName().equals(user.getUserName())) {
                        myJobs.add(job);
                    }
                }

                adapter.setJobs(myJobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
