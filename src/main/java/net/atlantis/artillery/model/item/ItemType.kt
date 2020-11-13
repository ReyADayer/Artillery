package net.atlantis.artillery.model.item

enum class ItemType(val key: String) {
    REMOVER("Remover");

    fun getItem(): Item {
        return when (this) {
            REMOVER -> Remover()
        }
    }

    companion object {
        fun findByKey(key: String?): ItemType? {
            return values().firstOrNull { it.key == key }
        }
    }
}