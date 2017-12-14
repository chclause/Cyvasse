package com.example.charlie.cyvasse

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.Tile
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast

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
                    setHighlightedMovement(position, currentTile.movement)
                    // Set ranged unit stuff
                    if (currentTile.range > 1) {
                        setHighlightedRange(position, currentTile.range)
                    }
                    for (tile in currentlyHighlighted) {
                        if (tile != -1) {
                            // Only highlight terrain or enemy units
                            if (gameObject.gameTiles[tile].tileType == TileType.TERRAIN ||
                                    (currentTile.p1Tile && !gameObject.gameTiles[tile].p1Tile) ||
                                    (!currentTile.p1Tile && gameObject.gameTiles[tile].p1Tile)) {
                                gameObject.gameTiles[tile].highlighted = true
                            }
                            if (gameObject.gameTiles[tile].tileType == TileType.MOUNTAIN) {
                                gameObject.gameTiles[tile].highlighted = false
                            }
                        }
                    }
                    selectedTile = position
                    gameBoard.invalidateViews()
                }
                else {
                    // Deselect
                    if (position == selectedTile) {
                        resetHighlighted()
                        selectedTile = -1
                        gameBoard.invalidateViews()
                    }
                    // Movement
                    else if (currentTile.highlighted && currentTile.tileType == TileType.TERRAIN) {
                        resetHighlighted()
                        gameObject.gameTiles[position] = GameTile(gameObject.gameTiles[selectedTile].tileType, gameObject.gameTiles[selectedTile].p1Tile)
                        gameObject.gameTiles[selectedTile] = GameTile(TileType.TERRAIN, true)
                        selectedTile = -1
                        gameObject.p1Turn = !gameObject.p1Turn
                        GlobalGameData.player1Turn = !GlobalGameData.player1Turn
                        selectedTile = -1
                        gameBoard.invalidateViews()
                    }
                    else if ((currentTile.p1Tile && gameObject.gameTiles[selectedTile].p1Tile) || (!currentTile.p1Tile && !gameObject.gameTiles[selectedTile].p1Tile)) {
                        return
                    }
                    else if (currentTile.highlighted && currentTile.tileType != TileType.MOUNTAIN && currentTile.tileType != TileType.TERRAIN) {
                        // Player wins attack
                        Toast.makeText(this@OfflineGameActivity, "Attack: ${calculateAttack(position) + gameObject.gameTiles[selectedTile].attack}",
                                Toast.LENGTH_LONG).show()
                       if ((calculateAttack(position) + gameObject.gameTiles[selectedTile].attack) > currentTile.defense) {
                           resetHighlighted()
                           if (gameObject.gameTiles[position].tileType == TileType.KING) {
                               // Game over
                               val intent = Intent(applicationContext, MainActivity::class.java)
                               startActivity(intent)
                           }
                           gameObject.gameTiles[position] = GameTile(gameObject.gameTiles[selectedTile].tileType, gameObject.gameTiles[selectedTile].p1Tile)
                           gameObject.gameTiles[selectedTile] = GameTile(TileType.TERRAIN, true)
                           selectedTile = -1
                           gameObject.p1Turn = !gameObject.p1Turn
                           GlobalGameData.player1Turn = !GlobalGameData.player1Turn
                           selectedTile = -1
                           gameBoard.invalidateViews()
                       }
                        // Its a tie, both die
                        else if ((calculateAttack(position) + gameObject.gameTiles[selectedTile].attack) == currentTile.defense) {
                           resetHighlighted()
                           if (gameObject.gameTiles[position].tileType == TileType.KING || gameObject.gameTiles[selectedTile].tileType == TileType.KING) {
                               // Game over
                               val intent = Intent(applicationContext, MainActivity::class.java)
                               startActivity(intent)
                           }
                           gameObject.gameTiles[position] = GameTile(TileType.TERRAIN, true)
                           gameObject.gameTiles[selectedTile] = GameTile(TileType.TERRAIN, true)
                           selectedTile = -1
                           gameObject.p1Turn = !gameObject.p1Turn
                           GlobalGameData.player1Turn = !GlobalGameData.player1Turn
                           selectedTile = -1
                           gameBoard.invalidateViews()
                       }
                    }
                }


            }
        }
    }

    // Set a ranged units range
    fun setHighlightedRange(pos: Int, range: Int) {
        setHighlightedMovement(pos, range)
    }

    // Calculate the attack of a tile
    fun calculateAttack(pos: Int): Int {
        var attack = 0
        Log.e("ATTACK POS", pos.toString())
        if (pos % 10 == 0) {
            if (checkOppositeTeam(pos, pos+1)) {
                attack += 1
            }
            if (pos < 90) {
                if (checkOppositeTeam(pos, pos+10)) {
                    attack += 1
                }
            }
            if (pos > 0) {
                if (checkOppositeTeam(pos, pos-10)) {
                    attack += 1
                }
            }
        }
        else if (pos % 10 == 9) {
            if (checkOppositeTeam(pos, pos-1)) {
                attack += 1
            }
            if (pos < 90) {
                if (checkOppositeTeam(pos, pos+10)) {
                    attack += 1
                }
            }
            if (pos > 9) {
               if (checkOppositeTeam(pos, pos-10)) {
                   attack += 1
               }
            }
        }
        else if (pos >= 90 && pos < 99) {
            if (checkOppositeTeam(pos, pos-10)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos+1)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos-1)) {
                attack += 1
            }
        }
        else if (pos >0 && pos < 9) {
            if (checkOppositeTeam(pos, pos+10)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos+1)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos-1)) {
                attack += 1
            }
        }
        else {
            if (checkOppositeTeam(pos, pos-10)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos+1)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos-1)) {
                attack += 1
            }
            if (checkOppositeTeam(pos, pos+10)) {
                attack += 1
            }
        }
        Log.e("RETURNING", attack.toString())
        return attack
    }

    fun checkOppositeTeam(pos1: Int, pos2: Int): Boolean {
        if (gameObject.gameTiles[pos1].tileType == TileType.MOUNTAIN || gameObject.gameTiles[pos2].tileType == TileType.MOUNTAIN) {
            return false
        }
        if (gameObject.gameTiles[pos1].tileType == TileType.TERRAIN || gameObject.gameTiles[pos2].tileType == TileType.TERRAIN) {
            return false
        }
        if (gameObject.gameTiles[pos1].p1Tile) {
            return !gameObject.gameTiles[pos2].p1Tile
        }
        else if (!gameObject.gameTiles[pos1].p1Tile) {
            return gameObject.gameTiles[pos2].p1Tile
        }
        else {
            Log.e("OPPOSITE TEAM ERROR", pos1.toString())
            return true
        }
    }

    fun setHighlightedMovement(pos: Int, movement: Int) {
        resetHighlighted()
        val isDragon = gameObject.gameTiles[pos].tileType == TileType.DRAGON
        // Left edge of map
        if (pos % 10 <= movement-1) {
            for (i in 1..movement) {
                // allowed movement right
                currentlyHighlighted.add(pos+i)
                // don't go off grid down
                if (pos-(10*i) >= 0) {
                    currentlyHighlighted.add(pos-(10*i))
                }
                // don't go off grid up
                if (pos+(10*i) <= 99) {
                    currentlyHighlighted.add(pos+(10*i))
                }
            }
            // don't wrap
            for (i in 1..movement) {
                if ((pos-i) % 10 >= 0) {
                    currentlyHighlighted.add(pos-i)
                }
                if ((pos-i) % 10 == 0) {
                    break
                }
            }
        }
        // Right edge of map
        else if (pos % 10 + (movement-1) >= 9) {
            for (i in 1..movement) {
                // allowed movement left
                currentlyHighlighted.add(pos-i)
                if (pos+(10*i) <= 99) {
                    currentlyHighlighted.add(pos+(10*i))
                }
                if (pos-(10*i) >= 9) {
                    currentlyHighlighted.add(pos-(10*i))
                }
            }
            for (i in 1..movement) {
                if ((pos+i) % 10 <=9 ) {
                    currentlyHighlighted.add(pos+i)
                }
                if ((pos+i) % 10 == 9) {
                    break
                }
            }
        }
        // Normal case
        else {
            for (i in 1..movement) {
                currentlyHighlighted.add(pos+i)
                currentlyHighlighted.add(pos-i)
                if (pos+(10*i) <= 99) {
                    currentlyHighlighted.add(pos+(10*i))
                }
                if (pos-(10*i) >= 0) {
                    currentlyHighlighted.add(pos-(10*i))
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
                currentlyHighlighted.add(pos-10)
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
        if (currentlyHighlighted.size > 50) {
            currentlyHighlighted = mutableListOf(-1, -1, -1 ,-1 , -1)
        }
    }

    // Don't allow back presses for now
    override fun onBackPressed() {
    }
}
