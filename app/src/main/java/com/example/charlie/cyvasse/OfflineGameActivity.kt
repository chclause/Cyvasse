package com.example.charlie.cyvasse

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class OfflineGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)

        // Set up adapters
        // TODO: Make a new adapter or make a parameter
        val player1GraveTiles: GridView = this.findViewById(R.id.player1Tiles)
        player1GraveTiles.adapter = SetTileAdapter(this, 0)
        val player2GraveTiles: GridView = this.findViewById(R.id.player2Tiles)
        player2GraveTiles.adapter = SetTileAdapter(this, 0)

        val gameBoard: GridView = this.findViewById(R.id.gameBoard)
        gameBoard.adapter = GameBoardAdapter(this)
    }
}
