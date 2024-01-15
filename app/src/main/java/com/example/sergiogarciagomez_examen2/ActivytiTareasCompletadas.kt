package com.example.sergiogarciagomez_examen2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ActivytiTareasCompletadas: AppCompatActivity() {

    //private lateinit var listView: ListView
    private lateinit var dbHandler: DataHelper
    private lateinit var btnvolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activyty_completados)

        val listView = findViewById<ListView>(R.id.listviewCompletados)

        btnvolver = findViewById(R.id.buttonVolverAtras)

        dbHandler = DataHelper(this)

        // Obtiene la lista de discos de la base de datos.
        val tareasList = dbHandler.getAllTareasCompletadas()

        // Crea un adaptador para el RecyclerView y lo configura.
        //val adapter = AdaptadorListaCoordenadas(coordenadasList)
        //listView.layoutManager = LinearLayoutManager(this)
        //listView.adapter = adapter


        // Crea un adaptador para el ListView y configúralo.
        val adapter = AdaptadorListView(this,tareasList)
        listView.adapter = adapter

        btnvolver.setOnClickListener {
            // Crear un Intent para abrir otra actividad (reemplaza YourActivity::class.java con el nombre de tu actividad de destino)
            // val intent = Intent(this, VistaCoordenadas::class.java)


            // Iniciar la nueva actividad
            //startActivity(intent)
            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)



        }

    }
}

