package codes.zaak.architecturesample.repository.model

import com.squareup.moshi.Json

data class Saga(
    val id: Int,
    @Json(name = "nm_saga")
    val name: String,
    val characters: List<Character>,
    @Json(name = "ds_saga")
    val description: String,
    @Json(name = "img_saga")
    val image: String)