package com.example.retry.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Ignore;

import com.example.retry.R;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    private int resource;
    private List<Course> courses;
    private LayoutInflater inflater;

    public CourseAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        //constructorul e aici
        this.context = context;
        this.resource = resource;
        this.courses = objects;
        this.inflater = layoutInflater;
    }

    //pt desenare si populare suprascriu getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Course course = courses.get(position);
        TextView tv_id = view.findViewById(R.id.tv_id);
        tv_id.setText(String.valueOf(course.getId()));
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(course.getLabName());
        TextView tv_number = view.findViewById(R.id.tv_number);
        tv_number.setText(String.valueOf(course.getClassNumber()));

        return view;
    }
}
