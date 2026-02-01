package com.cy.testapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.chenyue404.androidlib.logcat.L
import com.cy.testapp.activity.ui.theme.TestAppTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Scaffold(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = Coil.imageLoader(context)
    val key = "123"
    var loadSuccess by remember { mutableStateOf(false) }
    val memoryCacheValue = imageLoader.memoryCache?.get(MemoryCache.Key(key))
    L.d { memoryCacheValue.toString() }
    var loadImageIndex by remember { mutableIntStateOf(0) }
    Row {
        MyImage(key, loadSuccess)
        Text(
            text = "disk",
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(Color.Green)
                .clickable {
                    imageLoader.memoryCache?.remove(MemoryCache.Key(key))
                    imageLoader.enqueue(
                        ImageRequest.Builder(context)
                            .data("https://picsum.photos/id/$loadImageIndex/300")
                            .placeholderMemoryCacheKey(key)
                            .memoryCacheKey(key)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .listener(
                                onStart = {
                                    L.d { "onStart $loadImageIndex" }
                                },
                                onCancel = {
                                    L.d { "onCancel $loadImageIndex" }
                                },
                                onSuccess = { request, result ->
                                    loadSuccess = !loadSuccess
                                    L.d { "onSuccess $loadImageIndex" }
                                    loadImageIndex += 1
                                },
                                onError = { request, result ->
                                    L.d { "onError $loadImageIndex" }
                                }
                            )
                            .build()
                    )
                }
        )
    }
}

@Composable
private fun MyImage(key: String, loadSuccess: Boolean) {
    L.d { "load MyImage: key=$key, loadSuccess=$loadSuccess" }
    val cache = Coil.imageLoader(LocalContext.current).memoryCache?.get(MemoryCache.Key(key))
    L.d { "cache : ${cache.toString()}" }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
//            .data("https://this.is.a.dummy.url.for.cache.only")
            .data(cache)
            .placeholderMemoryCacheKey(key)
            .memoryCacheKey(key)
            .build(),
        contentDescription = "",
        modifier = Modifier.size(300.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppTheme {
        Greeting("Android")
    }
}