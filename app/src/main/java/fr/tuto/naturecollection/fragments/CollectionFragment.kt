package fr.tuto.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.tuto.naturecollection.MainActivity
import fr.tuto.naturecollection.PlantRepository.Singleton.plantList
import fr.tuto.naturecollection.R
import fr.tuto.naturecollection.adapter.PlantAdapter
import fr.tuto.naturecollection.adapter.PlantItemDecoration

class CollectionFragment(private val context:MainActivity) :Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection,container,false)

        // recuperer la recyclerView

        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = PlantAdapter(context,plantList.filter { it.liked },R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}