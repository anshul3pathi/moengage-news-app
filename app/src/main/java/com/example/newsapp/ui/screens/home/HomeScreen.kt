package com.example.newsapp.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreenRoute(
    viewModel: HomeScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(state = state, onClickRetry = viewModel::onRetryFetchingArticles)
}

@Composable
private fun HomeScreen(
    state: HomeScreenState,
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (state) {
            is HomeScreenState.Error -> HomeScreenError(onClickRetry = onClickRetry)
            HomeScreenState.Loading -> HomeScreenLoading()
            is HomeScreenState.Success -> HomeScreenSuccess(articles = state.articles)
        }
    }
}

@Composable
private fun BoxScope.HomeScreenSuccess(
    articles: List<ArticleUi>,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier.align(Alignment.Center)) {
        HomeScreenTopBar()

        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items = articles) { article ->
                Article(
                    article = article,
                    onClickArticle = {
                        it.articleUrl?.let { uri -> uriHandler.openUri(uri) }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreenTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = stringResource(id = R.string.lbl_news),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun BoxScope.HomeScreenLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier.align(Alignment.Center))
}

@Composable
fun BoxScope.HomeScreenError(
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_error_banner), contentDescription = null)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClickRetry,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = stringResource(id = R.string.lbl_retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenTopBar_Preview() {
    HomeScreenTopBar()
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenSuccess_Preview() {
    val article = ArticleUi(
        id = "id",
        imageUrl = null,
        author = "Anshul Tripathi",
        title = "Title",
        description = "Description",
        publishTimeInMillis = 10000000L,
        articleUrl = null,
    )
    val articles = listOf(article, article, article, article)

    NewsAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreenSuccess(articles = articles)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenError_Preview() {
    NewsAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreenError(onClickRetry = { /*TODO*/ })
            }
        }
    }
}