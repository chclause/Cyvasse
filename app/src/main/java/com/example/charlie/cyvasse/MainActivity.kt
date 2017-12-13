package com.example.charlie.cyvasse

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start a new game
        newGameButton.setOnClickListener {
            val intent = Intent(applicationContext, NewGameActivity::class.java)
            GlobalGameData.player1Turn = true
            startActivity(intent)
        }
        // Load an old game
        loadGameButton.setOnClickListener {
            // TODO: Implement when LoadGameActivity is created
        }
        // How To
        howToButton.setOnClickListener {
            val intent = Intent(applicationContext, HowToActivity::class.java)
            startActivity(intent)
        }
    }
}
