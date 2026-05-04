@file:OptIn(ExperimentalMaterial3Api::class)

package com.creative.isitvegan.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopAppBarHome() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Is It Vegan?",
                style = typography.headlineMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.testTag("top_bar_title")
            )
        }
    )
}

@Composable
@Preview
fun TopAppBarHomePreview() {
    TopAppBarHome()
}
