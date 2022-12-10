package com.example.mobilliumcase.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mobilliumcase.R

class MainListActivity : AppCompatActivity(){
    private var navController : NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        navController = findNavController(R.id.movies_nav_host_fragment)
        //configureActionBar()
    }

    /**
     * Set title, back button
     */
    private fun configureActionBar() {
        supportActionBar?.title = "Movies"
    }

}