package br.ucb.seligaturista

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.MediaController
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
        Thread.sleep(3000)
        setTheme(R.style.Theme_SeLigaTurista)
        setContentView(R.layout.activity_main)

        // Retorna os dados da API e insere no array Paises
        retornaJSONPaises()
        val spinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, paises as List<Any?>)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        origem_spinner.adapter = spinnerAdapter

        var paises: Paises?
        origem_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                text_view?.text = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // consultar informações sobre os documentos
        btnConsultar.setOnClickListener {
            val intentConsulta = Intent(this, InfoDocumentos::class.java )
            startActivity(intentConsulta)
        }

        // exibir video com tema de viagem
        exibirVideo()
    }
    // Retorna os dados da API e insere no array Paises
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

    // exibir video com tema de viagem na primeira tela
    private fun exibirVideo() {
        val mediaController = MediaController(this)

        mediaController.setAnchorView(video_view)
        video_view.setVideoPath("https://pic.pikbest.com/pre-videos/10/199210.mp4")
        video_view.setMediaController(mediaController)
        video_view.requestFocus()
        video_view.start()
    }

}

