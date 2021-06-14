package br.ucb.seligaturista

import CourseModal
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class CourseAdapter(courseModalArrayList: ArrayList<CourseModal>, context: Context) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    // creating a variable for array list and context.
    private val courseModalArrayList: ArrayList<CourseModal>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // below line is to inflate our layout.
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.course_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our views of recycler view.
        val modal: CourseModal = courseModalArrayList[position]
        holder.courseNameTV.text = modal.courseName
        holder.courseDescTV.text = modal.courseDescription
    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return courseModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        val courseNameTV: TextView
        val courseDescTV: TextView

        init {

            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription)
        }
    }

    // creating a constructor for our variables.
    init {
        this.courseModalArrayList = courseModalArrayList
        this.context = context
    }
}