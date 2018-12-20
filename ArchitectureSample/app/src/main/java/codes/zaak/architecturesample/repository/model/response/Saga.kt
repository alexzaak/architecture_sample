package codes.zaak.architecturesample.repository.model.response

import codes.zaak.architecturesample.repository.model.entity.SagaEntity
import com.squareup.moshi.Json

data class Saga(
    val id: Int,
    @Json(name = "nm_saga")
    val name: String,
    @Json(name = "ds_saga")
    val description: String,
    @Json(name = "img_saga")
    val image: String
) {
    companion object {
        fun create(entity: SagaEntity): Saga {
            return Saga(entity.id, entity.name, entity.description, entity.image)
        }
    }
}