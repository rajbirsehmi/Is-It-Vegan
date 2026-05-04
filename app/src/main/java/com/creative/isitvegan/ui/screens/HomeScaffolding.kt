// D:/Android/Projects/IsItVegan/app/src/main/java/com/creative/isitvegan/ui/screens/HomeScaffolding.kt

package com.creative.isitvegan.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.creative.isitvegan.ui.components.TopAppBarHome
import com.creative.isitvegan.ui.theme.IsItVeganTheme

@Composable
fun HomeScaffolding(
    searches: List<String>,
    onScanClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarHome()
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (searches.isEmpty()) {
                EmptyScreen(onScanClick = onScanClick)
            } else {
                RecentScreen()
            }
        }
    }
}

@Composable
@Preview
fun HomeScaffoldingPreview() {
    IsItVeganTheme {
        HomeScaffolding(searches = emptyList(), onScanClick = {})
    }
}
