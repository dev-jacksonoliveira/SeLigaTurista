package br.ucb.seligaturista

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info_documentos.*

class InfoDocumentos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SeLigaTurista)
        setContentView(R.layout.activity_info_documentos)

        btnSaibaMais.setOnClickListener {
            val intentSaibaMais = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.abear.com.br/blog-do-passageiro/recomendacoes/" +
                        "documentos-para-viajar-de-aviao/"))
            startActivity(intentSaibaMais)
        }

        infoBtnAdicionar.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }

    }
}