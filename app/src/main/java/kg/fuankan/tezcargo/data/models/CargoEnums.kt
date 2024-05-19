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

enum class AccountingType {
    TOTAL_DELIVERED,
    BEST_DRIVERS,
    DELIVERIES_TOTAL_AMOUNT
}