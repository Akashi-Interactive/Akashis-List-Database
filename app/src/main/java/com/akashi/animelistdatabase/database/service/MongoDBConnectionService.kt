package com.akashi.animelistdatabase.database.service



import android.annotation.SuppressLint
import org.bson.Document
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.runBlocking

class MongoDBConnectionService {
//https://www.mongodb.com/docs/atlas/troubleshoot-connection/#special-characters-in-connection-string-password
    fun connect(){
        @SuppressLint("AuthLeak")
        val connectionString = "mongodb+srv://android_adl:TL0JoWBvpkdw7pos@akashilistdatabase" +
                ".qcai8ya.mongodb.net/?retryWrites=true&w=majority&appName=AkashiListDatabase"
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        MongoClient.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged.")
        }
    }
}