package com.example.antheia_plant_manager.screens.account_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.antheia_plant_manager.R
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun AccountSettingsScreen() {
   AccountSettingsScreenCompact()
}

@Composable
fun AccountSettingsScreenCompact(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .safeContentPadding()
            .fillMaxWidth()
    ) {
        Text(
            text = "Account Settings",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
fun AccountDetail(
    accountDetailTitle: String,
    accountDetail: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .safeContentPadding()
            .background(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)),
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .border(
                width = dimensionResource(id = R.dimen.small_padding),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.large_padding),
                vertical = dimensionResource(id = R.dimen.medium_padding)
            )
    ) {
        Text(
            text = accountDetailTitle,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(text = accountDetail, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = modifier.weight(1f))
            TextButton(
                onClick = { onDetailClick() },
            ) {
                Text(
                    text = "Edit ${accountDetailTitle.lowercase()}",
                )
            }
        }
    }

}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
fun AccountDetailPreview() {
    AntheiaplantmanagerTheme() {
        AccountDetail(
            accountDetailTitle = "Detail",
            accountDetail = "current Detail",
            onDetailClick = {}
        )
    }
}
@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
fun AccountSettingsScreenCompactPreview() {
    AntheiaplantmanagerTheme() {
        AccountSettingsScreenCompact()
    }
}
