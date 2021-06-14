package br.ucb.seligaturista


import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    private lateinit var request:StringRequest
    private lateinit var requestQueue:RequestQueue
    private val paises: ArrayList<Paises> = ArrayList()
//    val url = "https://servicodados.ibge.gov.br/api/v1/localidades/paises?orderBy=nome"
    private val url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val origemSpinner = findViewById<Spinner>(R.id.origem_spinner)
        // Retorna os dados da API e insere no array Paises
        retornaJSONPaises()

        val spinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, paises as List<Any?>)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        origemSpinner.adapter = spinnerAdapter
//        val spinnerAdapter: ArrayAdapter<*> =
//            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_dropdown_item,
//                paises as List<*>)
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        origemSpinner.adapter = spinnerAdapter
//
        var paises: Paises?
//
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

        btnAdicionar.setOnClickListener {
//            startActivityForResult(intent, newWordActivityRequestCode)
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
    //                    jsonInt = jsonArray.getJSONObject(i).getInt("id")
                        jsonInt = cont
                        paises.add(Paises(jsonInt, jsonString))

                        cont += 1

    //                    val paises: ArrayList<Paises> = ArrayList()
    //                    paises.add(Paises(1, "Fulano "))
    //                    paises.add(Paises(2, "Fulano "))

                        println("PAISES: $jsonString")
                        println("ID: $jsonInt")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    println("Nomes:")
                }

        }) { }
        requestQueue.add(request)
    }

}


