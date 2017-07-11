package ru.spbau.shavkunov.server.data

enum class AmountType(private val value: String) {
    GR("gr"),
    KG("kg"),
    PIECE("piece(-s)"),
    TBSP("tpsp"),
    ML("ml");

    fun getValue(): String {
        return value
    }

    override fun toString(): String {
        return this.getValue()
    }

    companion object {
        fun getEnum(value: String): AmountType {
            values()
                    .filter { it.getValue().toLowerCase() == value.toLowerCase() }
                    .forEach { return it }

            throw IllegalArgumentException()
        }
    }
}