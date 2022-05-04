package com.trust.walletclone.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trust.walletclone.R
import com.trust.walletclone.util.Coin
import java.io.IOException
import java.io.InputStream


class ReceiveTokenAdapter(private val dataSet: ArrayList<Coin>, private val context: Context?) :
    RecyclerView.Adapter<ReceiveTokenAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName: TextView = view.findViewById(R.id.coin_name)
        val coinSymbol:TextView = view.findViewById(R.id.coin_symbol)
        val coinType:TextView = view.findViewById(R.id.coin_type)
        val coinIcon:ImageView = view.findViewById(R.id.coin_icon)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_receive_token, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.coinName.text = dataSet[position].name
        viewHolder.coinSymbol.text = dataSet[position].symbol
        viewHolder.coinType.text = dataSet[position].type.toString()
        if(dataSet[position].type == "native"){
            viewHolder.coinType.visibility = View.GONE
        }
        var imageStream: InputStream? = null
        try {
            imageStream = context!!.assets.open("coin/"+dataSet[position].icon + ".webp")
            val drawable = Drawable.createFromStream(imageStream, null)
            viewHolder.coinIcon.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e("error", ex.toString())
            return
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
