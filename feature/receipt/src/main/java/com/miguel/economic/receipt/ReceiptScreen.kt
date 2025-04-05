package com.miguel.economic.receipt

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.miguel.economic.core.R
import com.miguel.economic.core.compose.ViewEventEffect
import com.miguel.economic.core.navigation.GalleryDestination
import com.miguel.economic.core.navigation.NavigationDestination
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.receipt.model.ReceiptUiState
import com.miguel.economic.receipt.model.ReceiptViewEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReceiptScreen(
    args: ReceiptDestination,
    onNavigate: (NavigationDestination) -> Unit
) {
    val viewModel = koinViewModel<ReceiptViewModel> { parametersOf(args) }
    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture(), viewModel::onPhotoTaken)

    ViewEventEffect(viewModel.viewEvent) { event ->
        when (event) {
            is ReceiptViewEvent.TakePicture -> {
                takePicture.launch(event.filename.toUri())
            }

            is ReceiptViewEvent.SaveAndExit, is ReceiptViewEvent.Back -> {
                onNavigate(GalleryDestination)
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ReceiptUiState.Loading -> {

        }

        is ReceiptUiState.Success -> {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box {
                        AsyncImage(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                            model = state.receipt.photoFilename,
                            contentDescription = null,
                            fallback = painterResource(R.drawable.ic_image_placeholder),
                        )

                        Icon(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .clickable(
                                    onClick = viewModel::onClickBack,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(bounded = false)
                                )
                                .padding(all = 8.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                    val items = remember(state.receipt) {
                        listOf(
                            "Amount" to state.receipt.amount?.toString().orEmpty(),
                            "Currency" to state.receipt.currencyCode.orEmpty(),
                            "Created" to state.receipt.createdDate.orEmpty()
                        )
                    }

                    for ((title, value) in items) {
                        DataItem(
                            modifier = Modifier.padding(all = 8.dp),
                            title = title,
                            value = value
                        )
                    }
                }


                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = viewModel::onClickPhoto
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null
                    )
                }
            }
        }
    }


}

@Composable
private fun DataItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Row(modifier) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = value
        )
    }
}