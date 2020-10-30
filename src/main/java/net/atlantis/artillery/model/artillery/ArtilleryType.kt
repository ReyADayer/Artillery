package net.atlantis.artillery.model.artillery

enum class ArtilleryType(val key: String) {
    BASIC("BasicArtillery"),
    EGG("EggArtillery");

    fun getArtillery(): Artillery? {
        return when (this) {
            BASIC -> {
                BasicArtillery()
            }
            EGG -> {
                EggArtillery()
            }
            else -> {
                null
            }
        }
    }

    companion object {
        fun find(key: String?): ArtilleryType? {
            return values().firstOrNull { it.key == key }
        }
    }
}