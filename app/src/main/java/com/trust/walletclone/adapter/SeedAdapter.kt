package com.trust.walletclone.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.trust.walletclone.R
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.Dapp
import java.io.IOException
import java.io.InputStream


class SeedAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<SeedAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val seedId: TextView = view.findViewById(R.id.seed_id)
        val seedValue:TextView = view.findViewById(R.id.seed_value)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_seed, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.seedId.text = (position +1).toString()
        viewHolder.seedValue.text = dataSet[position]

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
