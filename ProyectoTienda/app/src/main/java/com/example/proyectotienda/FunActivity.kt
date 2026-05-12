package com.example.proyectotienda

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.proyectotienda.databinding.ActivityFunBinding
import com.example.proyectotienda.fragments.CartFragment
import com.example.proyectotienda.fragments.HomeFragment
import com.example.proyectotienda.fragments.ProductsFragment
import com.google.android.material.tabs.TabLayout

class FunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFunBinding
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityFunBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // contenido no se solapa de esta manera hasta arriba
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //intent = objeto con info de la actividad que llamo a esta
        //getStringExtra = busca valor asociado a Username, y el default es..
        username = intent.getStringExtra("USERNAME") ?: "Usuario"

        setupToolbar()
        setupTabs()

        // Cargar fragmento inicial
        if (savedInstanceState == null) {
            cambiarFragmento(HomeFragment())
        }
    }

    //configurar el uso del email user, y el boton de logout (navigationIcon)
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = username
        binding.toolbar.setNavigationOnClickListener {
            logout()
        }
    }

    // configurar tabs
    private fun setupTabs() {
        val iconos = listOf(R.drawable.home, R.drawable.products, R.drawable.cesta)

        for (icono in iconos) {
            val tab = binding.tabLayout.newTab()
            //lo configuramos vacios..
            val vistaTab = layoutInflater.inflate(R.layout.item_tab_personalizado, null)
            //lo llenamos con el icono
            val img = vistaTab.findViewById<ImageView>(R.id.img_tab)
            //para que sea mas bonito visualmente
            img.setImageResource(icono)
            img.setColorFilter(getColor(R.color.hueso))
            //asigna la vista pesonalizada a la pestanya
            tab.customView = vistaTab
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> HomeFragment()
                    1 -> ProductsFragment()
                    2 -> CartFragment()
                    else -> return
                }
                cambiarFragmento(fragment)
                supportActionBar?.title = username
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun logout() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().clear().apply()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    fun cambiarFragmento(fragmento: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_fragmentos, fragmento)
            .commit()
    }
}