package com.example.proyectotienda

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectotienda.databinding.ActivityFunBinding
import com.example.proyectotienda.fragments.CategoriesFragment
import com.example.proyectotienda.fragments.HomeFragment
import com.google.android.material.tabs.TabLayout

class FunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityFunBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        binding.tabLayout.removeAllTabs()
        //ICONOS
        val iconos = listOf(R.drawable.home, R.drawable.products, R.drawable.cesta)

        for (i in iconos.indices) {
            val tab = binding.tabLayout.newTab()

            val vistaTab = layoutInflater.inflate(R.layout.item_tab_personalizado, null)
            val img = vistaTab.findViewById<ImageView>(R.id.img_tab)

            img.setImageResource(iconos[i])
            img.setColorFilter(getColor(R.color.hueso))
            tab.customView = vistaTab
            binding.tabLayout.addTab(tab);
        }

        //abrir el home tan solo empezar
        if (savedInstanceState == null) {
            supportActionBar?.title = "Home"
            cambiarFragmento(HomeFragment())
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //cambiar a solo el nombre del usuario y un icono de salir
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportActionBar?.title = "Home"
                        cambiarFragmento(HomeFragment())
                    }
                    1 -> {
                        supportActionBar?.title = "Categorias"
                        cambiarFragmento(CategoriesFragment())
                    }
                    2 -> supportActionBar?.title = "Mi carrito"
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }


    //esta logica de cambiar fragmento debe estar en algun viewmodel
    fun cambiarFragmento(fragmento: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_fragmentos, fragmento) // Usa el ID del FrameLayout de tu XML
            .commit()
    }

}