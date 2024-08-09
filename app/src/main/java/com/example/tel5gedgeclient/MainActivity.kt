package com.example.tel5gedgeclient


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.snackbar.Snackbar

import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.Fresco.initialize
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

var cloudIP = "http://192.168.0.190:3000/"  // My Laptop (for now)
var edgeIP = "http://192.168.99.234:3000/" // GXC gNB Machine
var currentServerIP = cloudIP

class MainActivity : AppCompatActivity() {

    private lateinit var btn_jokes: Button
    private lateinit var btn_mapview: Button
    private lateinit var tv_jokes: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var netswitch: SwitchCompat
    private lateinit var currentView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        netswitch = findViewById((R.id.netswitch))
        imageView = findViewById(R.id.image_view)
        imageView2 = findViewById(R.id.image_view2)
        btn_mapview = findViewById(R.id.btn_map)
        btn_jokes = findViewById(R.id.btn_joke)
        tv_jokes = findViewById(R.id.tv_joke)
        progressBar = findViewById(R.id.idLoadingPB)

        currentView = imageView

        netswitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                currentServerIP = edgeIP
            } else {
                currentServerIP = cloudIP
            }
            val message = if (isChecked) "Now using 5G EDGE services" else "Now using CLOUD services"
            Snackbar.make(btn_mapview, message, Snackbar.LENGTH_SHORT).show()
        }

        btn_mapview.setOnClickListener {
            getImage()
        }

        // Set an OnClickListener on the button view.
        btn_jokes.setOnClickListener {
            tv_jokes.text = String.format("")
            // show the progress bar
            progressBar.visibility = View.VISIBLE

            // Call the getjokes() method of the ApiCall class,
            // passing a callback function as a parameter.
            ApiCall().getjokes(this) { jokes ->
                // Set the text of the text view to the
                // joke value returned by the API response.
                //tv_jokes.text = jokes.value
                // hide the progress bar
                progressBar.visibility = View.GONE

                val model = jokes[0]
                tv_jokes.append(String.format("Name: %s\n", model.name))
                val observingUnits = model.observing_units.joinToString(", ")
                //tv_jokes.append(String.format("Observing Units: %s\n", observingUnits))

                for (i in model.observing_units.indices) {
                    val unit = model.observing_units[i]
                    val unitHolding = model.observing_units_holding[i]
                    var holding: String? = null

                    if (unitHolding){
                        holding = "IS holding"
                    } else {
                        holding = "NOT holding"
                    }
                    tv_jokes.append(String.format("%s: %s\n", unit, holding))
                }

                if (model.all_present){
                    tv_jokes.append(String.format("All Present\n"))
                }
                if (model.is_triggered){
                    tv_jokes.append(String.format("Triggered\n"))
                }

                // hide the progress bar
                //progressBar.visibility = View.GONE
            }
        }
    }
    private fun getImage() {
        val imageUrl = currentServerIP + "map"
        currentView.bringToFront()

        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            //.transition(DrawableTransitionOptions.withCrossFade(300))
            .into(currentView)

        if (currentView == imageView){
            currentView = imageView2

        } else {
            currentView = imageView
        }
    }
}


/*
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.tel5gedgeclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
*/
