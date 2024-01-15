package com.example.sergiogarciagomez_examen2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
    companion object {
        private const val DATABASE_NAME = "TareasDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PARTIDAS = "tareas"
        private const val KEY_ID = "id"
        private const val KEY_TITULO = "titulo"
        private const val KEY_FECHA = "fecha"
        private const val KEY_COMPLETADO = "completado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createPartidasTable = (
                "CREATE TABLE $TABLE_PARTIDAS ("
                        + "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "$KEY_TITULO TEXT, "
                        + "$KEY_FECHA TEXT, "
                        + "$KEY_COMPLETADO TEXT"
                        + ")"
                )
        db.execSQL(createPartidasTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARTIDAS")
        onCreate(db)
    }


    fun getAllTareas(): ArrayList<Tareas> {
        // Lista para almacenar y retornar los discos.
        val tareasList = ArrayList<Tareas>()
        val completadoComprobar = "false"
        // Consulta SQL para seleccionar todos los discos.
        val selectQuery = "SELECT  * FROM $TABLE_PARTIDAS WHERE $KEY_COMPLETADO = $completadoComprobar"

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            // Ejecuta la consulta SQL.
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // Maneja la excepción en caso de error al ejecutar la consulta.
            db.execSQL(selectQuery)
            return ArrayList()
        }

        // Variables para almacenar los valores de las columnas.
        var id: Int
        var titulo: String
        var fecha: String
        var completado: Boolean

        // Itera a través del cursor para leer los datos de la base de datos.
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los índices de las columnas.
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val tituloIndex = cursor.getColumnIndex(KEY_TITULO)
                val fechaIndex = cursor.getColumnIndex(KEY_FECHA)
                val completadoIndex = cursor.getColumnIndex(KEY_COMPLETADO)

                // Verifica que los índices sean válidos.
                if (idIndex != -1 && tituloIndex != -1 && fechaIndex != -1) {
                    // Lee los valores y los añade a la lista de discos.
                    id = cursor.getInt(idIndex)
                    titulo = cursor.getString(tituloIndex)
                    fecha = cursor.getString(fechaIndex)
                    completado = cursor.getString(completadoIndex).toBoolean()

                    val disco = Tareas(id = id, titulo = titulo, fecha = fecha, completado = completado)
                    tareasList.add(disco)
                }
            } while (cursor.moveToNext())
        }

        // Cierra el cursor para liberar recursos.
        cursor.close()
        return tareasList
    }

    fun getAllTareasCompletadas(): ArrayList<Tareas> {
        // Lista para almacenar y retornar los discos.
        val tareasList = ArrayList<Tareas>()
        val completadoComprobar = "true"
        // Consulta SQL para seleccionar todos los discos.
        val selectQuery = "SELECT  * FROM $TABLE_PARTIDAS WHERE $KEY_COMPLETADO = $completadoComprobar"

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            // Ejecuta la consulta SQL.
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // Maneja la excepción en caso de error al ejecutar la consulta.
            db.execSQL(selectQuery)
            return ArrayList()
        }

        // Variables para almacenar los valores de las columnas.
        var id: Int
        var titulo: String
        var fecha: String
        var completado: Boolean

        // Itera a través del cursor para leer los datos de la base de datos.
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los índices de las columnas.
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val tituloIndex = cursor.getColumnIndex(KEY_TITULO)
                val fechaIndex = cursor.getColumnIndex(KEY_FECHA)
                val completadoIndex = cursor.getColumnIndex(KEY_COMPLETADO)

                // Verifica que los índices sean válidos.
                if (idIndex != -1 && tituloIndex != -1 && fechaIndex != -1) {
                    // Lee los valores y los añade a la lista de discos.
                    id = cursor.getInt(idIndex)
                    titulo = cursor.getString(tituloIndex)
                    fecha = cursor.getString(fechaIndex)
                    completado = cursor.getString(completadoIndex).toBoolean()

                    val disco = Tareas(id = id, titulo = titulo, fecha = fecha, completado = completado)
                    tareasList.add(disco)
                }
            } while (cursor.moveToNext())
        }

        // Cierra el cursor para liberar recursos.
        cursor.close()
        return tareasList
    }

    fun addTarea(tareas: Tareas): Long {
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            // Prepara los valores a insertar.
            contentValues.put(KEY_TITULO, tareas.titulo)
            contentValues.put(KEY_FECHA, tareas.fecha)
            contentValues.put(KEY_COMPLETADO, tareas.completado)

            // Inserta el nuevo disco y retorna el ID del nuevo disco o -1 en caso de error.
            val success = db.insert(TABLE_PARTIDAS, null, contentValues)
            db.close()
            return success
        } catch (e: Exception) {
            // Maneja la excepción en caso de error al insertar.
            Log.e("Error", "Error al agregar disco", e)
            return -1
        }
    }

    fun deleteTarea(idTarea: Int): Int {
        val db = this.writableDatabase
        // Elimina la fila correspondiente y retorna el número de filas afectadas.
        val success = db.delete(TABLE_PARTIDAS, "$KEY_ID = ?", arrayOf(idTarea.toString()))
        db.close()
        return success
    }

    fun updateTarea(idTarea: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        // Prepara los valores a actualizar.
        contentValues.put(KEY_COMPLETADO, "true")

        // Actualiza la fila correspondiente y retorna el número de filas afectadas.
        return db.update(TABLE_PARTIDAS, contentValues, "$KEY_ID = ?", arrayOf(idTarea.toString()))
    }

    fun updateTareaDatos(idTarea: Int, titulo: String, fecha: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        // Prepara los valores a actualizar.
        contentValues.put(KEY_TITULO, titulo)
        contentValues.put(KEY_FECHA, titulo)

        // Actualiza la fila correspondiente y retorna el número de filas afectadas.
        return db.update(TABLE_PARTIDAS, contentValues, "$KEY_ID = ?", arrayOf(idTarea.toString()))
    }


}