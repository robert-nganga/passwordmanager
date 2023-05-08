package com.robert.passwordmanager.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.robert.passwordmanager.R
import com.robert.passwordmanager.models.Account
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var bottomAppBar: BottomAppBar

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            passwordViewModel.insert(getPasswordDetails(it))
            Log.i("MainActivity", "AccountItem added successfully")
        }
    }

    val passwordViewModel: PasswordViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController

        // connect NavigationView with NavController
        bottomNavView.setupWithNavController(navController)


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
//            val intent = Intent(this, AddPasswordActivity::class.java)
//            getResult.launch(intent)
            navController.navigateUp()
            navController.navigate(R.id.addAccountFragment)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getPasswordDetails(result: ActivityResult?): Account {
        val sdf = SimpleDateFormat("dd MMM, yyy")
        val currentDate: String = sdf.format(Date())
        return Account(
            websiteName = result?.data?.getStringExtra("name")!!,
            userName = result.data?.getStringExtra("email")!!,
            category = result.data?.getStringExtra("category")!!,
            password = result.data?.getStringExtra("password")!!,
            date = currentDate
        )
    }
}