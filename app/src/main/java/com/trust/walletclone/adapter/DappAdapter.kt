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
import com.trust.walletclone.util.Dapp
import java.io.IOException
import java.io.InputStream


class DappAdapter(private val dataSet: ArrayList<Dapp>, private val context: Context?) :
    RecyclerView.Adapter<DappAdapter.ViewHolder>() {
    var onItemClick: ((Dapp) -> Unit)? = null


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dappName: TextView = view.findViewById(R.id.dapp_name)
        val dappDesc:TextView = view.findViewById(R.id.dapp_desc)
        val dappIcon:ImageView = view.findViewById(R.id.dapp_icon)
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
            .inflate(R.layout.item_dapp, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

            viewHolder.dappName.text = dataSet[position].name
            viewHolder.dappDesc.text = dataSet[position].description
            var imageStream: InputStream? = null
            try {
                imageStream = context!!.assets.open("dapp/" + dataSet[position].icon)
                val drawable = Drawable.createFromStream(imageStream, null)
                viewHolder.dappIcon.setImageDrawable(drawable)
            } catch (ex: IOException) {
                Log.e("error", ex.toString())
                return
            }

    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
