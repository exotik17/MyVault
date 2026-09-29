package com.example.myvault.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myvault.ui.screens.DetachmentScreen
import com.example.myvault.ui.screens.HomeScreen
import com.example.myvault.ui.screens.ItemDetailScreen
import com.example.myvault.ui.screens.ItemFormScreen
import com.example.myvault.ui.theme.AlertAmber
import com.example.myvault.ui.theme.BackgroundGray
import com.example.myvault.ui.theme.PrimaryBlue
import com.example.myvault.ui.theme.PrimaryBlueLight
import com.example.myvault.ui.theme.SurfaceWhite
import com.example.myvault.ui.theme.TextSecondary

// ── Rutas de navegación ───────────────────────────────────────────────────

sealed class Screen(val route: String) {
    object Home       : Screen("home")
    object Detachment : Screen("detachment")
    object Search     : Screen("search")
    object Profile    : Screen("profile")

    object ItemDetail : Screen("item_detail/{itemId}") {
        fun createRoute(itemId: String) = "item_detail/$itemId"
    }

    /**
     * Formulario con parámetro opcional:
     *  - Nuevo objeto  → navController.navigate(ItemForm.base)
     *  - Editar objeto → navController.navigate(ItemForm.createEditRoute(id))
     */
    object ItemForm : Screen("item_form?itemId={itemId}") {
        const val base = "item_form"
        fun createEditRoute(itemId: String) = "item_form?itemId=$itemId"
    }
}

// ── Modelo del ítem de navegación inferior ────────────────────────────────

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

// ── Composable raíz de la app ─────────────────────────────────────────────

@Composable
fun MyVaultApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Rutas donde se muestra la barra inferior
    val bottomBarRoutes = setOf(
        Screen.Home.route,
        Screen.Detachment.route,
        Screen.Search.route,
        Screen.Profile.route
    )
    val showBottomBar = currentRoute in bottomBarRoutes

    val bottomNavItems = listOf(
        BottomNavItem(Screen.Home.route,       "Inicio",   Icons.Filled.Home),
        BottomNavItem(Screen.Search.route,     "Buscar",   Icons.Filled.Search),
        BottomNavItem(Screen.Detachment.route, "Desapego", Icons.Filled.Inventory2),
        BottomNavItem(Screen.Profile.route,    "Perfil",   Icons.Filled.Person)
    )

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            if (showBottomBar) {
                MyVaultBottomBar(
                    navController = navController,
                    currentRoute  = currentRoute,
                    items         = bottomNavItems
                )
            }
        },
        floatingActionButton = {
            if (currentRoute == Screen.Home.route) {
                FloatingActionButton(
                    onClick        = { navController.navigate(Screen.ItemForm.base) },
                    containerColor = PrimaryBlue,
                    contentColor   = Color.White,
                    shape          = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar objeto")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController    = navController,
            startDestination = Screen.Home.route,
            modifier         = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(Screen.Detachment.route) {
                DetachmentScreen(navController)
            }

            composable(Screen.Search.route) {
                PlaceholderScreen("Buscar")
            }

            composable(Screen.Profile.route) {
                PlaceholderScreen("Perfil")
            }

            composable(
                route     = Screen.ItemDetail.route,
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { backStack ->
                val itemId = backStack.arguments?.getString("itemId") ?: ""
                ItemDetailScreen(navController, itemId)
            }

            composable(
                route     = Screen.ItemForm.route,
                arguments = listOf(
                    navArgument("itemId") {
                        type         = NavType.StringType
                        nullable     = true
                        defaultValue = null
                    }
                )
            ) { backStack ->
                val itemId = backStack.arguments?.getString("itemId")
                ItemFormScreen(navController, itemId)
            }
        }
    }
}

// ── Bottom Navigation Bar ─────────────────────────────────────────────────

@Composable
fun MyVaultBottomBar(
    navController: NavController,
    currentRoute:  String?,
    items:         List<BottomNavItem>
) {
    NavigationBar(
        containerColor  = SurfaceWhite,
        tonalElevation  = 0.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector     = item.icon,
                        contentDescription = item.label
                    )
                },
                label   = { Text(item.label, fontSize = 11.sp) },
                selected = selected,
                onClick  = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = PrimaryBlue,
                    selectedTextColor   = PrimaryBlue,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = PrimaryBlueLight
                )
            )
        }
    }
}

// ── Pantalla placeholder para tabs sin implementar ────────────────────────

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier        = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text      = "$title\n(Próximamente)",
            style     = MaterialTheme.typography.titleLarge,
            color     = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}
