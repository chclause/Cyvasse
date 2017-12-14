package com.example.charlie.cyvasse

import android.service.quicksettings.Tile

/**
 * A game object to hold some collections and data about the current game
 */
class GameObject {
    /** Tile number constants **/
    val numMountains = 4
    val numRabbles = 4
    val numSpears = 2

    val numCrossbows = 2
    val numCatupults = 1
    val numTrebuchet = 1

    val numLightHorse = 2
    val numHeavyHorse = 1
    val numElephant = 1


    // Collections of game tiles
    var gameTiles: MutableList<GameTile> = mutableListOf()
    var tempTiles: MutableList<GameTile> = mutableListOf()
    var p1Graveyard: MutableList<GameTile> = mutableListOf()
    var p2Graveyard: MutableList<GameTile> = mutableListOf()
    var p1ToBeSet: HashMap<TileType, Int> = hashMapOf()
    var p2ToBeSet: HashMap<TileType, Int> = hashMapOf()
    // Keep track of whose turn it is
    var p1Turn = true
    var gameStarted = false

    var preGameSelected = -1


    // Add all tiles that need to be set to their proper mapping
    fun init() {
        p1ToBeSet[TileType.RABBLE] = numRabbles
        p2ToBeSet[TileType.RABBLE] = numRabbles

        p1ToBeSet[TileType.SPEARMAN] = numSpears
        p2ToBeSet[TileType.SPEARMAN] = numSpears

        p1ToBeSet[TileType.CROSSBOW] = numCrossbows
        p2ToBeSet[TileType.CROSSBOW] = numCrossbows

        p1ToBeSet[TileType.CATIPULT] = numCatupults
        p2ToBeSet[TileType.CATIPULT] = numCatupults

        p1ToBeSet[TileType.TREBUCHET] = numTrebuchet
        p2ToBeSet[TileType.TREBUCHET] = numTrebuchet

        p1ToBeSet[TileType.LIGHTHORSE] = numLightHorse
        p2ToBeSet[TileType.LIGHTHORSE] = numLightHorse

        p1ToBeSet[TileType.HEAVYHORSE] = numHeavyHorse
        p2ToBeSet[TileType.HEAVYHORSE] = numHeavyHorse

        p1ToBeSet[TileType.ELEPHANT] = numElephant
        p2ToBeSet[TileType.ELEPHANT] = numElephant

        p1ToBeSet[TileType.MOUNTAIN] = numMountains
        p2ToBeSet[TileType.MOUNTAIN] = numMountains

        p1ToBeSet[TileType.DRAGON] = 1
        p2ToBeSet[TileType.DRAGON] = 1

        p1ToBeSet[TileType.KING] = 1
        p2ToBeSet[TileType.KING] = 1

        // Initialize the gameboard to only terrain
        for (i in 1..GlobalGameData.boardSize) {
            gameTiles.add(GameTile(TileType.TERRAIN, true))
            tempTiles.add(GameTile(TileType.TERRAIN, true))
        }
    }
}