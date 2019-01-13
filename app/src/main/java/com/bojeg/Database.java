package com.bojeg;

import com.bojeg.model.Job;
import com.bojeg.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    private static final String USER_PATH = "users";

    private static final String JOB_PATH = "jobs";

    private static DatabaseReference sDatabase;

    private static DatabaseReference getDatabase() {
        if (sDatabase == null) {
            sDatabase = FirebaseDatabase.getInstance().getReference();
        }

        return sDatabase;
    }

    public static String saveUser(User user) {
        DatabaseReference reference = getDatabase().child(USER_PATH).push();
        reference.setValue(user);

        return reference.getKey();
    }

    public static void getUser(String id, ValueEventListener listener) {
        getDatabase().child(USER_PATH).child(id).addListenerForSingleValueEvent(listener);
    }

    public static String saveJob(Job job) {
        DatabaseReference reference = getDatabase().child(JOB_PATH).push();
        job.setId(reference.getKey());
        reference.setValue(job);

        return reference.getKey();
    }

    public static void updateJob(Job job) {
        DatabaseReference reference = getDatabase().child(JOB_PATH).child(job.getId());
        reference.setValue(job);
    }

    public static void getJob(String id, ValueEventListener listener) {
        getDatabase().child(JOB_PATH).child(id).addListenerForSingleValueEvent(listener);
    }

    public static void getJobList(ValueEventListener listener) {
        getDatabase().child(JOB_PATH).addValueEventListener(listener);
    }
}
