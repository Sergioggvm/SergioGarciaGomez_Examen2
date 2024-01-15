package com.example.sergiogarciagomez_examen2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FragmentDetalle : Fragment() {

    // Variable para almacenar el texto actual que se muestra en el fragmento.
    private var tituloActual: String? = null
    private var fechaActual: String? = null

    // El método onSaveInstanceState se llama automáticamente cuando el sistema está a punto de destruir el fragmento.
    // Se utiliza para guardar cualquier dato que se quiera preservar ante cambios de configuración (como rotaciones de pantalla).
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardamos el texto actual en el Bundle. 'textoGuardado' es la clave con la que se guarda el texto.
        outState.putString("tituloGuardado", tituloActual)
        outState.putString("fechaGuardado", fechaActual)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflamos el layout asociado con FragmentoB.
        val view = inflater.inflate(R.layout.fragment_detalle, container, false)

        // Restauramos el estado si está disponible. savedInstanceState contiene los datos guardados en onSaveInstanceState.
        if (savedInstanceState != null) {
            // Recuperamos el texto guardado y lo asignamos a la variable textoActual.
            tituloActual = savedInstanceState.getString("textoGuardado")
            // Actualizamos el TextView en la vista con el texto recuperado.
            view.findViewById<TextView>(R.id.txtIdTituloPoner).text = tituloActual
        }

        // Devolvemos la vista inflada.
        return view
    }


    // Método para actualizar el texto mostrado en el fragmento.
    fun actualizarTexto(titulo: String) {
        // Guardamos el nuevo texto en textoActual.
        tituloActual = titulo
        // Buscamos el TextView por su ID y actualizamos su texto.
        view?.findViewById<TextView>(R.id.txtIdTituloPoner)?.text = titulo
    }

}