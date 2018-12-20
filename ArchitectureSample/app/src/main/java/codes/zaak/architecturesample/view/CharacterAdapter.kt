package codes.zaak.architecturesample.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.repository.model.response.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(characterList: List<Character>?) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var characterList = ArrayList<Character>()

    init {
        this.characterList = characterList as ArrayList<Character>
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

    fun addCharacterList(characters: List<Character>) {
        val initPosition = characterList.size
        characterList.addAll(characters)
        notifyItemRangeInserted(initPosition, characterList.size)
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