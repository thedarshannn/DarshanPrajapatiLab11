package darshan.prajapati.n01584247.dp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Da11rshanFragment extends Fragment {


    private DatabaseReference myRef;
    private Button addBtn;
    private EditText courseNameET, courseDescET;
    private CourseAdapter adapter;
    private RecyclerView courseRV;
    private ArrayList<Course> courseArrayList;

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
        courseRV = view.findViewById(R.id.DarRV);
        // Initializing the array list
        courseArrayList = new ArrayList<>();
        buildRecyclerView();
        readDataFromDatabase();

            addBtn.setOnClickListener(e ->{
                String courseName = courseNameET.getText().toString().trim();
                String courseDesc = courseDescET.getText().toString().trim();

                if(!courseName.isEmpty() && !courseDesc.isEmpty()){
                    if(isValidCourseName(courseName)){
                        addCourse();
                        addDataToRecycleView();
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

    // Read data from the database
    private void readDataFromDatabase(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Clear the list before adding new courses
                courseArrayList.clear();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    // Getting the values from the database
                    Course course = courseSnapshot.getValue(Course.class);
                    // Add data to recycle view
                    courseArrayList.add(course);
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void buildRecyclerView() {
        adapter = new CourseAdapter(courseArrayList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(adapter);
    }

    private void addDataToRecycleView(){
        courseArrayList.add(new Course(courseNameET.getText().toString(), courseDescET.getText().toString()));
        adapter.notifyItemInserted(courseArrayList.size());

        // Clear the EditText fields after adding
        courseNameET.setText(R.string.clear_field);
        courseDescET.setText(R.string.clear_field);
    }
}