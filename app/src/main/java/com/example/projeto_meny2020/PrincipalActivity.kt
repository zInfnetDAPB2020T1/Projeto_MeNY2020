package com.example.projeto_meny2020

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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
import com.example.projeto_meny2020.classes.NossoUsuarioGoogle
import com.example.projeto_meny2020.textgradient.MyGradientTextView
import com.example.projeto_meny2020.ui.gallery.GalleryFragment
import com.example.projeto_meny2020.ui.home.HomeFragment
import com.example.projeto_meny2020.viewModel.DadosTempoViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.app_bar_principal.*
import kotlinx.android.synthetic.main.content_principal.*
import kotlinx.android.synthetic.main.recycler_dias_seguintes.*

class PrincipalActivity : AppCompatActivity() {

    private val latLonList: Map<Int, List<String>> = mapOf(
        R.id.saopaulo to listOf("-23.573252", "-46.641681"),
        R.id.riodejaneiro to listOf("-22.875113", "-43.277548"),
        R.id.belohorizonte to listOf("-19.901739", "-43.964196"),
        R.id.portoalegre to listOf("-30.033333", "-51.2"),
        R.id.recife to listOf("-8.05", "-34.9"),
        R.id.fortaleza to listOf("-3.737464", "-38.546167"),
        R.id.salvador to listOf("-12.983333", "-38.516667"),
        R.id.curitiba to listOf("-25.416667", "-49.25"),
        R.id.belem to listOf("-1.437281", "-48.470614"),
        R.id.goiania to listOf("-16.701028", "-49.266793"),
        R.id.manaus to listOf("-3.113333", "-60.025278"),
        R.id.vitoria to listOf("-20.332179", "-40.345011"),
        R.id.maceio to listOf("-9.652406", "-35.722433"),
        R.id.natal to listOf("-5.80021", "-35.210669"),
        R.id.saoluis to listOf("-2.532519", "-44.296299"),
        R.id.florianopolis to listOf("-27.5949", "-48.5482"),
        R.id.joaopessoa to listOf("-7.123742", "-34.865646"),
        R.id.teresina to listOf("-5.102887", "-42.801549"),
        R.id.cuiaba to listOf("-15.596", "-56.097"),
        R.id.campogrande to listOf("-20.45", "-54.616667"),
        R.id.aracaju to listOf("-10.916667", "-37.066667"),
        R.id.macapa to listOf("0.035158", "-51.061633"),
        R.id.portovelho to listOf("-8.766667", "-63.9"),
        R.id.riobranco to listOf("-9.966667", "-67.8"),
        R.id.palmas to listOf("-10.166944", "-48.332778"),
        R.id.boavista to listOf("2.820842", "-60.671312"),
        R.id.brasilia to listOf("-15.783333", "-47.916667")
    )

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var dadosTempoViewModel: DadosTempoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


// mudar o intervalo para nao encher o saco do usuario, esses aqui foram apenas para testes!
        AppRate.with(this).setInstallDays(0)
            .setLaunchTimes(0)
            .setRemindInterval(1)
            .monitor()
        AppRate.showRateDialogIfMeetsConditions(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        dadosTempoViewModel = ViewModelProviders.of(this)[DadosTempoViewModel::class.java]

        dadosTempoViewModel.fileDir = filesDir.toString()
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

        navView.setNavigationItemSelectedListener{
           when(it.itemId){
               R.id.nav_avaliar -> {
                   try{
                       startActivity(Intent(Intent.ACTION_VIEW,
                           Uri.parse("market://details?id=" + "com.android.chrome")))
                   }catch (e: ActivityNotFoundException){
                       startActivity(Intent(Intent.ACTION_VIEW,
                           Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)))
                   }
               }
               R.id.nav_signOut -> {
                   val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                       .requestIdToken(getString(R.string.default_client_id))
                       .requestEmail()
                       .build()

                   val googleSignInClient = GoogleSignIn.getClient(this, gso)
                   googleSignInClient.signOut()
                   finish()
               }
               R.id.nav_share -> {
                   val share = Intent(Intent.ACTION_SEND).apply {
                       type = "text/plain"
                       putExtra(Intent.EXTRA_TEXT, "Estou usando o Tempigo, o seu amigo para ver o tempo! Baixa aí, também")
                   }
                   if (share.resolveActivity(packageManager) != null){
                       startActivity(share)
                   }
               }
               R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow ->{
                   navController.navigate(it.itemId)
                   drawerLayout.closeDrawers()
               }
           }
            return@setNavigationItemSelectedListener true
        }


        //adiciona o ad
        MobileAds.initialize(this)
        val header = navView.getHeaderView(0)
        val adview = header.findViewById(R.id.adView) as AdView
        val request = AdRequest.Builder().build()
        adview.loadAd(request)

//        var gradient_max = gradientTextView.setColors(R.color.primaryColor, R.color.colorMax)
//        var gradient_min = gradientTextView.setColors2(R.color.colorMinGradient, R.color.colorMin)
//        //não sei colocar para o text view, n sei se é aqui ou dentro do adapter do recycle
    }

    override fun onDestroy() {
        super.onDestroy()
//        usuario.usuario.signOut()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //menu lateral para mudar as cidades
        when (item.itemId) {
            R.id.aracaju, R.id.belem, R.id.belohorizonte, R.id.boavista,
            R.id.brasilia, R.id.campogrande, R.id.cuiaba, R.id.curitiba,
            R.id.florianopolis, R.id.fortaleza, R.id.goiania, R.id.joaopessoa,
            R.id.macapa, R.id.maceio, R.id.manaus, R.id.natal, R.id.palmas,
            R.id.portoalegre, R.id.portovelho, R.id.recife, R.id.riobranco,
            R.id.riodejaneiro, R.id.salvador, R.id.saoluis, R.id.saopaulo,
            R.id.teresina, R.id.vitoria -> {

                Toast.makeText(this, "A cidade selecionada foi: ${item}", Toast.LENGTH_SHORT).show()

                dadosTempoViewModel.lat = latLonList[item.itemId]!![0]
                dadosTempoViewModel.lon = latLonList[item.itemId]!![1]

                if (dadosTempoViewModel.currentFragment is GalleryFragment) {
                    val fAtual = dadosTempoViewModel.currentFragment as GalleryFragment
                    fAtual.DadosEViews2()
                } else if (dadosTempoViewModel.currentFragment is HomeFragment) {
                    val fAtual = dadosTempoViewModel.currentFragment as HomeFragment
                    fAtual.DadosEViews()
                } else {
                    Log.e("Mudar lat/lon", "ocorreu algum erro ao pegar os fragments")
                }
                return false
            }
            else -> {
                Log.d("ERRO AO CLICAR TOOLBAR", "algo inesperado esta acontecendo")
                return false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


