package com.example.sergiogarciagomez_examen2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdaptadorListView  (private val context: Context, private val coordenadasList: List<Tareas>) : BaseAdapter() {

    override fun getCount(): Int {
        return coordenadasList.size
    }

    override fun getItem(position: Int): Any {
        return coordenadasList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ver_titulo, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val tarea = getItem(position) as Tareas

        viewHolder.txtTitulo.text = "Tarea"
        viewHolder.txtTituloTarea.text = tarea.titulo
        viewHolder.txtFecha.text = "Fecha"
        viewHolder.txtFechaTarea.text = tarea.fecha

        return view
    }

    private class ViewHolder(view: View) {
        val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
        val txtTituloTarea: TextView = view.findViewById(R.id.txtIdTituloPoner)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val txtFechaTarea: TextView = view.findViewById(R.id.txtFechaPoner)
    }
}