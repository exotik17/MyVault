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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myvault.data.mock.MockData
import com.example.myvault.ui.theme.BackgroundGray
import com.example.myvault.ui.theme.BorderGray
import com.example.myvault.ui.theme.PrimaryBlue
import com.example.myvault.ui.theme.SurfaceWhite
import com.example.myvault.ui.theme.TextPrimary
import com.example.myvault.ui.theme.TextSecondary

// ── Opciones de los dropdowns ──────────────────────────────────────────────

private val categories = listOf(
    "Electrónica", "Hogar", "Deportes", "Ropa",
    "Libros", "Herramientas", "Juguetes", "Otro"
)

private val zonaNames = MockData.zones.map { it.name }

// ── Pantalla de formulario ─────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemFormScreen(navController: NavController, itemId: String?) {
    val isEditMode = itemId != null
    val existingItem = if (isEditMode) MockData.getItemById(itemId!!) else null

    // ── Estado del formulario ──────────────────────────────────────────
    var name          by remember { mutableStateOf(existingItem?.name ?: "") }
    var selectedCat   by remember { mutableStateOf(existingItem?.category ?: categories.first()) }
    var selectedZone  by remember { mutableStateOf(existingItem?.zone ?: zonaNames.first()) }
    var purchaseDate  by remember { mutableStateOf(existingItem?.purchaseDate ?: "") }
    var warrantyDate  by remember { mutableStateOf(existingItem?.warrantyExpiryDate ?: "") }

    var catExpanded   by remember { mutableStateOf(false) }
    var zoneExpanded  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {

        // ── Top bar ────────────────────────────────────────────────────
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector        = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint               = TextPrimary
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text       = if (isEditMode) "Editar objeto" else "Nuevo objeto",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )
        }

        // ── Cuerpo con scroll ──────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Placeholder de foto ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceWhite)
                    .border(
                        width = 2.dp,
                        color = BorderGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { /* abrir galería — entregable 2 */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector        = Icons.Outlined.AddAPhoto,
                        contentDescription = "Agregar foto",
                        tint               = TextSecondary,
                        modifier           = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text  = "Agregar foto",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Text(
                        text  = "Toca para seleccionar",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary.copy(alpha = 0.7f)
                    )
                }
            }

            SectionLabel("Información del objeto")

            // ── Campo: Nombre ──────────────────────────────────────────
            StyledTextField(
                value       = name,
                onValueChange = { name = it },
                label       = "Nombre del objeto",
                placeholder = "ej. Auriculares Sony WH-1000XM5"
            )

            // ── Campo: Categoría (dropdown) ────────────────────────────
            ExposedDropdownMenuBox(
                expanded         = catExpanded,
                onExpandedChange = { catExpanded = !catExpanded }
            ) {
                OutlinedTextField(
                    value         = selectedCat,
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("Categoría") },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                    colors        = fieldColors(),
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded         = catExpanded,
                    onDismissRequest = { catExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text    = { Text(cat, color = TextPrimary) },
                            onClick = {
                                selectedCat  = cat
                                catExpanded  = false
                            }
                        )
                    }
                }
            }

            // ── Campo: Zona (dropdown) ─────────────────────────────────
            ExposedDropdownMenuBox(
                expanded         = zoneExpanded,
                onExpandedChange = { zoneExpanded = !zoneExpanded }
            ) {
                OutlinedTextField(
                    value         = selectedZone,
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("Zona de almacenamiento") },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = zoneExpanded) },
                    colors        = fieldColors(),
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded         = zoneExpanded,
                    onDismissRequest = { zoneExpanded = false }
                ) {
                    zonaNames.forEach { zona ->
                        DropdownMenuItem(
                            text    = { Text(zona, color = TextPrimary) },
                            onClick = {
                                selectedZone  = zona
                                zoneExpanded  = false
                            }
                        )
                    }
                }
            }

            SectionLabel("Compra y garantía")

            // ── Campo: Fecha de compra ─────────────────────────────────
            StyledTextField(
                value         = purchaseDate,
                onValueChange = { purchaseDate = it },
                label         = "Fecha de compra",
                placeholder   = "ej. 14 marzo 2023",
                trailingIcon  = {
                    Icon(
                        imageVector        = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(20.dp)
                    )
                }
            )

            // ── Campo: Fecha de vencimiento de garantía ────────────────
            StyledTextField(
                value         = warrantyDate,
                onValueChange = { warrantyDate = it },
                label         = "Vencimiento de garantía",
                placeholder   = "ej. 14 marzo 2025",
                trailingIcon  = {
                    Icon(
                        imageVector        = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(20.dp)
                    )
                }
            )

            Spacer(Modifier.height(8.dp))
        }

        // ── Botón Guardar — fijo en la parte inferior ──────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick  = { navController.popBackStack() },   // mock: solo regresa
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text       = "Guardar",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = Color.White
                )
            }
        }
    }
}

// ── Helpers de UI ──────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text       = text,
        style      = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color      = TextSecondary
    )
}

@Composable
private fun StyledTextField(
    value:         String,
    onValueChange: (String) -> Unit,
    label:         String,
    placeholder:   String,
    trailingIcon:  @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label) },
        placeholder   = { Text(placeholder, color = TextSecondary.copy(alpha = 0.6f)) },
        trailingIcon  = trailingIcon,
        singleLine    = true,
        colors        = fieldColors(),
        shape         = RoundedCornerShape(12.dp),
        modifier      = Modifier.fillMaxWidth()
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = PrimaryBlue,
    unfocusedBorderColor = BorderGray,
    focusedLabelColor    = PrimaryBlue,
    unfocusedLabelColor  = TextSecondary,
    cursorColor          = PrimaryBlue,
    focusedContainerColor   = SurfaceWhite,
    unfocusedContainerColor = SurfaceWhite
)
