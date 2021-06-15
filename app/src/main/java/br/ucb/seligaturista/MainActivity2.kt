package br.ucb.seligaturista

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MainActivity2 : AppCompatActivity() {

    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    private var nomeDestino: EditText? = null
    private var descricao: EditText? = null
    private var addBtn: Button? = null
    private var saveBtn: Button? = null
    private var itemRecyclerView: RecyclerView? = null
    private var adapter: ViagemAdapter? = null
    private var viagemModalArrayList: ArrayList<ViagemModal>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SeLigaTurista)
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

        // it is a class to notify the user of events that happen.
        // This is how you tell the user that something has happened in the
        // background.
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        saveBtn?.setOnClickListener(View.OnClickListener {
            salvar()
            // exibir notificação após salvar
            exibirNotificacao()
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

    private fun exibirNotificacao() {
        Thread.sleep(3000)
        // pendingIntent is an intent for future use i.e after
        // the notification is clicked, this intent will come into action
        val destino = nomeDestino?.text.toString()
        val data = descricao?.text.toString()
        val intent = Intent(this, afterNotification::class.java)

        // FLAG_UPDATE_CURRENT specifies that if a previous
        // PendingIntent already exists, then the current one
        // will update it with the latest intent
        // 0 is the request code, using it later with the
        // same method again will get back the same pending
        // intent for future reference
        // intent passed here is to our afterNotification class
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
        val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle("Pensando em Viajar?")
                .setContentText("Você tem um possível destino de viagem para $destino, em $data")
//                .setStyle(Notification.BigTextStyle().bigText("OPA"))
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                    R.drawable.notification_icon))
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
                .setContentTitle("Pensando em Viajar?")
                .setContentText("Você tem um possível destino de viagem para $destino, em $data")
//                .setStyle(Notification.BigTextStyle().bigText("OPA"))
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                    R.drawable.notification_icon))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }
}