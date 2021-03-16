package fr.tuto.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.tuto.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Charger plantRepository
        val repo = PlantRepository()
        //mettre à jour la liste de plantes
        repo.updateData{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()

        }

        // Injecter le fragment home dans fragment_container

    }
}