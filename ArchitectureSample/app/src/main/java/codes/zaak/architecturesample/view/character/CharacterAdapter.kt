package codes.zaak.architecturesample.view.character

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.repository.model.response.Character
import codes.zaak.architecturesample.view.SmartItemUpdate
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(),
    SmartItemUpdate {

    private var characterList: List<Character> by Delegates.observable(emptyList()) { _, old, new ->
        smartUpdate(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(
            R.layout.character_card,
            parent, false
        )
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterItem = characterList[position]
        holder.characterListItem(characterItem)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun addCharacterList(characterList: List<Character>) {
        this.characterList = characterList
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var characterName = itemView.findViewById<TextView>(R.id.name)
        var characterImage = itemView.findViewById<ImageView>(R.id.image)
        var characterPowerLevel = itemView.findViewById<TextView>(R.id.power_level)

        fun characterListItem(characterItem: Character) {
            characterName.text = characterItem.name
            characterPowerLevel.text = characterItem.power

            if (characterItem.image.isNotBlank()) {
                Picasso
                    .get()
                    .load(characterItem.image)
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(characterImage)
            }
        }
    }
}