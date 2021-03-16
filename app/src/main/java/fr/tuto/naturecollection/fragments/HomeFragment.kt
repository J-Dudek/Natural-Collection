package fr.tuto.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tuto.naturecollection.MainActivity
import fr.tuto.naturecollection.PlantModel
import fr.tuto.naturecollection.R
import fr.tuto.naturecollection.adapter.PlantAdapter
import fr.tuto.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context:MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        val view = inflater?.inflate(R.layout.fragment_home,container,false)

        // creer une liste qui stoke les plantes
        val planteList = arrayListOf<PlantModel>()


        // recuperer le ryceclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context,planteList, R.layout.item_horizontal_plant);

        // recurer le second recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context,planteList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}