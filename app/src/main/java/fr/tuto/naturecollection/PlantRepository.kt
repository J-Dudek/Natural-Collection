package fr.tuto.naturecollection

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.tuto.naturecollection.PlantRepository.Singleton.databaseRef
import fr.tuto.naturecollection.PlantRepository.Singleton.downloadUri
import fr.tuto.naturecollection.PlantRepository.Singleton.plantList
import fr.tuto.naturecollection.PlantRepository.Singleton.storageReference
import java.util.*

class PlantRepository {

    object Singleton{

        private val BUCKET_URL: String = "gs://natural-collection.appspot.com"

        // se connecter à l'espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la réference plante
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //créer une liste qui contient les plantes
        val plantList = arrayListOf<PlantModel>()

        //contenir l'image courante
        var downloadUri:Uri?=null

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

    // inserer une nouvelle plante en bdd
    fun insertPlan(plant:PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

    //supprimer une plante de la base
    fun deletePlant(plant : PlantModel){
        databaseRef.child(plant.id).removeValue()
    }

    // créer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file:Uri,callback: () -> Unit){
        // verifier que ce fichier n'est pas null
        if(file!=null){
            val fileName= UUID.randomUUID().toString()+".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot,Task<Uri>>{ task->
                // on verifie si il y a eu un pb lors de l'envoi su fichier
                if(!task.isSuccessful){
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task->
                if(task.isSuccessful){
                    // recuperer l'image qui est en ligne
                    downloadUri = task.result
                    callback()
                }
            }

        }
    }

}