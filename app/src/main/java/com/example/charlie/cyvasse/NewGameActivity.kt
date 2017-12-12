package com.example.charlie.cyvasse

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.content.pm.PackageManager
import android.service.quicksettings.Tile
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.content_new_game.*

class NewGameActivity : AppCompatActivity() {
    var selectedTileType = TileType.TERRAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val pos: Int
        val newGame: GameObject

        if (GlobalGameData.player1Turn) {
            newGame = GameObject()
            newGame.init()
            GlobalGameData.globalGameObjects.add(newGame)
            pos = GlobalGameData.globalGameObjects.indexOf(newGame)
        }
        else {
            newGame = GlobalGameData.globalGameObjects.last()
            pos = GlobalGameData.globalGameObjects.indexOf(newGame)
            for (i in 1..GlobalGameData.boardSize) {
                newGame.tempTiles[i] = GameTile(TileType.TERRAIN, false)
            }
        }

        // Set up adapters for the game board and the tiles
        val gameBoardGridView: GridView = this.findViewById(R.id.gameBoard)
        gameBoardGridView.adapter = GameBoardAdapter(this, pos)

        gameBoardGridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                // No tile to set
                if (selectedTileType == TileType.TERRAIN || position < GlobalGameData.boardSize/2) {
                    return
                }
                if (newGame.p1Turn) {
                    // Check if any remain
                    var tilesLeft = 0
                    if (newGame.p1ToBeSet[selectedTileType] == 0) {
                        selectedTileType = TileType.TERRAIN
                        return
                    }
                    // Tile is already set, so replace it and increment the old tile type
                    if (newGame.tempTiles[position].tileType != TileType.TERRAIN) {
                        tilesLeft = newGame.p1ToBeSet.getOrDefault(newGame.tempTiles[position].tileType, 0)
                        tilesLeft ++
                        newGame.p1ToBeSet[newGame.tempTiles[position].tileType] = tilesLeft
                    }
                    // Set the tile in the temp grid
                    newGame.tempTiles[position] = GameTile(selectedTileType, true)
                    // Decrement
                    tilesLeft = newGame.p1ToBeSet.getOrDefault(selectedTileType, 0)
                    tilesLeft --
                    if (tilesLeft < 0) {
                        Log.e("NEWGAMEACTIVITY", "Tiles left less than 0")
                    }
                    newGame.p1ToBeSet[selectedTileType] = tilesLeft
                    // TODO: toast if they are done maybe
                }
                else {
                    // Check if any remain
                    var tilesLeft = 0
                    if (newGame.p2ToBeSet[selectedTileType] == 0) {
                        selectedTileType = TileType.TERRAIN
                        return
                    }
                    // Tile is already set, so replace it and increment the old tile type
                    if (newGame.tempTiles[position].tileType != TileType.TERRAIN) {
                        tilesLeft = newGame.p2ToBeSet.getOrDefault(newGame.tempTiles[position].tileType, 0)
                        tilesLeft ++
                        newGame.p2ToBeSet[newGame.tempTiles[position].tileType] = tilesLeft
                    }
                    // Set the tile in the temp grid
                    newGame.tempTiles[position] = GameTile(selectedTileType, true)
                    // Decrement
                    tilesLeft = newGame.p2ToBeSet.getOrDefault(selectedTileType, 0)
                    tilesLeft --
                    if (tilesLeft < 0) {
                        Log.e("NEWGAMEACTIVITY", "Tiles left less than 0")
                    }
                    newGame.p2ToBeSet[selectedTileType] = tilesLeft
                    // TODO: toast if they are done maybe
                }
                selectedTileType = TileType.TERRAIN
                gameBoardGridView.invalidateViews()
            }
        }

        val tileSelectionGridView: GridView = this.findViewById(R.id.openingTileSelectionGrid)
        tileSelectionGridView.adapter = SetTileAdapter(this, pos)
        tileSelectionGridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, v: View,
                                     position: Int, id: Long) {
                // Past selectable tile
                if (position > 10) {
                    return
                }
                // Set position and tiletype
                selectedTileType = getTileType(position)
                if (newGame.p1Turn) {
                    Toast.makeText(this@NewGameActivity, newGame.p1ToBeSet[selectedTileType].toString() + " remaining",
                            Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this@NewGameActivity, newGame.p2ToBeSet[selectedTileType].toString() + " remaining",
                            Toast.LENGTH_LONG).show()
                }
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

    fun getTileType(pos: Int): TileType {
        when (pos) {
            0 -> return TileType.RABBLE
            1 -> return TileType.SPEARMAN
            2 -> return TileType.CROSSBOW
            3 -> return TileType.CATIPULT
            4 -> return TileType.TREBUCHET
            5 -> return TileType.LIGHTHORSE
            6 -> return TileType.HEAVYHORSE
            7 -> return TileType.ELEPHANT
            8 -> return TileType.DRAGON
            9 -> return TileType.KING
            10 -> return TileType.MOUNTAIN
            else -> return TileType.TERRAIN
        }
    }
}
