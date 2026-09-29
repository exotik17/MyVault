package com.example.myvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Warehouse
import androidx.compose.material.icons.outlined.Yard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myvault.data.mock.Item
import com.example.myvault.data.mock.MockData
import com.example.myvault.data.mock.Zone
import com.example.myvault.navigation.Screen
import com.example.myvault.ui.theme.BackgroundGray
import com.example.myvault.ui.theme.BorderGray
import com.example.myvault.ui.theme.PrimaryBlue
import com.example.myvault.ui.theme.PrimaryBlueLight
import com.example.myvault.ui.theme.SurfaceWhite
import com.example.myvault.ui.theme.TextPrimary
import com.example.myvault.ui.theme.TextSecondary

// ── Pantalla principal ────────────────────────────────────────────────────

@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(scrollState)
    ) {

        // ── Top bar ──────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text  = "Jueves, 17 sep",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text       = "Hola, Lucía 👋",
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )
            }
            // Campana — navegación al módulo de desapego
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SurfaceWhite)
                    .border(1.dp, BorderGray, CircleShape)
                    .clickable { navController.navigate(Screen.Detachment.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Filled.Notifications,
                    contentDescription = "Ver módulo de desapego",
                    tint               = TextPrimary,
                    modifier           = Modifier.size(22.dp)
                )
            }
        }

        // ── Buscador ──────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(SurfaceWhite, RoundedCornerShape(12.dp))
                .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = Icons.Outlined.Search,
                contentDescription = null,
                tint               = TextSecondary,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text  = "Buscar objetos, zonas...",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Objetos recientes — encabezado ─────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text       = "Objetos recientes",
                style      = MaterialTheme.typography.titleMedium,
                color      = TextPrimary
            )
            Text(
                text       = "Ver todos",
                style      = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color      = PrimaryBlue
            )
        }

        Spacer(Modifier.height(12.dp))

        // ── Scroll horizontal de objetos ──────────────────────────────
        LazyRow(
            contentPadding      = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.recentItems) { item ->
                RecentItemCard(item = item) {
                    navController.navigate(Screen.ItemDetail.createRoute(item.id))
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        // ── Zonas — encabezado ────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text  = "Zonas",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text       = "Gestionar",
                style      = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color      = PrimaryBlue
            )
        }

        Spacer(Modifier.height(12.dp))

        // ── Grid 2 columnas de zonas ──────────────────────────────────
        Column(
            modifier  = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MockData.zones.chunked(2).forEach { rowZones ->
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowZones.forEach { zone ->
                        ZoneCard(
                            zone     = zone,
                            modifier = Modifier.weight(1f),
                            onClick  = { /* navegar a detalle de zona — entregable 2 */ }
                        )
                    }
                    if (rowZones.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }

        // Espaciado extra para que el FAB no tape el último ítem
        Spacer(Modifier.height(88.dp))
    }
}

// ── Tarjeta de objeto reciente ─────────────────────────────────────────────

@Composable
private fun RecentItemCard(item: Item, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .width(130.dp)
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border    = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // Imagen / placeholder
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(item.imagePlaceholderColor)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = categoryIcon(item.category),
                    contentDescription = null,
                    tint               = Color.White.copy(alpha = 0.85f),
                    modifier           = Modifier.size(36.dp)
                )
            }
            // Info
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text     = item.name,
                    style    = MaterialTheme.typography.labelLarge,
                    color    = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(12.dp)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text     = item.zone,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// ── Tarjeta de zona ────────────────────────────────────────────────────────

@Composable
private fun ZoneCard(zone: Zone, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier  = modifier.clickable(onClick = onClick),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border    = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier          = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de zona con fondo azul claro
            Box(
                modifier         = Modifier
                    .size(42.dp)
                    .background(PrimaryBlueLight, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = zoneIcon(zone.iconName),
                    contentDescription = null,
                    tint               = PrimaryBlue,
                    modifier           = Modifier.size(22.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text       = zone.name,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Text(
                    text  = "${zone.itemCount} objetos",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

// ── Mapeo categoría → ícono ────────────────────────────────────────────────

fun categoryIcon(category: String): ImageVector = when (category) {
    "Electrónica" -> Icons.Outlined.Devices
    "Hogar"       -> Icons.Outlined.Kitchen
    "Deportes"    -> Icons.Outlined.FitnessCenter
    "Ropa"        -> Icons.Outlined.Checkroom
    else          -> Icons.Outlined.Inventory2
}

// ── Mapeo zona → ícono ─────────────────────────────────────────────────────

fun zoneIcon(iconName: String): ImageVector = when (iconName) {
    "checkroom"    -> Icons.Outlined.Checkroom
    "inventory"    -> Icons.Outlined.Inventory2
    "directions_car" -> Icons.Outlined.DirectionsCar
    "kitchen"      -> Icons.Outlined.Kitchen
    "warehouse"    -> Icons.Outlined.Warehouse
    "yard"         -> Icons.Outlined.Yard
    else           -> Icons.Outlined.Inventory2
}
