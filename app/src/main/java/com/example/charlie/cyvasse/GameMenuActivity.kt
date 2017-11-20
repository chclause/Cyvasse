package com.example.charlie.cyvasse

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_menu.*

class GameMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_menu)

        gameMenuSaveButton.setOnClickListener {
            //TODO: Implement when save functionality exists
        }
        gameMenuHowToButton.setOnClickListener {
            val intent = Intent(applicationContext, HowToActivity::class.java)
            startActivity(intent)
        }
        gameMenuExitButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
