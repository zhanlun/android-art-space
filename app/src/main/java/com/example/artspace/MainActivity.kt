package com.example.artspace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

data class ImageType(
    val imageResourceId: Int,
    val title: String,
    val author: String,
    val year: String,
)

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    val imagesData: List<ImageType> = listOf(
        ImageType(R.drawable.jojo1, "Jojo's playing", "Lonlon", "2022"),
        ImageType(R.drawable.jojo2, "Jojo's chilling", "Lonlon", "2022"),
        ImageType(R.drawable.jojo3, "Jojo's sleeping", "Lonlon", "2022"),
        ImageType(R.drawable.jojo4, "Jojo's rolling", "Lonlon", "2022"),
    )

    var imageCursor by remember {
        mutableStateOf(0)
    }

    Log.d("Rerender", "render count")

    val currentImage = imagesData[imageCursor]
    val (imageResourceId, title, author, year) = currentImage

    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        ArtImage(imageId = imageResourceId)
        ArtDescription(
            title,
            author,
            year,
        )
        ArtActionButtonRow(
            onPreviousButtonClick = { imageCursor = maxOf(imageCursor - 1, 0) },
            onNextButtonClick = { imageCursor = minOf(imageCursor + 1, imagesData.size - 1) },
        )
    }
}

@Composable
fun ArtImage(@DrawableRes imageId: Int) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Image(
        painter = painterResource(id = imageId), contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 2 / 3)
            .border(BorderStroke(3.dp, Color.Gray))
            .shadow(elevation = 4.dp)
            .padding(40.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ArtDescription(
    title: String,
    author: String,
    year: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp)
            .padding(20.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Light, fontSize = 24.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 18.sp)) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(author)
                    }
                    append(" ($year)")
                }
            }
        )
    }
}

@Composable
fun ArtActionButtonRow(
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    val buttonWidth = 100.dp
    val buttonModifier = Modifier.width(buttonWidth)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = onPreviousButtonClick, modifier = buttonModifier) {
            Text(text = stringResource(id = R.string.previous))
        }
        Button(onClick = onNextButtonClick, modifier = buttonModifier) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}