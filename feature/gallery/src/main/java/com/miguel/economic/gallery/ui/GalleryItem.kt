package com.miguel.economic.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.miguel.economic.core.R
import com.miguel.economic.gallery.model.ReceiptViewData

@Composable
internal fun GalleryItem(
    modifier: Modifier = Modifier,
    data: ReceiptViewData
) {
    Row(
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
            .background(Color.White)
            .then(modifier)
    ) {
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = data.photoFilename,
            contentDescription = null,
            placeholder = if (LocalInspectionMode.current) {
                rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_image_placeholder))
            } else {
                null
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.amount,
                fontSize = 32.sp
            )

            Text(
                text = data.createdDate,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun GalleryItemPreview() {
    GalleryItem(
        data = ReceiptViewData(
            id = -1,
            photoFilename = "",
            amount = "100 €",
            createdDate = "04-04-2025 10:10"
        )
    )
}