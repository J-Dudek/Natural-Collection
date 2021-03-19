package fr.tuto.naturecollection

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.tuto.naturecollection.fragments.AddPlantFragment
import fr.tuto.naturecollection.fragments.CollectionFragment
import fr.tuto.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment(this),R.string.home_page_title)

        //importer la BottomNavigationView
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page->{
                    loadFragment(HomeFragment(this),R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_home->{
                    loadFragment(CollectionFragment(this),R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_home->{
                    loadFragment(AddPlantFragment(this),R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment, string:Int) {
        //Charger plantRepository
        val repo = PlantRepository()

        //actualiser le nom de la page
        findViewById<TextView>(R.id.page_title).text= resources.getString(string)
        //mettre à jour la liste de plantes
        repo.updateData{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }
}