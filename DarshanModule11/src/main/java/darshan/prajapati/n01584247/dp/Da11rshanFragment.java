package darshan.prajapati.n01584247.dp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Da11rshanFragment extends Fragment {


    private DatabaseReference myRef;
    private Button addBtn;
    private EditText courseNameET, courseDescET;

    public Da11rshanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da11rshan, container, false);

        // Getting the reference of course node
        myRef = FirebaseDatabase.getInstance().getReference("Courses");

        addBtn = view.findViewById(R.id.DarAddBtn);
        courseNameET = view.findViewById(R.id.DarCourseNameET);
        courseDescET = view.findViewById(R.id.DarDescriptionET);

            addBtn.setOnClickListener(e ->{
                String courseName = courseNameET.getText().toString().trim();
                String courseDesc = courseDescET.getText().toString().trim();

                if(!courseName.isEmpty() && !courseDesc.isEmpty()){
                    if(isValidCourseName(courseName)){
                        addCourse();
                    }else {
                        courseNameET.setError(getString(R.string.err_invalid_course));
                    }
                }else {
                    if (courseName.isEmpty()){
                        courseNameET.setError(getString(R.string.err_null_course_name));
                    }
                    if (courseDesc.isEmpty()) {
                        courseDescET.setError(getString(R.string.err_null_course_desc));
                    }
                }
            });

        // Inflate the layout for this fragment
        return view;
    }


    private boolean isValidCourseName(String input) {
        // Compile regular expression
        Pattern pattern = Pattern.compile("^[A-Z]{4}-\\d{3,4}$");
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }

    // commit msg : Added the addCourse method to save the course data to the Firebase Realtime Database
    // short one liner : Added the addCourse method to save the course data to the Firebase Realtime Database
    // much short one liner : Added addCourse method to save course data to Firebase Realtime Database
    // much short one liner : Added addCourse method to save course data to Firebase
    private void addCourse(){

        // Getting values to save
        String courseName = courseNameET.getText().toString().trim();
        String courseDesc = courseDescET.getText().toString().trim();

        // Checking if the values are provided
        if (!TextUtils.isEmpty(courseName) && !TextUtils.isEmpty(courseDesc)){

            // Getting a unique id using push().getKey() method
            // It will create a unique id and we will use it as the Primary Key for the Course
            String courseID = myRef.push().getKey();

            // Creating the course obj
            Course course = new Course(courseID, courseName, courseDesc);

            // Saving the course
            myRef.child(courseName).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                     courseNameET.setText("");
                     courseDescET.setText("");
                     Toast.makeText(getActivity(), getString(R.string.addCourse_success_msg), Toast.LENGTH_LONG).show();
                }
            });

        }else {
            // If the value is not given, displaying a toast
            Toast.makeText(getActivity(), getString(R.string.addCourse_failure_msg), Toast.LENGTH_LONG).show();
        }
    }
}