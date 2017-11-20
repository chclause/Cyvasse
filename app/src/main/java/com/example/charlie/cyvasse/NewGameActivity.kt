package com.example.charlie.cyvasse

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.GridView

import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.content_new_game.*

class NewGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        // Set up adapters for the game board and the tiles
        val gameBoardGridView: GridView = this.findViewById(R.id.gameBoard)
        gameBoardGridView.adapter = GameBoardAdapter(this)
        val tileSelectionGridView: GridView = this.findViewById(R.id.openingTileSelectionGrid)
        tileSelectionGridView.adapter = SetTileAdapter(this)
        tileSelectionGridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, v: View,
                                     position: Int, id: Long) {
                //TODO: Set a global selection to position
            }
        }

        // Hook up done button
        doneButton.setOnClickListener {
            // TODO: Implement checks for all tiles being set when more game logic exists
            // TODO: Check which player is setting tiles and start game or just clear itself
            val intent = Intent(applicationContext, OfflineGameActivity::class.java)
            startActivity(intent)
        }
        // Go home
        startScreenHomeButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
