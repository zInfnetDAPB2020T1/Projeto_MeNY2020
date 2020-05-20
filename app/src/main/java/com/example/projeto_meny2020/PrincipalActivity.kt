package com.example.projeto_meny2020

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.projeto_meny2020.ui.gallery.GalleryFragment
import com.example.projeto_meny2020.ui.home.HomeFragment
import com.example.projeto_meny2020.viewModel.DadosTempoViewModel
import kotlinx.android.synthetic.main.app_bar_principal.*
import kotlinx.android.synthetic.main.content_principal.*

class PrincipalActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var dadosTempoViewModel: DadosTempoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        dadosTempoViewModel = ViewModelProviders.of(this)[DadosTempoViewModel::class.java]

        dadosTempoViewModel.fileDir = filesDir.toString()
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.aracaju, R.id.belem, R.id.belohorizonte, R.id.boavista,
            R.id.brasilia, R.id.campogrande, R.id.cuiaba, R.id.curitiba,
            R.id.florianopolis, R.id.fortaleza, R.id.goiania, R.id.joaopessoa,
            R.id.macapa, R.id.maceio, R.id.manaus, R.id.natal, R.id.palmas,
            R.id.portoalegre, R.id.portovelho, R.id.recife, R.id.riobranco,
            R.id.riodejaneiro, R.id.salvador, R.id.saoluis, R.id.saopaulo,
            R.id.teresina, R.id.vitoria -> {
                Toast.makeText(this, "A cidade selecionada foi: ${item}", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}
