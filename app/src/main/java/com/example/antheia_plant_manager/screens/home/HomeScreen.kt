package com.example.antheia_plant_manager.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.data.mock.PlantRepositoryImplMock
import com.example.antheia_plant_manager.model.service.mock.AccountServiceMock
import com.example.antheia_plant_manager.util.cardColor
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    LocationListCompact()
}

@Composable
fun LocationListCompact(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val locationList by viewModel.locationsList.collectAsState()
    if(locationList.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = dimensionResource(id = R.dimen.dialog_padding),
                bottom = dimensionResource(id = R.dimen.dialog_padding),
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dialog_padding))
        ) {
            itemsIndexed(items = locationList) { index, location ->
                LocationCard(
                    index = index,
                    locationName = location
                )
            }
        }
    } else {
        EmptyPlantList()
    }
}


@Composable
fun LocationCard(
    index: Int,
    locationName: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = cardColor(index),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_padding)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dialog_padding))
            .height(dimensionResource(id = R.dimen.location_card_height))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = locationName,
                maxLines = 1,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.displayLarge,
            )
        }
    }
}

@Composable
fun EmptyPlantList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_plants_found_would_you_like_to_add_some),
            textAlign = TextAlign.Center,
            modifier = modifier
                .alpha(0.5F)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PlantsScreenPreview() {
    AntheiaplantmanagerTheme(darkTheme = true) {
        LocationListCompact(viewModel = HomeViewModel(
            plantDatabase = PlantRepositoryImplMock(),
            accountService = AccountServiceMock()
        ))
    }
}
