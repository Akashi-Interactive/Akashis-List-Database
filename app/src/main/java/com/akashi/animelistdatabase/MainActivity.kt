package com.akashi.animelistdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.animelistdatabase.database.service.MongoDBConnectionService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mongoDBConnectionService = MongoDBConnectionService()
        mongoDBConnectionService.connect()
    }
}
/*
api: Este paquete podría contener clases y utilidades relacionadas específicamente con la comunicación con la API, como clases para manejar las solicitudes HTTP, modelos de datos para representar las respuestas de la API, etc.
model: Aquí podrías definir las clases de modelo que representan los datos de tu aplicación, como las clases para representar anime, personajes, géneros, etc. Estas clases podrían utilizarse tanto para mapear los datos recibidos de la API como para interactuar con la base de datos MongoDB.
database: Este paquete podría contener las clases y utilidades relacionadas con la interacción con la base de datos MongoDB, como clases para manejar la conexión a la base de datos, operaciones CRUD (Create, Read, Update, Delete), etc.
ui: Dentro de este paquete podrías organizar las clases relacionadas con la interfaz de usuario de tu aplicación, como actividades, fragmentos, adaptadores de RecyclerView, etc.
adapter: Aquí podrías colocar adaptadores personalizados para tus vistas de lista o grilla, si es necesario.
view: Este paquete podría contener clases específicas de vista, como vistas personalizadas o componentes reutilizables.
util: En este paquete podrías colocar clases de utilidad genérica que no encajen en ninguna otra categoría, como clases para el manejo de fechas, formatos de texto, etc.
service: Si tu aplicación necesita ejecutar servicios en segundo plano, podrías organizar esas clases aquí.
*/