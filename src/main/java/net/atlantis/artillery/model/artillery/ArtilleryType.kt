package net.atlantis.artillery.model.artillery

enum class ArtilleryType(val key: String) {
    ARROW("ArrowArtillery"),
    BASIC("BasicArtillery"),
    EGG("EggArtillery"),
    LASER("LaserArtillery");

    fun getArtillery(): Artillery? {
        return when (this) {
            ARROW -> {
                ArrowArtillery()
            }
            BASIC -> {
                BasicArtillery()
            }
            EGG -> {
                EggArtillery()
            }
            LASER -> {
                LaserArtillery()
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