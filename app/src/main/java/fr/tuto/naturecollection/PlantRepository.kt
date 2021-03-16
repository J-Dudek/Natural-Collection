package fr.tuto.naturecollection

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.tuto.naturecollection.PlantRepository.Singleton.databaseRef
import fr.tuto.naturecollection.PlantRepository.Singleton.plantList
import javax.security.auth.callback.Callback

class PlantRepository {

    object Singleton{
        // se connecter à la réference plante
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //créer une liste qui contient les plantes
        val plantList = arrayListOf<PlantModel>()

    }

    fun updateData(callback: ()->Unit){
        // absorder les données récupérées et les mettre dans liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                // retirer les anciennes plantes
                plantList.clear()

                // recolter la liste
                for (ds in p0.children) {
                    val plant = ds.getValue(PlantModel::class.java)
                    Log.d(TAG,"plant size  : ${plant.toString()}")
                    // verifier que la plante n'est pas null
                    if (plant != null) {
                        Log.d(TAG, "plant : $plant")
                        plantList.add(plant)
                    }
                }
                //actionner le callback
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

    }

    //mise à jour de l'objet plante en BDD
    fun updatePlant(plant :PlantModel ){
        databaseRef.child(plant.id).setValue(plant)

    }

}