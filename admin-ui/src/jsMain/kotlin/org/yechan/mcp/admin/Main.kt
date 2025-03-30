package org.yechan.mcp.admin

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.yechan.mcp.admin.api.ApiClient
import org.yechan.mcp.admin.components.UrlForm
import org.yechan.mcp.admin.components.UrlTable
import org.yechan.mcp.admin.model.UrlInfo

fun main() {
    renderComposable(rootElementId = "root") {
        MainApp()
    }
}

@Composable
fun MainApp() {
    val apiClient = remember { ApiClient() }
    val scope = rememberCoroutineScope()
    var urlList by remember { mutableStateOf<List<UrlInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        loadUrls(apiClient, onSuccess = { urlList = it }, onError = { error = it }, onLoading = { isLoading = it })
    }
    
    fun refreshUrls() {
        scope.launch {
            loadUrls(apiClient, onSuccess = { urlList = it }, onError = { error = it }, onLoading = { isLoading = it })
        }
    }
    
    Div(
        attrs = {
            style {
                maxWidth(800.px)
                margin(0.px, "auto")
                padding(20.px)
                fontFamily("Arial", "sans-serif")
            }
        }
    ) {
        H1 {
            Text("URL Shortener Admin")
        }
        
        UrlForm(
            onShortenSuccess = { refreshUrls() },
            apiClient = apiClient
        )
        
        if (isLoading) {
            Div(attrs = { style { textAlign("center"); padding(20.px) } }) {
                Text("Loading...")
            }
        }
        
        error?.let {
            Div(
                attrs = {
                    style {
                        backgroundColor(Color.rgb(255, 235, 235))
                        color(Color.rgb(197, 60, 60))
                        padding(10.px)
                        borderRadius(4.px)
                        marginTop(16.px)
                        marginBottom(16.px)
                    }
                }
            ) {
                Text(it)
            }
        }
        
        UrlTable(
            urls = urlList,
            onDelete = { shortKey ->
                scope.launch {
                    try {
                        isLoading = true
                        error = null
                        apiClient.deleteUrl(shortKey)
                        refreshUrls()
                    } catch (e: Exception) {
                        error = "Failed to delete URL: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            }
        )
    }
}

private suspend fun loadUrls(
    apiClient: ApiClient,
    onSuccess: (List<UrlInfo>) -> Unit,
    onError: (String) -> Unit,
    onLoading: (Boolean) -> Unit
) {
    try {
        onLoading(true)
        onError(null)
        val urls = apiClient.getAllUrls()
        onSuccess(urls)
    } catch (e: Exception) {
        console.log("Error loading URLs", e)
        onError("Failed to load URLs: ${e.message}")
    } finally {
        onLoading(false)
    }
}
