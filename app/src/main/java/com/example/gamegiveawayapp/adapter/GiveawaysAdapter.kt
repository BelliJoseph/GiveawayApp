package com.example.gamegiveawayapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.databinding.ActivityMainBinding
import com.example.gamegiveawayapp.databinding.GiveawayitemBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.squareup.picasso.Picasso

class GiveawaysAdapter(
    private val giveawaysClickListener: GiveawaysClickListener,
    private val giveaways: MutableList<Giveaways> = mutableListOf()
) : RecyclerView.Adapter<GiveawayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder {
        val giveawayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.giveawayitem, parent, false)
        return GiveawayViewHolder(giveawayView, giveawaysClickListener)


    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) {
        val giveaway = giveaways[position]



        holder.bind(giveaway)
    }

    override fun getItemCount(): Int = giveaways.size


    fun setNewGiveaways(newGiveaways: List<Giveaways>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }
}

class GiveawayViewHolder(
    view: View,
    private val giveawaysClickListener: GiveawaysClickListener
): RecyclerView.ViewHolder(view){

    private val date: TextView = itemView.findViewById(R.id.Date)
    private val platform: TextView = itemView.findViewById(R.id.platform)
    private val price: TextView = itemView.findViewById(R.id.price)
    private val status: TextView = itemView.findViewById(R.id.status)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val image: ImageView = itemView.findViewById(R.id.giveawayImage)

    fun bind(giveaways: Giveaways){
        date.text = giveaways.endDate
        platform.text = giveaways.platforms
        price.text = giveaways.worth
        status.text = giveaways.status
        title.text = giveaways.title


        itemView.setOnClickListener(){
            giveawaysClickListener.onGiveawaysClicked(giveaways)
        }

        Picasso.get()
            .load(giveaways.thumbnail)
            .resize(100,100)
            .into(image)
    }

}