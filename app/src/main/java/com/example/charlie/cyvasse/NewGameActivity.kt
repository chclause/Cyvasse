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
                // No tile to set, but tile needs to be returned to list if it isn't a terrain tile
                if (selectedTileType == TileType.TERRAIN || position < GlobalGameData.boardSize/2) {
                    if (newGame.tempTiles[position].tileType != TileType.TERRAIN) {
                        var tilesLeft: Int
                        if (newGame.p1Turn) {
                            tilesLeft = newGame.p1ToBeSet.getOrDefault(newGame.tempTiles[position].tileType, 0)
                            tilesLeft ++
                            newGame.p1ToBeSet[newGame.tempTiles[position].tileType] = tilesLeft
                        }
                        else {
                            tilesLeft = newGame.p2ToBeSet.getOrDefault(newGame.tempTiles[position].tileType, 0)
                            tilesLeft ++
                            newGame.p2ToBeSet[newGame.tempTiles[position].tileType] = tilesLeft
                        }
                        newGame.tempTiles[position] = GameTile(TileType.TERRAIN, true)
                    }
                    gameBoardGridView.invalidateViews()
                    return
                }
                // Set or replace the correct tile
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
                // Reset selection and then redraw
                selectedTileType = TileType.TERRAIN
                gameBoardGridView.invalidateViews()
            }
        }

        // Set up adapter for selection panel
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
            var anyLeft = false
            val pSet = newGame.p1ToBeSet.keys
            for (key in pSet) {
                if (newGame.p1Turn) {
                    if (newGame.p1ToBeSet.getOrDefault(key, -1) > 0) {
                        anyLeft = true
                        break
                    }
                }
                else {
                    if (newGame.p2ToBeSet.getOrDefault(key, -1) > 0) {
                        anyLeft = true
                        break
                    }
                }
            }
            // If p1 is out of tiles and it is their turn
            if (!anyLeft && newGame.p1Turn) {
                // Set it to p2 turn
                newGame.p1Turn = !newGame.p1Turn
                GlobalGameData.player1Turn = !GlobalGameData.player1Turn
                // Reset the temp grid and set p1's tiles in the real grid, redraw
                for (i in 1..newGame.tempTiles.size-1) {
                    newGame.gameTiles[i] = GameTile(newGame.tempTiles[i].tileType, true)
                    newGame.tempTiles[i] = GameTile(TileType.TERRAIN, true)
                }
                gameBoardGridView.invalidateViews()
                tileSelectionGridView.invalidateViews()
            }
            else if (!anyLeft && !newGame.p1Turn) {
                var temp: Int
                newGame.p1Turn = !newGame.p1Turn
                newGame.gameStarted = true
                GlobalGameData.player1Turn = !GlobalGameData.player1Turn
                for (i in 1..newGame.tempTiles.size-1) {
                    if (newGame.tempTiles[i].tileType != TileType.TERRAIN) {
                        temp = convertTilePosition(i)
                        newGame.gameTiles[temp] = GameTile(newGame.tempTiles[i].tileType, false)
                    }
                }
                val intent = Intent(applicationContext, OfflineGameActivity::class.java)
                intent.putExtra("GAME", GlobalGameData.globalGameObjects.indexOf(newGame))
                startActivity(intent)
            }
        }


        // Go home
        startScreenHomeButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Algorithm to set player 2's tiles into their spot in their side of the grid
    fun convertTilePosition(pos: Int): Int {
        var otherTile = 0
        var startV = 99
        var startH: Int
        while (startV > pos+10) {
            startV -= 10
            otherTile += 10
        }
        startH = 99 - otherTile
        while (startH != pos) {
            startH -= 1
            otherTile += 1
        }
        return otherTile
    }

    // Instead of being smart and making a global map of these I did this horrible thing in like 3 different places
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
