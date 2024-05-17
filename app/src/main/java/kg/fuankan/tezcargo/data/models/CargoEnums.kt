package kg.fuankan.tezcargo.data.models

enum class Role {
    ADMIN,
    DRIVER
}

enum class DeliveryStatus(val status: String) {
    CURRENT("В пути"),
    WAITING("В ожидании"),
    CANCELED("Отменен"),
    DELIVERED("Доставлен")
}