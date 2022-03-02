package mx.tecnm.tepic.ladm_u1_practica2_frenchnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    companion object {
        var titles= mutableListOf<String>()
        var details = mutableListOf<String>()
        var index : Int = 0
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_element,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.titulo.text = titles[i]
        viewHolder.descripcion.text = details[i]
        viewHolder.logo.setImageResource(R.drawable.moi)
    }

    override fun getItemCount(): Int {
        return titles.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var logo: ImageView
        var titulo: TextView
        var descripcion: TextView

        init{
            logo = itemView.findViewById(R.id.logo)
            titulo = itemView.findViewById(R.id.titulo)
            descripcion = itemView.findViewById(R.id.descripcion)
        }
    }
}