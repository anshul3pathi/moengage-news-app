package com.example.newsapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Article(
    article: ArticleUi,
    onClickArticle: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { onClickArticle(article) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = article.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_placeholder)
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.title,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = article.description,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun NewsItem_Preview() {
    val article = ArticleUi(
        id = "id",
        imageUrl = null,
        author = "Anshul Tripathi",
        title = "Title",
        articleUrl = null,
        description = "Description",
        publishTimeInMillis = 10000000L
    )
    Surface {
        NewsAppTheme {
            Article(
                article = article,
                modifier = Modifier.padding(16.dp),
                onClickArticle = {}
            )
        }
    }
}