package br.ucb.seligaturista


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        Handler().postDelayed({
            finish()
        }, 3000)
    }
}
