package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import so.codex.hawk.R

/*
 Application launch class
 The class is activity.
*/
class MainActivity : AppCompatActivity() {
    /*
     The method is designed to initialize
     the activity (setting the root view element
     and other ui elements).
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
