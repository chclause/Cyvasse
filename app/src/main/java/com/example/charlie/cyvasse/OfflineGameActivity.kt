package com.example.charlie.cyvasse

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.Tile
import android.view.View
import android.widget.AdapterView
import android.widget.GridView

class OfflineGameActivity : AppCompatActivity() {

    var selectedTile = -1
    var p1Turn = true
    var currentlyHighlighted: MutableList<Int> = mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1)
    lateinit var gameObject: GameObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)
        val gameID = intent.getIntExtra("GAME", 0)
        gameObject = GlobalGameData.globalGameObjects[gameID]

        // Set up adapters
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
                val currentTile = gameObject.gameTiles[position]
                // Handle a selected item
                if (selectedTile == -1) {
                    // Not the current players turn
                    if (currentTile.p1Tile && !gameObject.p1Turn || !currentTile.p1Tile && gameObject.p1Turn) {
                        return
                    }
                    // Not clickable
                    if (currentTile.tileType == TileType.TERRAIN || currentTile.tileType == TileType.MOUNTAIN) {
                        return
                    }
                    // Undraw current highlights
                    for (i in 1..currentlyHighlighted.size-1) {
                        if (currentlyHighlighted[i] != -1) {
                            gameObject.gameTiles[currentlyHighlighted[i]].highlighted = false
                        }
                        currentlyHighlighted[i] = -1
                    }
                    gameBoard.invalidateViews()
                    // Set new highlighted
                    setHighlighted(position, currentTile.movement)
                    for (tile in currentlyHighlighted) {
                        if (tile != -1) {
                            // Only highlight terrain or enemy units
                            if (gameObject.gameTiles[tile].tileType == TileType.TERRAIN ||
                                    (currentTile.p1Tile && !gameObject.gameTiles[tile].p1Tile) ||
                                    (!currentTile.p1Tile && gameObject.gameTiles[tile].p1Tile)) {
                                gameObject.gameTiles[tile].highlighted = true
                            }
                        }
                    }
                    gameBoard.invalidateViews()
                }


            }
        }
    }

    // Currently only supports 1 tile movement
    fun setHighlighted(pos: Int, movement: Int) {
       // resetHighlighted()

        if (pos % 10 == 0) {
            currentlyHighlighted.add(pos+1)
            if (pos < 90) {
                currentlyHighlighted.add(pos+10)
            }
            if (pos > 0) {
                currentlyHighlighted.add(pos-10)
            }
        }
        else if (pos % 10 == 9) {
            currentlyHighlighted.add(pos-1)
            if (pos < 90) {
                currentlyHighlighted.add(pos+10)
            }
            if (pos > 9) {
                currentlyHighlighted.add(pos-1)
            }
        }
        else if (pos >= 90 && pos < 99) {
            currentlyHighlighted.add(pos-10)
            currentlyHighlighted.add(pos+1)
            currentlyHighlighted.add(pos-1)
        }
        else if (pos >0 && pos < 9) {
            currentlyHighlighted.add(pos+10)
            currentlyHighlighted.add(pos+1)
            currentlyHighlighted.add(pos-1)
        }
        else {
            currentlyHighlighted.add(pos+1)
            currentlyHighlighted.add(pos-1)
            currentlyHighlighted.add(pos+10)
            currentlyHighlighted.add(pos-10)
        }
    }

    // Reset the highlighted list
    fun resetHighlighted() {
        for (i in 1..currentlyHighlighted.size-1) {
            if (currentlyHighlighted[i] != -1) {
                gameObject.gameTiles[currentlyHighlighted[i]].highlighted = false
            }
            currentlyHighlighted[i] = -1
        }
    }

    // Don't allow back presses for now
    override fun onBackPressed() {
    }
}
