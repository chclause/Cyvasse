package com.example.charlie.cyvasse

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView

class OfflineGameActivity : AppCompatActivity() {

    var selectedTile = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)
        val gameID = intent.getIntExtra("GAME", 0)
        val gameObject = GlobalGameData.globalGameObjects[gameID]

        // Set up adapters
        // TODO: Evaluate if another adapter is needed for this
        val player1GraveTiles: GridView = this.findViewById(R.id.player1Tiles)
        player1GraveTiles.adapter = SetTileAdapter(this, gameID)
        val player2GraveTiles: GridView = this.findViewById(R.id.player2Tiles)
        player2GraveTiles.adapter = SetTileAdapter(this, gameID)

        // Set up board and adapter
        val gameBoard: GridView = this.findViewById(R.id.gameBoard)
        gameBoard.adapter = GameBoardAdapter(this, gameID)

        // Handle 1,000,000 scenarios when somebody touches the screen
        gameBoard.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, v: View,
                                     position: Int, id: Long) {
                // Handle no item selected
                if (selectedTile == -1) {

                }

            }
        }
    }

    // Don't allow back presses for now
    override fun onBackPressed() {
    }
}
