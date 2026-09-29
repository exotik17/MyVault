package com.example.myvault.data.mock

// ── Modelos de datos ──────────────────────────────────────────────────────

data class Item(
    val id: String,
    val name: String,
    val category: String,
    val zone: String,
    val subZone: String,
    val purchaseDate: String,
    val warrantyExpiryDate: String,
    val warrantyDaysLeft: Int,
    val warrantyActive: Boolean,
    val price: String,
    val invoiceFileName: String?,
    val invoiceSize: String?,
    val invoiceDate: String?,
    val lastUsedMonthsAgo: Int = 0,
    /** Color ARGB en Long usado como fondo del placeholder de imagen */
    val imagePlaceholderColor: Long = 0xFFE8F1FB
)

data class Zone(
    val id: String,
    val name: String,
    val itemCount: Int,
    /** Clave para mapear a un ImageVector en la UI (ver IconUtils.kt) */
    val iconName: String
)

data class InactiveZone(
    val id: String,
    val name: String,
    val monthsInactive: Int,
    val itemCount: Int,
    val iconName: String
)

// ── Datos de ejemplo ──────────────────────────────────────────────────────

object MockData {

    val items = listOf(
        Item(
            id = "1",
            name = "Auriculares Sony WH-1000XM5",
            category = "Electrónica",
            zone = "Escritorio",
            subZone = "Cajón derecho",
            purchaseDate = "14 marzo 2023",
            warrantyExpiryDate = "14 marzo 2025",
            warrantyDaysLeft = 179,
            warrantyActive = true,
            price = "$349.00 USD",
            invoiceFileName = "Factura_Sony_2023.pdf",
            invoiceSize = "234 KB",
            invoiceDate = "14 mar 2023",
            lastUsedMonthsAgo = 2,
            imagePlaceholderColor = 0xFFFFCC00L   // amarillo — fiel a la imagen de referencia
        ),
        Item(
            id = "2",
            name = "Cafetera Nespresso",
            category = "Hogar",
            zone = "Cocina",
            subZone = "Mesón principal",
            purchaseDate = "3 julio 2023",
            warrantyExpiryDate = "3 julio 2024",
            warrantyDaysLeft = 0,
            warrantyActive = false,
            price = "$189.00 USD",
            invoiceFileName = "Factura_Nespresso.pdf",
            invoiceSize = "178 KB",
            invoiceDate = "3 jul 2023",
            lastUsedMonthsAgo = 0,
            imagePlaceholderColor = 0xFF5D4037L   // café oscuro
        ),
        Item(
            id = "3",
            name = "Cámara Fujifilm X-T5",
            category = "Electrónica",
            zone = "Clóset principal",
            subZone = "Estante superior",
            purchaseDate = "20 noviembre 2022",
            warrantyExpiryDate = "20 noviembre 2024",
            warrantyDaysLeft = 0,
            warrantyActive = false,
            price = "\$1,699.00 USD",
            invoiceFileName = "Factura_Fujifilm.pdf",
            invoiceSize = "312 KB",
            invoiceDate = "20 nov 2022",
            lastUsedMonthsAgo = 4,
            imagePlaceholderColor = 0xFF37474FL   // gris azulado oscuro
        ),
        Item(
            id = "4",
            name = "Patineta eléctrica",
            category = "Deportes",
            zone = "Bodega",
            subZone = "Pared izquierda",
            purchaseDate = "12 enero 2023",
            warrantyExpiryDate = "12 enero 2024",
            warrantyDaysLeft = 0,
            warrantyActive = false,
            price = "$450.00 USD",
            invoiceFileName = null,
            invoiceSize = null,
            invoiceDate = null,
            lastUsedMonthsAgo = 8,
            imagePlaceholderColor = 0xFF7B3F8CL   // púrpura
        ),
        Item(
            id = "5",
            name = "Impresora HP Envy",
            category = "Electrónica",
            zone = "Cuarto útil",
            subZone = "Mesa esquinera",
            purchaseDate = "5 octubre 2022",
            warrantyExpiryDate = "5 octubre 2023",
            warrantyDaysLeft = 0,
            warrantyActive = false,
            price = "$279.00 USD",
            invoiceFileName = "Factura_HP.pdf",
            invoiceSize = "156 KB",
            invoiceDate = "5 oct 2022",
            lastUsedMonthsAgo = 11,
            imagePlaceholderColor = 0xFF185FA5L   // azul HP
        ),
        Item(
            id = "6",
            name = "Máquina de coser",
            category = "Hogar",
            zone = "Clóset principal",
            subZone = "Estante inferior",
            purchaseDate = "18 agosto 2021",
            warrantyExpiryDate = "18 agosto 2023",
            warrantyDaysLeft = 0,
            warrantyActive = false,
            price = "$320.00 USD",
            invoiceFileName = null,
            invoiceSize = null,
            invoiceDate = null,
            lastUsedMonthsAgo = 14,
            imagePlaceholderColor = 0xFFD4A373L   // beige cálido
        )
    )

    val zones = listOf(
        Zone("z1", "Clóset principal", 48, "checkroom"),
        Zone("z2", "Cuarto útil",      32, "inventory"),
        Zone("z3", "Baúl del carro",   14, "directions_car"),
        Zone("z4", "Despensa",         27, "kitchen"),
        Zone("z5", "Bodega",           61, "warehouse"),
        Zone("z6", "Terraza",           9, "yard")
    )

    /** Tres objetos más recientes para el scroll horizontal del Home */
    val recentItems: List<Item> = items.take(3)

    /** Objetos sin usar ≥ 6 meses, ordenados de mayor a menor inactividad */
    val inactiveItems: List<Item> = items
        .filter { it.lastUsedMonthsAgo >= 6 }
        .sortedByDescending { it.lastUsedMonthsAgo }

    /** Zonas que llevan meses sin visitarse */
    val inactiveZones = listOf(
        InactiveZone("z5", "Bodega",          7, 61, "warehouse"),
        InactiveZone("z3", "Baúl del carro",  4, 14, "directions_car")
    )

    fun getItemById(id: String): Item? = items.find { it.id == id }
}
