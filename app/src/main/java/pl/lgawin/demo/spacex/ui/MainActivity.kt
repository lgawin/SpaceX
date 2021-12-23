package pl.lgawin.demo.spacex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.lgawin.demo.spacex.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SpaceX)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
