package com.example.charlie.cyvasse

/**
Different types of tiles a player can have or that can be on the board
 */
enum class TileType {
    // A basic terrain tile
    TERRAIN,
    // An impassible mountain tile
    MOUNTAIN,
    // A lowly rabble
    RABBLE,
    // Basic ranged unit
    CROSSBOW,
    // Mid-strength unit
    SPEARMAN,
    // Mid-strength more movement unit
    LIGHTHORSE,
    // High-strength more movement
    HEAVYHORSE,
    // Mid-strength ranged unit
    CATIPULT,
    // High-strength ranged unit
    TREBUCHET,
    // Highest-strength high movement
    ELEPHANT,
    // Very hard to kill, lots of movement
    DRAGON,
    // Kill to end the game
    KING
}