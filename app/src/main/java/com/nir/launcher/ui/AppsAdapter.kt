package com.nir.launcher.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nir.launcher.R
import com.nir.launcher.model.AppData

/**
 * AppsAdapter class responsible for displaying the app launchers info
 */
class AppsAdapter(private val context: Context?, private val appList: List<AppData>) : RecyclerView.Adapter<AppsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.appName.text = appList[position].appName
        holder.appIcon.setImageDrawable(appList[position].icon)
        holder.itemView.setOnClickListener {
            val launchIntent =
                context?.packageManager?.getLaunchIntentForPackage(appList[position].packageName)
            context?.startActivity(launchIntent)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appName: TextView = itemView.findViewById(R.id.app_name)
        var appIcon: ImageView = itemView.findViewById(R.id.app_icon)
    }
}