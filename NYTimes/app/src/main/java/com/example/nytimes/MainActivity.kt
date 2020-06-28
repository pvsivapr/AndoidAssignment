package com.example.nytimes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nytimes.uiviews.IntroPageActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClicked(view: View) = try
    {
        val introPageIntent: Intent = Intent(this, IntroPageActivity::class.java)
        startActivity(introPageIntent);
    }
    catch (ex: Exception)
    {

    }
}
