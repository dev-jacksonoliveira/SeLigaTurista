package br.ucb.seligaturista

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

    private var nomeDestino: EditText? = null
    private var descricao: EditText? = null
    private var addBtn: Button? = null
    private var saveBtn: Button? = null
    private var itemRecyclerView: RecyclerView? = null
    private var adapter: ViagemAdapter? = null
    private var viagemModalArrayList: ArrayList<ViagemModal>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        nomeDestino = findViewById(R.id.editTextNomeDestino)
        descricao = findViewById(R.id.editTextDescricao)
        addBtn = findViewById(R.id.idBtnAdd)
        saveBtn = findViewById(R.id.idBtnSave)
        itemRecyclerView = findViewById(R.id.recyclerViewViagem)

        // carregar dados
        carregarDados()

        // método que cria a recyclerView
        criarRecyclerView()

        // adicionar dados à lista de array.
        addBtn?.setOnClickListener(View.OnClickListener {
            viagemModalArrayList!!.add(
                ViagemModal(
                    nomeDestino?.text.toString(),
                    descricao?.text.toString()
                )
            )
            // notificação de quando os dados são inseridos
            adapter!!.notifyItemInserted(viagemModalArrayList!!.size)
        })

        saveBtn?.setOnClickListener(View.OnClickListener { // calling method to save data in shared prefs.
            salvar()
        })
    }

    // criar recyclerView
    private fun criarRecyclerView() {
        adapter = ViagemAdapter(viagemModalArrayList!!, this@MainActivity2)

        val manager = LinearLayoutManager(this)
        itemRecyclerView?.setHasFixedSize(true)
        itemRecyclerView?.layoutManager = manager
        itemRecyclerView?.adapter = adapter

    }

    private fun carregarDados() {

        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
//        val json = sharedPreferences.getString("courses", null)
        val json = sharedPreferences.getString("viagem", null)
        val type = object : TypeToken<ArrayList<ViagemModal?>?>() {}.type

        viagemModalArrayList = gson.fromJson(json, type)

        if (viagemModalArrayList == null) {
            viagemModalArrayList = ArrayList<ViagemModal>()
        }
    }

    // salvar informações no array
    private fun salvar() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        val json = gson.toJson(viagemModalArrayList)
        editor.putString("viagem", json)
        editor.apply()

        Toast.makeText(this, "Destino salvo com sucesso! ", Toast.LENGTH_SHORT).show()
    }
}