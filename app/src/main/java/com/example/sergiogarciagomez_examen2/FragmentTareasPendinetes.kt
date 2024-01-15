package com.example.sergiogarciagomez_examen2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView


class FragmentTareasPendinetes : Fragment() {

    // Declaramos una variable comunicador de tipo Comunicador, que será utilizada para comunicarse con otras partes de la aplicación.
    // Inicialmente es null porque aún no se ha establecido la comunicación.
    private var comunicador: Comunicador? = null

     private lateinit var mainActivity: MainActivity

    //private lateinit var listView: ListView
    private lateinit var dbHandler: DataHelper

    // El método onAttach se llama cuando el fragmento se asocia con su contexto, generalmente la actividad que lo contiene.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Se intenta convertir el contexto a tipo Comunicador. Si el contexto implementa la interfaz Comunicador, se asigna a la variable comunicador.
        comunicador = context as? Comunicador

        mainActivity = context as MainActivity

        dbHandler = DataHelper(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tareas_pendinetes, container, false)

        val listView = view.findViewById<ListView>(R.id.listViewTareasPendinetes)

        // Obtiene la lista de discos de la base de datos.
        val tareasList = dbHandler.getAllTareas()

        // Crea un adaptador para el RecyclerView y lo configura.
        //val adapter = AdaptadorListaCoordenadas(coordenadasList)
        //listView.layoutManager = LinearLayoutManager(this)
        //listView.adapter = adapter


        // Crea un adaptador para el ListView y configúralo.
        val adapter = AdaptadorListView(requireContext(),tareasList)
        listView.adapter = adapter


        // Agrega un OnItemClickListener al ListView
        listView.setOnItemClickListener { parent, view, position, id ->
            // Obtenemos el nombre de la ciudad y la imagen asociada en la posición seleccionada
            val titulo = tareasList[position].titulo
            mainActivity.idtarea = tareasList[position].id
            // Utilizamos la interfaz para enviar datos al otro fragmento
            comunicador?.enviarDatos(titulo)
        }

        return view
    }

}