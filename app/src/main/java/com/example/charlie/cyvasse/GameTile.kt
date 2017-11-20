package com.example.charlie.cyvasse

/**
 * The main class in the game.  I decided to not use a heirarchy and just have all game tiles
 *  initialize their own stats based on an enum type passed in.  I will only be keeping track of 1 collection of these
 *  and they will hold all the data needed for the game logic.  Attack, defense, range, and movement
 */
class GameTile {
    var attack: Int = 0
    var defense: Int = 0
    var range: Int = 0
    var movement: Int = 0
    lateinit var tileType: TileType
    constructor(tType: TileType) {
        tileType = tType
        init()
    }

    // Initialize the stats for this tile
    fun init() {
        when (tileType) {
            // Basic terrain tiles
            TileType.TERRAIN -> return
            TileType.MOUNTAIN -> return
            // Ground Unit tiles
            TileType.RABBLE -> {
                attack = 1
                defense = 1
                range = 1
                movement = 1
            }
            TileType.SPEARMAN -> {
                attack = 2
                defense = 2
                range = 1
                movement = 1
            }
            // Calvary Unit tiles
            TileType.LIGHTHORSE -> {
                attack = 2
                defense = 2
                range = 1
                movement = 3
            }
            TileType.HEAVYHORSE -> {
                attack = 3
                defense = 3
                range = 1
                movement = 2
            }
            TileType.ELEPHANT -> {
                attack = 4
                defense = 4
                range = 1
                movement = 2
            }
            // Ranged Units
            TileType.CROSSBOW -> {
                attack = 1
                defense = 1
                range = 2
                movement = 1
            }
            TileType.CATIPULT -> {
                attack = 2
                defense = 1
                range = 3
                movement = 1
            }
            TileType.TREBUCHET -> {
                attack = 3
                defense = 1
                range = 3
                movement = 1
            }
            // Special Unis
            TileType.DRAGON -> {
                attack = 5
                defense = 5
                range = 1
                movement = 10
            }
            TileType.KING -> {
                attack = 3
                defense = 1
                range = 1
                movement = 1
            }
        }
    }
}