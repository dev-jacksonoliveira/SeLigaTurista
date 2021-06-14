package br.ucb.seligaturista

import CourseModal
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity2 : AppCompatActivity() {
    // creating variables for our ui components.
    private var courseNameEdt: EditText? = null
    private var courseDescEdt: EditText? = null
    private var addBtn: Button? = null
    private var saveBtn: Button? = null
    private var courseRV: RecyclerView? = null

    // variable for our adapter class and array list
    private var adapter: CourseAdapter? = null
    private var courseModalArrayList: ArrayList<CourseModal>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//         initializing our variables.
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescEdt = findViewById(R.id.idEdtCourseDescription)
        addBtn = findViewById(R.id.idBtnAdd)
        saveBtn = findViewById(R.id.idBtnSave)
        courseRV = findViewById(R.id.idRVCourses)

        // calling method to load data
        // from shared prefs.
        loadData()

        // calling method to build
        // recycler view.
        buildRecyclerView()

        // on click listener for adding data to array list.
        addBtn?.setOnClickListener(View.OnClickListener { // below line is use to add data to array list.
            courseModalArrayList!!.add(
                CourseModal(
                    courseNameEdt?.text.toString(),
                    courseDescEdt?.text.toString()
                )
            )
            // notifying adapter when new data added.
            adapter!!.notifyItemInserted(courseModalArrayList!!.size)
        })
        // on click listener for saving data in shared preferences.
        saveBtn?.setOnClickListener(View.OnClickListener { // calling method to save data in shared prefs.
            saveData()
        })
    }

    private fun buildRecyclerView() {
        // initializing our adapter class.
        adapter = CourseAdapter(courseModalArrayList!!, this@MainActivity2)

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(this)
        courseRV?.setHasFixedSize(true)
//        courseRV!!.setHasFixedSize(true)

        // setting layout manager to our recycler view.
        courseRV?.layoutManager = manager
//        courseRV!!.layoutManager = manager

        // setting adapter to our recycler view.
        courseRV?.adapter = adapter
//        courseRV!!.adapter = adapter
    }

    private fun loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)

        // creating a variable for gson.
        val gson = Gson()

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        val json = sharedPreferences.getString("courses", null)

        // below line is to get the type of our array list.
        val type = object : TypeToken<ArrayList<CourseModal?>?>() {}.type

        // in below line we are getting data from gson
        // and saving it to our array list
        courseModalArrayList = gson.fromJson(json, type)

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = ArrayList<CourseModal>()
        }
    }

    private fun saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)

        // creating a variable for editor to
        // store data in shared preferences.
        val editor = sharedPreferences.edit()

        // creating a new variable for gson.
        val gson = Gson()

        // getting data from gson and storing it in a string.
        val json = gson.toJson(courseModalArrayList)

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json)

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply()

        // after saving data we are displaying a toast message.
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }
}