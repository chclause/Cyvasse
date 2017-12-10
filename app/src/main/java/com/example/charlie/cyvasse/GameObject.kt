package com.example.charlie.cyvasse

import android.service.quicksettings.Tile

/**
 * A game object to hold some collections and data about the current game
 */
class GameObject {
    /** Tile number constants **/
    val numMountains = 5
    val numRabbles = 5
    val numSpears = 2

    val numCrossbows = 2
    val numCatupults = 1
    val numTrebuchet = 1

    val numLightHorse = 2
    val numHeavyHorse = 1
    val numElephant = 1


    // Collections of game tiles
    var gameTiles: MutableList<GameTile> = mutableListOf()
    var p1Graveyard: MutableList<GameTile> = mutableListOf()
    var p2Graveyard: MutableList<GameTile> = mutableListOf()
    var p1ToBeSet: MutableList<GameTile> = mutableListOf()
    var p2ToBeSet: MutableList<GameTile> = mutableListOf()
    // Keep track of whose turn it is
    var p1Turn = true


    // Add all tiles that need to be set to their proper list
    fun init() {
        for (i in 1..numRabbles) {
            p1ToBeSet.add(GameTile(TileType.RABBLE, true))
            p2ToBeSet.add(GameTile(TileType.RABBLE, false))
        }
        for (i in 1..numSpears) {
            p1ToBeSet.add(GameTile(TileType.SPEARMAN, true))
            p2ToBeSet.add(GameTile(TileType.SPEARMAN, false))
        }
        for (i in 1..numCrossbows) {
            p1ToBeSet.add(GameTile(TileType.CROSSBOW, true))
            p2ToBeSet.add(GameTile(TileType.CROSSBOW, false))
        }
        for (i in 1..numCatupults) {
            p1ToBeSet.add(GameTile(TileType.CATIPULT, true))
            p2ToBeSet.add(GameTile(TileType.CATIPULT, false))
        }
        for (i in 1..numTrebuchet) {
            p1ToBeSet.add(GameTile(TileType.TREBUCHET, true))
            p2ToBeSet.add(GameTile(TileType.TREBUCHET, false))
        }
        for (i in 1..numLightHorse) {
            p1ToBeSet.add(GameTile(TileType.LIGHTHORSE, true))
            p2ToBeSet.add(GameTile(TileType.LIGHTHORSE, false))
        }
        for (i in 1..numHeavyHorse) {
            p1ToBeSet.add(GameTile(TileType.HEAVYHORSE, true))
            p2ToBeSet.add(GameTile(TileType.HEAVYHORSE, false))
        }
        for (i in 1..numElephant) {
            p1ToBeSet.add(GameTile(TileType.ELEPHANT, true))
            p2ToBeSet.add(GameTile(TileType.ELEPHANT, false))
        }
        for (i in 1..numMountains) {
            p1ToBeSet.add(GameTile(TileType.MOUNTAIN, true))
            p2ToBeSet.add(GameTile(TileType.MOUNTAIN, false))
        }
        p1ToBeSet.add(GameTile(TileType.KING, true))
        p2ToBeSet.add(GameTile(TileType.KING, false))
        p1ToBeSet.add(GameTile(TileType.DRAGON, true))
        p2ToBeSet.add(GameTile(TileType.DRAGON, false))

        // Initialize the gameboard to only terrain
        for (i in 1..GlobalGameData.boardSize) {
            gameTiles.add(GameTile(TileType.TERRAIN, true))
        }
    }
}