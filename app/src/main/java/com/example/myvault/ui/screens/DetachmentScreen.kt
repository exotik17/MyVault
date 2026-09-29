package com.example.myvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myvault.data.mock.InactiveZone
import com.example.myvault.data.mock.Item
import com.example.myvault.data.mock.MockData
import com.example.myvault.navigation.Screen
import com.example.myvault.ui.theme.AlertAmber
import com.example.myvault.ui.theme.AlertAmberBg
import com.example.myvault.ui.theme.BackgroundGray
import com.example.myvault.ui.theme.BorderGray
import com.example.myvault.ui.theme.PrimaryBlue
import com.example.myvault.ui.theme.PrimaryBlueLight
import com.example.myvault.ui.theme.SurfaceWhite
import com.example.myvault.ui.theme.TextPrimary
import com.example.myvault.ui.theme.TextSecondary

// ── Pantalla de desapego ───────────────────────────────────────────────────

@Composable
fun DetachmentScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {

        // ── Encabezado personalizado ───────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite)
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.padding(horizontal = 4.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector        = Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint               = TextPrimary
                    )
                }
                Spacer(Modifier.width(4.dp))
                Column {
                    Text(
                        text       = "Revisión mensual",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary
                    )
                    Text(
                        text  = "Septiembre 2026",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        // ── Contenido con scroll ───────────────────────────────────────
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Banner de alerta ámbar
            item {
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .background(AlertAmberBg, RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint               = AlertAmber,
                        modifier           = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text  = "Encontramos 5 objetos y 2 zonas sin revisar en los últimos meses.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AlertAmber
                    )
                }
            }

            // ── Tabs personalizados ────────────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(SurfaceWhite, RoundedCornerShape(12.dp))
                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TabButton(
                        text     = "Objetos sin usar",
                        selected = selectedTab == 0,
                        modifier = Modifier.weight(1f),
                        onClick  = { selectedTab = 0 }
                    )
                    TabButton(
                        text     = "Zonas sin visitar",
                        selected = selectedTab == 1,
                        modifier = Modifier.weight(1f),
                        onClick  = { selectedTab = 1 }
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            // ── Contenido del tab activo ───────────────────────────────
            if (selectedTab == 0) {
                items(MockData.inactiveItems) { item ->
                    DetachmentItemCard(
                        item     = item,
                        onClick  = { navController.navigate(Screen.ItemDetail.createRoute(item.id)) }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            } else {
                items(MockData.inactiveZones) { zone ->
                    InactiveZoneCard(zone = zone)
                    Spacer(Modifier.height(8.dp))
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

// ── Botón de tab personalizado ─────────────────────────────────────────────

@Composable
private fun TabButton(
    text:     String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick:  () -> Unit
) {
    Box(
        modifier         = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) PrimaryBlue else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = text,
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color      = if (selected) Color.White else TextSecondary
        )
    }
}

// ── Tarjeta de objeto inactivo ─────────────────────────────────────────────

@Composable
private fun DetachmentItemCard(item: Item, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(SurfaceWhite, RoundedCornerShape(14.dp))
            .border(1.dp, BorderGray, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {

            // Imagen placeholder
            Box(
                modifier         = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(item.imagePlaceholderColor)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = categoryIcon(item.category),
                    contentDescription = null,
                    tint               = Color.White.copy(alpha = 0.8f),
                    modifier           = Modifier.size(30.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = item.name,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text  = item.zone,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                Spacer(Modifier.height(6.dp))
                // Badge ámbar de inactividad
                Row(
                    modifier          = Modifier
                        .background(AlertAmberBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.AccessTime,
                        contentDescription = null,
                        tint               = AlertAmber,
                        modifier           = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text  = "Sin usar hace ${item.lastUsedMonthsAgo} meses",
                        style = MaterialTheme.typography.labelSmall,
                        color = AlertAmber
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        HorizontalDivider(color = BorderGray)

        // Botones de acción rápida
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { /* donar — entregable 2 */ }) {
                Text("Donar", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(32.dp)
                    .align(Alignment.CenterVertically)
                    .background(BorderGray)
            )
            TextButton(onClick = { /* vender — entregable 2 */ }) {
                Text("Vender", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(32.dp)
                    .align(Alignment.CenterVertically)
                    .background(BorderGray)
            )
            TextButton(onClick = { /* conservar — entregable 2 */ }) {
                Text("Conservar", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

// ── Tarjeta de zona inactiva ───────────────────────────────────────────────

@Composable
private fun InactiveZoneCard(zone: InactiveZone) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(SurfaceWhite, RoundedCornerShape(14.dp))
            .border(1.dp, BorderGray, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier         = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryBlueLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = zoneIcon(zone.iconName),
                    contentDescription = null,
                    tint               = PrimaryBlue,
                    modifier           = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = zone.name,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Text(
                    text  = "${zone.itemCount} objetos",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier          = Modifier
                        .background(AlertAmberBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.AccessTime,
                        contentDescription = null,
                        tint               = AlertAmber,
                        modifier           = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text  = "Sin visitar hace ${zone.monthsInactive} meses",
                        style = MaterialTheme.typography.labelSmall,
                        color = AlertAmber
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        HorizontalDivider(color = BorderGray)

        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { /* visitar */ }) {
                Text("Visitar", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(32.dp)
                    .align(Alignment.CenterVertically)
                    .background(BorderGray)
            )
            TextButton(onClick = { /* donar todo */ }) {
                Text("Donar todo", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(32.dp)
                    .align(Alignment.CenterVertically)
                    .background(BorderGray)
            )
            TextButton(onClick = { /* ignorar */ }) {
                Text("Ignorar", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
