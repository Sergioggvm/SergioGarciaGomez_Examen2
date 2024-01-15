package com.example.sergiogarciagomez_examen2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), Comunicador {

    private lateinit var btnAnadirTarea: Button
    private lateinit var btnCompletados: Button
    var idtarea = 0
    var nuevo = 0

    //private lateinit var listView: ListView
    private lateinit var dbHandler: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Inflomso la toolbarr
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_principal)
        setSupportActionBar(toolbar)

        btnAnadirTarea = findViewById(R.id.buttonAnadir)
        btnCompletados = findViewById(R.id.buttonCompletados)


        dbHandler = DataHelper(this)

        // Añadir FragmentoA
        // savedInstanceState es un Bundle que guarda el estado de la actividad. Si es null, significa que la actividad se está iniciando por primera vez.
        if (savedInstanceState == null) {
            // Comienza una transacción de fragmentos. Las transacciones se utilizan para añadir, reemplazar o realizar otras operaciones con fragmentos.
            supportFragmentManager.beginTransaction()
                // Reemplaza el contenido del contenedor (identificado por R.id.containerFragmentoA) con una instancia de FragmentoA.
                // Este es el proceso de agregar el fragmento a la actividad.
                .replace(R.id.fragmentTareasCompletadas, FragmentTareasPendinetes())
                // Confirma la transacción. Hasta que no se llama a commit, los cambios no son efectivos.
                .commit()
        }

        // Añadir FragmentoB
        // Repite el proceso para el FragmentoB. Esta vez se coloca en un contenedor diferente dentro del layout de la actividad.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentDetalles, FragmentDetalle())
                .commit()
        }

        btnAnadirTarea.setOnClickListener {
            // Crear un Intent para abrir otra actividad (reemplaza YourActivity::class.java con el nombre de tu actividad de destino)
            // val intent = Intent(this, VistaCoordenadas::class.java)


            // Iniciar la nueva actividad
            //startActivity(intent)
            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, ActitivityDatos::class.java)


            // Añadimos el ID de la partida seleccionada al Intent.
            intent.putExtra("selectedPartidaId", idtarea)

            startActivity(intent)



        }

        btnCompletados.setOnClickListener {
            // Crear un Intent para abrir otra actividad (reemplaza YourActivity::class.java con el nombre de tu actividad de destino)
            // val intent = Intent(this, VistaCoordenadas::class.java)


            // Iniciar la nueva actividad
            //startActivity(intent)
            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, ActivytiTareasCompletadas::class.java)

            startActivity(intent)



        }


    }



    override fun enviarDatos(titulo: String) {
        val fragmentoB = supportFragmentManager.findFragmentById(R.id.fragmentDetalles) as? FragmentDetalle
        fragmentoB?.actualizarTexto(titulo)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.ItemCompletar -> {

                dbHandler.updateTarea(idtarea)

                finish()
                true
            }

            R.id.ItemModificar -> {

                // Iniciar la nueva actividad
                //startActivity(intent)
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, ActitivityDatos::class.java)

                nuevo = 1

                // Añadimos el ID de la partida seleccionada al Intent.
                intent.putExtra("selectedPartidaId", idtarea)
                intent.putExtra("selectedPartidaIdNuevo", nuevo)
                startActivity(intent)
                true
            }

            R.id.Itemeliminar -> {

                dbHandler.deleteTarea(idtarea)

                finish()
                true
            }

            R.id.ItemEnviar -> {

                true
            }

            R.id.formatoGrande -> {

                true
            }

            R.id.formatoPequeño -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}