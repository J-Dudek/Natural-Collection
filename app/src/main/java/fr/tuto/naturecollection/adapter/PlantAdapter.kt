package fr.tuto.naturecollection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fr.tuto.naturecollection.R

class PlantAdapter(private val layoutId: Int): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {


    // pour ranger tt les composants à controller
    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }
    /** Ewactement pareil
     * override fun getItemCount(): Int=5
     */
}