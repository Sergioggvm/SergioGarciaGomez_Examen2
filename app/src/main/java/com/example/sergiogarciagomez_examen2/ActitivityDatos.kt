package com.example.sergiogarciagomez_examen2

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ActitivityDatos : AppCompatActivity(){

    private lateinit var btFecha: Button
    private lateinit var btnLimpaiar: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    private lateinit var editTitulo: EditText

    private lateinit var completo: CheckBox
    private lateinit var dbHandler: DataHelper

    private var fecha = "Fecha"

    private var completado = false
    private var nuevo = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anadir_modificar_tareas)

        dbHandler = DataHelper(this)

        btFecha = findViewById(R.id.buttonFecha)
        btnLimpaiar = findViewById(R.id.buttonLimpiar)
        btnGuardar = findViewById(R.id.buttonGuardar)
        btnVolver = findViewById(R.id.buttonVolver)
        editTitulo = findViewById(R.id.editTextTitulo)
        completo = findViewById(R.id.checkBoxCompletado)

        val valor = intent.getStringExtra("selectedPartidaId")
        nuevo = intent.getIntExtra("selectedPartidaIdNuevo", 0)

        btnVolver.setOnClickListener {
            // Crear un Intent para abrir otra actividad (reemplaza YourActivity::class.java con el nombre de tu actividad de destino)
            // val intent = Intent(this, VistaCoordenadas::class.java)


            // Iniciar la nueva actividad
            //startActivity(intent)
            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

        }

        btnLimpaiar.setOnClickListener {

            completo.isEnabled = false
            editTitulo.setText("")

        }

        btFecha.setOnClickListener {

            abrirTimePickerDialog()

        }

        completo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                completado = true
            } else {
                completado = false
            }
        }

        btnGuardar.setOnClickListener {

            val titulo = editTitulo.text.toString()



            // Crea un objeto Disco y lo añade a la base de datos.
            val tarea = Tareas(titulo = titulo, fecha = fecha, completado = completado)

            val status = dbHandler.addTarea(tarea)
            // Verifica si la inserción fue exitosa.
            if (status > -1) {
                Toast.makeText(applicationContext, "Tarea agregado", Toast.LENGTH_LONG).show()

            }


        }


    }

    private fun abrirTimePickerDialog() {
        // Obtengo una instancia del calendario y fijo la hora y minutos actuales
        val calendario = Calendar.getInstance()
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minutos = calendario.get(Calendar.MINUTE)

        // Creo un timePickerDialog y le paso el contexto, la hora seleccionada y el minuto
        // y establezco una alarma
        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, horaSeleccionada, minutoSeleccionado ->
                // Este código se ejecuta cuando el usuario selecciona una hora
                establecerAlarma(horaSeleccionada, minutoSeleccionado)
            }, hora, minutos, true
        )

        timePickerDialog.show()
    }

    // Método para configurar una alarma a la hora especificada por el usuario
    private fun establecerAlarma(hora: Int, minutos: Int) {
        // Obtengo una instancia del calendario y le fijo la hora y minutos obtenidos por parámetro
        val calendarioAlarma = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hora)
            set(Calendar.MINUTE, minutos)
            set(Calendar.SECOND, 0)
        }

      fecha = calendarioAlarma.toString()
    }

}