package codes.zaak.architecturesample.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.repository.model.response.Saga
import com.squareup.picasso.Picasso

class SagaAdapter(sagaList: List<Saga>) : RecyclerView.Adapter<SagaAdapter.SagaViewHolder>() {

    private var sagaList = ArrayList<Saga>()

    init {
        this.sagaList = sagaList as ArrayList<Saga>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SagaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.saga_card,
            parent, false
        )
        return SagaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SagaViewHolder, position: Int) {
        val item = sagaList[position]
        holder.sagaListItem(item)
    }

    override fun getItemCount(): Int {
        return sagaList.size
    }

    fun addSagaList(sagaList: List<Saga>) {
        val initPosition = this.sagaList.size
        this.sagaList.clear()
        this.sagaList.addAll(sagaList)
        notifyItemRangeInserted(initPosition, this.sagaList.size)
    }

    class SagaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.name)
        var image = itemView.findViewById<ImageView>(R.id.image)
        var description = itemView.findViewById<TextView>(R.id.description)

        fun sagaListItem(item: Saga) {
            name.text = item.name
            description.text = item.description

            if (item.image.isNotBlank()) {
                Picasso
                    .get()
                    .load(item.image)
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(image)
            }
        }
    }
}