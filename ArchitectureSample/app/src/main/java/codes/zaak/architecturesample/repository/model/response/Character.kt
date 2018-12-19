package codes.zaak.architecturesample.repository.model.response

import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import com.squareup.moshi.Json

data class Character(
    val id: Int,
    @Json(name = "nm_character")
    val name: String,
    @Json(name = "img_character")
    val image: String,
    @Json(name = "fighting_power")
    val power: String,
    val race: String,
    val saga: String
) {

    companion object {
        fun create(entity: CharacterEntity): Character {
            return Character(entity.id, entity.name, entity.image, entity.power, entity.race, entity.saga)
        }
    }
}