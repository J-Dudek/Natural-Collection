package fr.tuto.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.tuto.naturecollection.MainActivity
import fr.tuto.naturecollection.PlantModel
import fr.tuto.naturecollection.PlantRepository
import fr.tuto.naturecollection.PlantRepository.Singleton.downloadUri
import fr.tuto.naturecollection.R
import java.util.*

class AddPlantFragment(private val context:MainActivity): Fragment() {
    private var file:Uri?=null
    private var uploadedImage: ImageView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant,container,false)

        // Recuperer upladedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // recuperer le bouton pour charger l'image
        val pickUpImageButton = view.findViewById<Button>(R.id.upload_button)
        // interaction
        pickUpImageButton.setOnClickListener {
            pickUpImage()
        }

        // recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{ sendForm(view)}
        return view
    }

    private fun sendForm(view:View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!){
            //recuperer le nom de la plante
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer nouvel objet Plant model
            val plant = PlantModel(UUID.randomUUID().toString(),plantName,plantDescription,downloadImageUrl.toString(),liked = true,grow,water)

            //envoyer en bdd
            repo.insertPlan(plant)

        }




    }

    private fun pickUpImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select picture"),47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            // Verifier si les données receptionnées sont null
            if(data == null || data.data == null) return

            //recuperer image receptionnée
            file = data.data
            // Mettre à jour l'aperçu de l'image
            uploadedImage?.setImageURI(file)

        }
    }
}