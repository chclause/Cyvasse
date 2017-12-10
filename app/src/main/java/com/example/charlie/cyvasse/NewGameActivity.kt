package com.example.charlie.cyvasse

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.content_new_game.*

class NewGameActivity : AppCompatActivity() {
    var currentlySelected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val newGame = GameObject()
        newGame.init()
        GlobalGameData.globalGameObjects.add(newGame)
        val pos = GlobalGameData.globalGameObjects.indexOf(newGame)

        // Set up adapters for the game board and the tiles
        val gameBoardGridView: GridView = this.findViewById(R.id.gameBoard)
        gameBoardGridView.adapter = GameBoardAdapter(this)
        gameBoardGridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@NewGameActivity, p2.toString(),
                        Toast.LENGTH_LONG).show()
                //TODO check currentlySelected, check if any pieces remain, place and reset currentlySelected, update gameTiles and redraw
                // TODO allow re-placing of tiles that are on the board
            }
        }

        val tileSelectionGridView: GridView = this.findViewById(R.id.openingTileSelectionGrid)
        tileSelectionGridView.adapter = SetTileAdapter(this, pos)
        tileSelectionGridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, v: View,
                                     position: Int, id: Long) {
                //TODO: Set a global selection to position
                Toast.makeText(this@NewGameActivity, position.toString() + " selected",
                        Toast.LENGTH_LONG).show()
                currentlySelected = position
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
