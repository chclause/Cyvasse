package com.example.charlie.cyvasse

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Adapter to set the images on offline, 2 player game board
 */

// TODO: Add gameID parameter when global data model implemented
class GameBoardAdapter(getContext : Context) : BaseAdapter() {
    val context = getContext

    // Required overrides
    override fun getItem(p0: Int): Any? { return null }
    override fun getItemId(p0: Int): Long { return p0.toLong() }

    // We want 100 tiles
    override fun getCount(): Int {
        return 64
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
        imageView.setImageResource(R.drawable.dirt_tiles)

        return imageView
    }
}