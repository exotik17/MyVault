package com.example.myvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myvault.data.mock.Item
import com.example.myvault.data.mock.MockData
import com.example.myvault.navigation.Screen
import com.example.myvault.ui.theme.AlertAmber
import com.example.myvault.ui.theme.AlertAmberBg
import com.example.myvault.ui.theme.BackgroundGray
import com.example.myvault.ui.theme.BorderGray
import com.example.myvault.ui.theme.PrimaryBlue
import com.example.myvault.ui.theme.PrimaryBlueLight
import com.example.myvault.ui.theme.SuccessGreen
import com.example.myvault.ui.theme.SuccessGreenBg
import com.example.myvault.ui.theme.SurfaceWhite
import com.example.myvault.ui.theme.TextPrimary
import com.example.myvault.ui.theme.TextSecondary

// ── Pantalla de detalle ────────────────────────────────────────────────────

@Composable
fun ItemDetailScreen(navController: NavController, itemId: String) {
    val item = MockData.getItemById(itemId) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Top bar ────────────────────────────────────────────────────
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector        = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint               = TextPrimary
                )
            }
            Text(
                text       = "Detalle de objeto",
                style      = MaterialTheme.typography.titleMedium,
                color      = TextPrimary
            )
            IconButton(onClick = { /* menú contextual — entregable 2 */ }) {
                Icon(
                    imageVector        = Icons.Outlined.MoreVert,
                    contentDescription = "Opciones",
                    tint               = TextPrimary
                )
            }
        }

        // ── Imagen / placeholder de foto ───────────────────────────────
        Box(
            modifier         = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(item.imagePlaceholderColor)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = categoryIcon(item.category),
                contentDescription = null,
                tint               = Color.White.copy(alpha = 0.75f),
                modifier           = Modifier.size(72.dp)
            )
        }

        // ── Card de información ────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            // Nombre
            Text(
                text       = item.name,
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            // Chip de categoría
            Box(
                modifier = Modifier
                    .background(PrimaryBlueLight, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text  = item.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = PrimaryBlue
                )
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = BorderGray)
            Spacer(Modifier.height(16.dp))

            // Filas de información
            InfoRow(label = "Ubicación", value = "${item.zone} · ${item.subZone}")
            Spacer(Modifier.height(10.dp))
            InfoRow(label = "Compra",    value = item.purchaseDate)
            Spacer(Modifier.height(10.dp))
            InfoRow(label = "Precio",    value = item.price)

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = BorderGray)
            Spacer(Modifier.height(16.dp))

            // ── Sección de garantía ────────────────────────────────────
            WarrantySection(item)

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = BorderGray)
            Spacer(Modifier.height(16.dp))

            // ── Factura adjunta ────────────────────────────────────────
            if (item.invoiceFileName != null) {
                InvoiceRow(item)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = BorderGray)
                Spacer(Modifier.height(16.dp))
            }

            // ── Barra de acciones ──────────────────────────────────────
            BottomActionBar(
                onEdit     = { navController.navigate(Screen.ItemForm.createEditRoute(item.id)) },
                onMarkUsed = { /* lógica entregable 2 */ },
                onDelete   = { /* lógica entregable 2 */ }
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}

// ── Fila label / valor ─────────────────────────────────────────────────────

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
        Text(
            text       = value,
            style      = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color      = TextPrimary
        )
    }
}

// ── Sección de garantía ────────────────────────────────────────────────────

@Composable
private fun WarrantySection(item: Item) {
    val statusColor = if (item.warrantyActive) SuccessGreen else TextSecondary
    val statusBg    = if (item.warrantyActive) SuccessGreenBg else BackgroundGray
    val statusLabel = if (item.warrantyActive) "✓ Activa" else "✗ Vencida"

    Column {
        // Encabezado con badge de estado
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = Icons.Outlined.Security,
                    contentDescription = null,
                    tint               = statusColor,
                    modifier           = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "Garantía del fabricante",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
            }
            Box(
                modifier = Modifier
                    .background(statusBg, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text  = statusLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = statusColor
                )
            }
        }

        // Detalle de vencimiento
        if (item.warrantyActive && item.warrantyDaysLeft > 0) {
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SuccessGreenBg, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text  = "Vencimiento",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text       = "${item.warrantyExpiryDate} · ${item.warrantyDaysLeft} días restantes",
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = SuccessGreen
                )
            }
        } else {
            Spacer(Modifier.height(6.dp))
            Text(
                text  = "Vencida el ${item.warrantyExpiryDate}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

// ── Fila de factura adjunta ────────────────────────────────────────────────

@Composable
private fun InvoiceRow(item: Item) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .background(BackgroundGray, RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector        = Icons.Outlined.InsertDriveFile,
            contentDescription = null,
            tint               = PrimaryBlue,
            modifier           = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = item.invoiceFileName ?: "",
                style      = MaterialTheme.typography.labelLarge,
                color      = TextPrimary
            )
            Text(
                text  = "${item.invoiceSize} · Agregado ${item.invoiceDate}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        Spacer(Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier          = Modifier
                .background(PrimaryBlueLight, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector        = Icons.Outlined.Visibility,
                contentDescription = "Ver factura",
                tint               = PrimaryBlue,
                modifier           = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text  = "Ver",
                style = MaterialTheme.typography.labelMedium,
                color = PrimaryBlue
            )
        }
    }
}

// ── Barra inferior de acciones ─────────────────────────────────────────────

@Composable
private fun BottomActionBar(
    onEdit:     () -> Unit,
    onMarkUsed: () -> Unit,
    onDelete:   () -> Unit
) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Botón Editar
        OutlinedButton(
            onClick = onEdit,
            modifier = Modifier.weight(1f),
            shape    = RoundedCornerShape(12.dp),
            border   = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Icon(
                imageVector        = Icons.Outlined.Edit,
                contentDescription = null,
                modifier           = Modifier.size(16.dp),
                tint               = TextPrimary
            )
            Spacer(Modifier.width(6.dp))
            Text("Editar", color = TextPrimary)
        }

        // Botón Marcar como usado
        Button(
            onClick  = onMarkUsed,
            modifier = Modifier.weight(1.4f),
            shape    = RoundedCornerShape(12.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Icon(
                imageVector        = Icons.Outlined.AccessTime,
                contentDescription = null,
                modifier           = Modifier.size(16.dp),
                tint               = Color.White
            )
            Spacer(Modifier.width(6.dp))
            Text("Marcar usado", color = Color.White)
        }

        // Botón Eliminar
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(AlertAmberBg, RoundedCornerShape(12.dp))
                .border(1.dp, AlertAmber.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector        = Icons.Outlined.Delete,
                    contentDescription = "Eliminar",
                    tint               = AlertAmber,
                    modifier           = Modifier.size(20.dp)
                )
            }
        }
    }
}
