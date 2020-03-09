package com.timeus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timeus.R
import com.timeus.models.Diary
import de.hdodenhof.circleimageview.CircleImageView

class DiaryListAdapter(
    var context: Context,
    var diaryList: List<Diary>
) : RecyclerView.Adapter<DiaryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val eventView = inflater.inflate(R.layout.diary_list, parent, false)
        return ViewHolder(eventView)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) { // Get the data model based on position
        val diary = diaryList[position]
        val textHeading = viewHolder.textHeading
        val textContent = viewHolder.textContent
        val imageProfilePic = viewHolder.imageProfilePic
        val textAuthor = viewHolder.textAuthor
        textHeading.text = diary.title
        textContent.text = diary.content
        textAuthor.text = "by " + (diary.author?.name)
        Glide.with(context).load(diary.author?.picture).centerCrop().into(imageProfilePic)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textHeading: TextView
        var textContent: TextView
        var textAuthor: TextView
        var imageProfilePic: CircleImageView

        init {
            var position = adapterPosition
            textHeading = itemView.findViewById(R.id.textHeading)
            textContent = itemView.findViewById(R.id.textLayoutContent)
            textAuthor = itemView.findViewById(R.id.textAuthor)
            imageProfilePic = itemView.findViewById(R.id.imageProfilePic)
        }
    }

}