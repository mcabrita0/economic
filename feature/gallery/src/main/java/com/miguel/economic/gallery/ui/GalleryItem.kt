package com.miguel.economic.gallery.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    Row(modifier = modifier.height(120.dp)) {
        AsyncImage(
            modifier = Modifier.width(120.dp),
            model = data.photoFilename,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_image_placeholder),
            error = painterResource(R.drawable.ic_image_placeholder)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = data.amount,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 4.dp)
                    .align(Alignment.End),
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