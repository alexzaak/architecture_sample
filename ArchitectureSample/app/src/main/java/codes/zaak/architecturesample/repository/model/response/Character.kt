package codes.zaak.architecturesample.repository.model.response

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
    val saga: String)