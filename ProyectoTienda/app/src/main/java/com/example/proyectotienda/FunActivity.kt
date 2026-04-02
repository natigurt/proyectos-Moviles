package com.example.proyectotienda

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectotienda.databinding.ActivityFunBinding
import com.google.android.material.tabs.TabLayout

class FunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFunBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Home"

        binding.tabLayout.removeAllTabs()

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

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> supportActionBar?.title = "Home"
                    1 -> supportActionBar?.title = "Productos"
                    2 -> supportActionBar?.title = "Mi carrito"
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }
}