package com.example.antheia_plant_manager.screens.locations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.cardColor
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun LocationScreen() {
    LocationListCompact()
}

@Composable
fun LocationListCompact() {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(3) {
            LocationCard(index = it)
        }
    }
}


@Composable
fun LocationCard(
    index: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = cardColor(index),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_padding)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(200.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Backyard",
                maxLines = 1,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.displayLarge,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlantsScreenPreview() {
    AntheiaplantmanagerTheme(darkTheme = true) {
        LocationScreen()
    }
}
