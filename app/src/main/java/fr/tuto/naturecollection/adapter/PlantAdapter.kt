package fr.tuto.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.tuto.naturecollection.MainActivity
import fr.tuto.naturecollection.PlantModel
import fr.tuto.naturecollection.PlantRepository
import fr.tuto.naturecollection.R

class PlantAdapter(private val context:MainActivity,private val plantList:List<PlantModel>,  private val layoutId: Int): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {


    // pour ranger tt les composants à controller
    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperation des informations de la plante
        val currentPlant = plantList[position]
        // recuperer le repo
        val repo = PlantRepository()


        // utilisation de glide pour recuperer l'image à partir du liens
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //mise à jour des infos de la plante
        holder.plantName?.text = currentPlant.name
        holder.plantDescription?.text = currentPlant.description
        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //ajout interaction avec l'etoile
        holder.starIcon.setOnClickListener{
            // inverser si le btn est like ou non
            currentPlant.liked =!currentPlant.liked
            // mettre à jour l'objet plante
            repo.updatePlant(currentPlant)

        }

    }

    override fun getItemCount(): Int = plantList.size

}