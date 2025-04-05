package com.miguel.economic.receipt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.receipt.model.CurrencyDialogUiState

@Composable
internal fun CurrencyDialog(
    modifier: Modifier = Modifier,
    onAmountChange: (String) -> Unit,
    onCurrencyCodeChange: (String) -> Unit,
    data: CurrencyDialogUiState.Show
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            text = "Amount"
        )

        TextField(
            modifier = Modifier
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            value = data.amount,
            onValueChange = onAmountChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
        ) {
            for (code in data.currencyCodes) {
                val backgroundColor = remember(data.currencyCode) {
                    if (data.currencyCode == code) {
                        Color.Gray
                    } else {
                        Color.LightGray
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundColor)
                        .clickable { onCurrencyCodeChange(code) }
                        .padding(all = 8.dp),
                    text = code
                )
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyDialogPreview() {
    CurrencyDialog(
        modifier = Modifier.background(Color.White),
        data = remember {
            CurrencyDialogUiState.Show(
                currencyCodes = CurrencyUtil.allCurrencyCodes(),
                currencyCode = "ADP",
                amount = "1.20",
            )
        },
        onAmountChange = {},
        onCurrencyCodeChange = {},
    )
}