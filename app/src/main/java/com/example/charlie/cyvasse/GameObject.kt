package com.example.charlie.cyvasse

/**
 * A game object to hold some collections and data about the current game
 */
class GameObject {
    // Collections of game tiles
    var gameTiles: MutableList<GameTile> = mutableListOf()
    var p1Graveyard: MutableList<GameTile> = mutableListOf()
    var p2Graveyard: MutableList<GameTile> = mutableListOf()
    // Keep track of whose turn it is
    var p1Turn = true
}