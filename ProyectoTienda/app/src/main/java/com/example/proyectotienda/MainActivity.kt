package com.example.proyectotienda

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectotienda.databinding.ActivityMainBinding
import com.example.proyectotienda.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //forzar que solo tenga una orientacion.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnSesion.setOnClickListener {
            val usuario = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            if (usuario.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(usuario, password)
            } else {
                Toast.makeText(
                    this, "Introduce el nombre de usuario y la contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //observamos el resultado de login
        loginViewModel.loginResult.observe(this) { loginResponse ->
            if (loginResponse != null) {
                val token = loginResponse.accessToken
                val intent = Intent(this, FunActivity::class.java)
                intent.putExtra("ACCESS_TOKEN", token)
                intent.putExtra("USERNAME", binding.editUsername.text.toString().trim())
                startActivity(intent)
                finish()
            }
        }

        //observamos los errores.
        loginViewModel.error.observe(this) { mensaje ->
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }

        //deshabilitar boton mientras carga
        loginViewModel.isLoading.observe(this) { loading ->
            binding.btnSesion.isEnabled = !loading
        }

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSesion.setOnClickListener {
            val intent = Intent(this, FunActivity::class.java)
            startActivity(intent)
        } */
    }
}