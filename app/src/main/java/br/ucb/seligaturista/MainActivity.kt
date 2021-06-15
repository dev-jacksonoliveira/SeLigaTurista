package br.ucb.seligaturista


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_info_documentos.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    private lateinit var request:StringRequest
    private lateinit var requestQueue:RequestQueue
    private val paises: ArrayList<Paises> = ArrayList()
    val url = "https://servicodados.ibge.gov.br/api/v1/localidades/paises?orderBy=nome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        setTheme(R.style.Theme_SeLigaTurista)
        setContentView(R.layout.activity_main)

        val origemSpinner = findViewById<Spinner>(R.id.origem_spinner)
        // Retorna os dados da API e insere no array Paises
        retornaJSONPaises()

        val spinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, paises as List<Any?>)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        origemSpinner.adapter = spinnerAdapter

        var paises: Paises?
        origemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                paises = parent.getItemAtPosition(position) as Paises
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                paises = null
            }
        }

        // consultar informações sobre os documentos
        btnConsultar.setOnClickListener {
            val intentConsulta = Intent(this, InfoDocumentos::class.java )
            startActivity(intentConsulta)
        }


        // direcionar para outra tela para adicionar um destino
        btnAdicionar.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }

    }

    private fun retornaJSONPaises() {

        // Instanciar a RequestQueue.
        requestQueue = Volley.newRequestQueue(this)
        // Solicite uma resposta de string do URL fornecido.
        request = StringRequest(Request.Method.GET, url, { response ->
                var jsonString: String
                var jsonInt: Int
                try {
                    val jsonArray = JSONArray(response)
                    var cont = 1
                    for (i in 0 until jsonArray.length()) {

                        jsonString = jsonArray.getJSONObject(i).getString("nome")
                        jsonInt = cont
                        paises.add(Paises(jsonInt, jsonString))
                        cont += 1

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

        }) { }
        requestQueue.add(request)
    }

}


