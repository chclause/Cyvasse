package com.example.charlie.cyvasse

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Adapter to set the GridAdapter full of game tiles at the start of a game
 */
class SetTileAdapter(getContext : Context, pos: Int) : BaseAdapter() {
    val context = getContext
    val gameObj = GlobalGameData.globalGameObjects[pos]

    // Required overrides
    override fun getItem(p0: Int): Any? { return null }
    override fun getItemId(p0: Int): Long { return p0.toLong() }

    // We want 22 tiles, 11 for the various classes and the next row of counts
    override fun getCount(): Int {
        return 22
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Load up a new custom square image if there are none with square dimensions and fill space
        //  otherwise just set it to return
        val imageView : SquareImageView

        if (convertView == null) {
            imageView = SquareImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setPadding(0,0,0,0)
        }
        else {
            imageView = convertView as SquareImageView
        }

        when (position) {
            0 -> imageView.setImageResource(R.drawable.black_rable)
            1 -> imageView.setImageResource(R.drawable.black_spearman)
            2 -> imageView.setImageResource(R.drawable.black_crossbow)
            3 -> imageView.setImageResource(R.drawable.black_catupult)
            4 -> imageView.setImageResource(R.drawable.black_trebuchet)
            5 -> imageView.setImageResource(R.drawable.black_lighthorse)
            6 -> imageView.setImageResource(R.drawable.black_heavyhorse)
            7 -> imageView.setImageResource(R.drawable.black_elephant)
            8 -> imageView.setImageResource(R.drawable.black_dragon)
            9 -> imageView.setImageResource(R.drawable.black_king)
            10 -> imageView.setImageResource(R.drawable.black_mountain)
            else -> { imageView.setImageResource(R.drawable.dirt_tiles) }
        }

        return imageView
    }
}