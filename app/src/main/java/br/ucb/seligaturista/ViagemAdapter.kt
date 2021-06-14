package br.ucb.seligaturista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class ViagemAdapter(viagemModalArrayList: ArrayList<ViagemModal>, context: Context) :
    RecyclerView.Adapter<ViagemAdapter.ViewHolder>() {
    // criando uma variável para a lista de array
    private val viagemModalArrayList: ArrayList<ViagemModal> = viagemModalArrayList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_rv_item, parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // definir dados para nossas visualizações do recyclerView
        val modal: ViagemModal = viagemModalArrayList[position]
        holder.courseNameTV.text = modal.courseName
        holder.courseDescTV.text = modal.courseDescription
    }

    override fun getItemCount(): Int {
        // retornar o tamanho da lista
        return viagemModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourseName)
        val courseDescTV: TextView = itemView.findViewById(R.id.idTVCourseDescription)

        init { }
    }

}