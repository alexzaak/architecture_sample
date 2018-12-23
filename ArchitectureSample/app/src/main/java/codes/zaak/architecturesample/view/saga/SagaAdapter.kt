package codes.zaak.architecturesample.view.saga

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.repository.model.response.Saga
import codes.zaak.architecturesample.view.SmartItemUpdate
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlin.properties.Delegates

class SagaAdapter : RecyclerView.Adapter<SagaAdapter.SagaViewHolder>(), SmartItemUpdate {

    private val subject: PublishSubject<Saga> = PublishSubject.create()
    private var sagaList: List<Saga> by Delegates.observable(emptyList()) { _, old, new ->
        smartUpdate(old, new) { o, n -> o.id != n.id }
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
        holder.itemView.setOnClickListener { this.subject.onNext(item) }
    }

    override fun getItemCount(): Int {
        return sagaList.size
    }

    fun itemSelected(): Observable<Saga> {
        return this.subject.observeOn(AndroidSchedulers.mainThread())
    }

    fun addSagaList(sagaList: List<Saga>) {
        this.sagaList = sagaList
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