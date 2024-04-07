// Darshan Prajapati (N01584247)
package darshan.prajapati.n01584247.dp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private final ArrayList<Course> courseModalArrayList;

    // creating a constructor for our variables.
    public CourseAdapter(ArrayList<Course> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList != null ? courseModalArrayList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Course modal = courseModalArrayList.get(position);
        holder.courseNameTV.setText(modal.getCourseName());
        holder.courseDescTV.setText(modal.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    // Interface for the long click listener
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    // Variable for the long click listener
    private static OnItemLongClickListener listener;

    // Method to set the long click listener
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        CourseAdapter.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private final TextView courseNameTV, courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.darTVCourseName);
            courseDescTV = itemView.findViewById(R.id.darTVCourseDescription);

            // Adding a long click listener to the item view
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Get the position of the item clicked
                    int position = getAdapterPosition();

                    // Check if position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        // Trigger the listener with the position of the long clicked item
                        if (listener != null) {
                            listener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
