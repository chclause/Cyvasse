package com.example.charlie.cyvasse

import android.content.Context
import android.service.quicksettings.Tile
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Adapter to set the images on offline, 2 player game board
 */

// TODO: Add gameID parameter when global data model implemented
class GameBoardAdapter(getContext : Context, id: Int) : BaseAdapter() {
    val context = getContext
    private val gameObject = GlobalGameData.globalGameObjects[id]
    private var tiles = gameObject.gameTiles

    // Required overrides
    override fun getItem(p0: Int): Any? { return null }
    override fun getItemId(p0: Int): Long { return p0.toLong() }

    // We want 8x8 grid tiles
    override fun getCount(): Int {
        return 100
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Load up a new custom square image if there are none with square dimensions and fill space
        //  otherwise just set it to return
        val imageView : SquareImageView
        if (!gameObject.gameStarted) {
            Log.e("IN ADAPTER", "NOT STARTED")
            tiles = gameObject.tempTiles
        }


        if (convertView == null) {
            imageView = SquareImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setPadding(0,0,0,0)
        }
        else {
            imageView = convertView as SquareImageView
        }

        if (gameObject.p1Turn) {
            when (tiles[position].tileType) {
                TileType.TERRAIN -> imageView.setImageResource(R.drawable.dirt_tiles)
                TileType.RABBLE -> imageView.setImageResource(R.drawable.black_rable)
                TileType.SPEARMAN -> imageView.setImageResource(R.drawable.black_spearman)
                TileType.CROSSBOW -> imageView.setImageResource(R.drawable.black_crossbow)
                TileType.CATIPULT -> imageView.setImageResource(R.drawable.black_catupult)
                TileType.TREBUCHET -> imageView.setImageResource(R.drawable.black_trebuchet)
                TileType.LIGHTHORSE -> imageView.setImageResource(R.drawable.black_lighthorse)
                TileType.HEAVYHORSE -> imageView.setImageResource(R.drawable.black_heavyhorse)
                TileType.ELEPHANT -> imageView.setImageResource(R.drawable.black_elephant)
                TileType.DRAGON -> imageView.setImageResource(R.drawable.black_dragon)
                TileType.KING -> imageView.setImageResource(R.drawable.black_king)
                TileType.MOUNTAIN -> imageView.setImageResource(R.drawable.black_mountain)
            }
        }
        else {
            if (gameObject.gameStarted) {
                when (tiles[position].tileType) {
                    TileType.TERRAIN -> imageView.setImageResource(R.drawable.dirt_tiles)
                    TileType.RABBLE -> imageView.setImageResource(R.drawable.blue_rable)
                    TileType.SPEARMAN -> imageView.setImageResource(R.drawable.blue_spearman)
                    TileType.CROSSBOW -> imageView.setImageResource(R.drawable.blue_crossbow)
                    TileType.CATIPULT -> imageView.setImageResource(R.drawable.blue_catupult)
                    TileType.TREBUCHET -> imageView.setImageResource(R.drawable.blue_trebuchet)
                    TileType.LIGHTHORSE -> imageView.setImageResource(R.drawable.blue_lighthorse)
                    TileType.HEAVYHORSE -> imageView.setImageResource(R.drawable.blue_heavyhorse)
                    TileType.ELEPHANT -> imageView.setImageResource(R.drawable.blue_elephant)
                    TileType.DRAGON -> imageView.setImageResource(R.drawable.blue_dragon)
                    TileType.KING -> imageView.setImageResource(R.drawable.blue_king)
                    TileType.MOUNTAIN -> imageView.setImageResource(R.drawable.blue_mountain)
                }
            }
            else {
                when (tiles[position].tileType) {
                    TileType.TERRAIN -> imageView.setImageResource(R.drawable.dirt_tiles)
                    TileType.RABBLE -> imageView.setImageResource(R.drawable.blue_rable_upright)
                    TileType.SPEARMAN -> imageView.setImageResource(R.drawable.blue_spearman_upright)
                    TileType.CROSSBOW -> imageView.setImageResource(R.drawable.blue_crossbow_upright)
                    TileType.CATIPULT -> imageView.setImageResource(R.drawable.blue_catupult_upright)
                    TileType.TREBUCHET -> imageView.setImageResource(R.drawable.blue_trebuchet_upright)
                    TileType.LIGHTHORSE -> imageView.setImageResource(R.drawable.blue_lighthorse_upright)
                    TileType.HEAVYHORSE -> imageView.setImageResource(R.drawable.blue_heavyhorse_upright)
                    TileType.ELEPHANT -> imageView.setImageResource(R.drawable.blue_elephant_upright)
                    TileType.DRAGON -> imageView.setImageResource(R.drawable.blue_dragon_upright)
                    TileType.KING -> imageView.setImageResource(R.drawable.blue_king_upright)
                    TileType.MOUNTAIN -> imageView.setImageResource(R.drawable.blue_mountain_upright)
                }
            }
        }

        return imageView
    }
}