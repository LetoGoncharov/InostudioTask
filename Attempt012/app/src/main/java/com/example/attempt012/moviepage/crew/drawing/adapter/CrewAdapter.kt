package com.example.attempt012.moviepage.crew.drawing.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.R
import com.example.attempt012.databinding.CrewItemBinding
import com.example.attempt012.databinding.MovieItemBinding
import com.example.attempt012.moviepage.crew.drawing.model.CrewModel
import com.squareup.picasso.Picasso

class CrewAdapter: RecyclerView.Adapter<CrewAdapter.CrewHolder>() {

    private var crewList = ArrayList<CrewModel>()

    class CrewHolder(crewmate: View): RecyclerView.ViewHolder(crewmate) {

        val binding = CrewItemBinding.bind(crewmate)

        fun bind(crewwec: CrewModel) = with(binding) {
            crewmateRoleHeader.text = crewwec.role
            crewmateName.text = crewwec.name
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original${crewwec.photo}")
                .into(crewmateProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.crew_item, parent, false)
        return CrewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewHolder, position: Int) {
        holder.bind(crewList[position])
    }

    override fun getItemCount(): Int {
        Log.d("Response", "quantity of crew -->" + crewList.size.toString())
        return crewList.size
    }

    fun addCrewmate(crew: CrewModel) {
        crewList.add(crew)
        notifyDataSetChanged()
    }
}