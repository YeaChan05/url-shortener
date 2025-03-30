package org.yechan.mcp.admin.components

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.yechan.mcp.admin.api.ApiClient

@Composable
fun UrlForm(
    onShortenSuccess: () -> Unit,
    apiClient: ApiClient
) {
    var url by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    Form(
        attrs = {
            onSubmit {
                it.preventDefault()
                if (url.isBlank()) {
                    error = "Please enter a URL"
                    return@onSubmit
                }
                
                scope.launch {
                    try {
                        isLoading = true
                        error = null
                        success = null
                        val response = apiClient.shortenUrl(url)
                        success = "URL shortened successfully: ${response.shortenedUrl}"
                        url = ""
                        onShortenSuccess()
                    } catch (e: Exception) {
                        error = "Failed to shorten URL: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            }
            style {
                marginTop(20.px)
                marginBottom(20.px)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                gap(10.px)
            }
        }
    ) {
        Label(
            forId = "urlInput",
            attrs = {
                style {
                    fontWeight("bold")
                }
            }
        ) {
            Text("Enter URL to shorten:")
        }
        
        Div(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    gap(10.px)
                }
            }
        ) {
            Input(
                type = InputType.Url,
                attrs = {
                    id("urlInput")
                    value(url)
                    onInput { url = it.value }
                    required(true)
                    placeholder("https://example.com/long/url")
                    style {
                        flex(1)
                        padding(8.px)
                        border(1.px, LineStyle.Solid, Color.rgb(200, 200, 200))
                        borderRadius(4.px)
                    }
                }
            )
            
            Button(
                attrs = {
                    type(ButtonType.Submit)
                    disabled(isLoading)
                    style {
                        padding(8.px, 16.px)
                        backgroundColor(Color.rgb(70, 130, 180))
                        color(Color.white)
                        border(0.px)
                        borderRadius(4.px)
                        cursor("pointer")
                        if (isLoading) {
                            opacity(0.7)
                        }
                    }
                }
            ) {
                Text(if (isLoading) "Shortening..." else "Shorten")
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
                        marginTop(10.px)
                    }
                }
            ) {
                Text(it)
            }
        }
        
        success?.let {
            Div(
                attrs = {
                    style {
                        backgroundColor(Color.rgb(235, 255, 235))
                        color(Color.rgb(60, 150, 60))
                        padding(10.px)
                        borderRadius(4.px)
                        marginTop(10.px)
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}
