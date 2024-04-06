package darshan.prajapati.n01584247.dp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Da11rshanFragment extends Fragment {

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

        addBtn = view.findViewById(R.id.DarAddBtn);
        courseNameET = view.findViewById(R.id.DarCourseNameET);
        courseDescET = view.findViewById(R.id.DarDescriptionET);

            addBtn.setOnClickListener(e ->{
                String courseName = courseNameET.getText().toString().trim();
                String courseDesc = courseDescET.getText().toString().trim();

                if(!courseName.isEmpty() && !courseDesc.isEmpty()){
                    if(isValidCourseName(courseName)){

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
}