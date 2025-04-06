package com.miguel.economic.receipt

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.miguel.economic.core.R
import com.miguel.economic.core.compose.ViewEventEffect
import com.miguel.economic.core.navigation.GalleryDestination
import com.miguel.economic.core.navigation.NavigationDestination
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.receipt.model.CurrencyDialogUiState
import com.miguel.economic.receipt.model.ReceiptUiState
import com.miguel.economic.receipt.model.ReceiptViewEvent
import com.miguel.economic.receipt.ui.CurrencyDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReceiptScreen(
    args: ReceiptDestination,
    onNavigate: (NavigationDestination) -> Unit
) {
    val viewModel = koinViewModel<ReceiptViewModel> { parametersOf(args) }
    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            viewModel.onPhotoTaken()
        }
    }

    val context = LocalContext.current

    ViewEventEffect(viewModel.viewEvent) { event ->
        when (event) {
            is ReceiptViewEvent.TakePicture -> {
                takePicture.launch(event.filename.toUri())
            }

            is ReceiptViewEvent.SaveAndExit -> {
                Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                onNavigate(GalleryDestination)
            }

            is ReceiptViewEvent.Back -> {
                onNavigate(GalleryDestination)
            }

            is ReceiptViewEvent.Error -> {
                val error = context.getString(event.message)
                val message = context.getString(R.string.error_toast, error)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.background(Color.White)) {

        when (val state = uiState) {
            is ReceiptUiState.Loading -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.loading),
                    fontSize = 24.sp
                )
            }

            is ReceiptUiState.Success -> {
                val currencyDialogUiState by viewModel.currencyDialogUiState.collectAsState()

                (currencyDialogUiState as? CurrencyDialogUiState.Show)?.let { dialogUiState ->
                    Dialog(
                        onDismissRequest = viewModel::onCurrencyDialogDismiss,
                        properties = DialogProperties()
                    ) {
                        CurrencyDialog(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = Color.White),
                            onAmountChange = viewModel::onCurrencyAmountChange,
                            onCurrencyCodeChange = viewModel::onCurrencyCodeChange,
                            data = dialogUiState
                        )
                    }
                }

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

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = viewModel::onClickCurrency,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple()
                            )
                            .padding(all = 8.dp),
                        text = if (state.receipt.amount.isNotEmpty() && state.receipt.currencyCode.isNotEmpty()) {
                            state.receipt.formattedAmount
                        } else {
                            stringResource(R.string.no_currency)
                        },
                        fontSize = 32.sp
                    )

                    if (state.receipt.createdDate != null) {
                        Row(modifier = Modifier.padding(all = 8.dp)) {
                            Text(
                                text = stringResource(R.string.created),
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = state.receipt.formattedDate
                            )
                        }
                    }
                }

                Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp),
                        onClick = viewModel::onClickPhoto
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            contentDescription = null
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        onClick = viewModel::onClickSave
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}
