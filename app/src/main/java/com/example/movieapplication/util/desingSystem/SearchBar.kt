package com.example.movieapplication.util.desingSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.search.presentation.SearchScreenState
import com.example.movieapplication.ui.theme.MovieApplicationTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    placeholder: String = "Placeholder",
    searchScreenState: SearchScreenState,
    onSearch: (String) -> Unit = {},
) {
    var text by rememberSaveable { mutableStateOf(searchScreenState.searchQuery) }

    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.shapes.small,
                )
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 17.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (text.isEmpty()) Text(
                            placeholder,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                fontSize = 17.sp
                            )
                        )
                        innerTextField()
                    }
                    if (text.isNotEmpty()) {
                        Icon(
                            Icons.Rounded.Close,
                            null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(28.dp)
                                .clickable {
                                    text = ""
                                    onSearch("")
                                }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun NonFocusedSearchBar(
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.shapes.small,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.Search,
            null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(23.dp)
                .alpha(0.3f)
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
            fontSize = 16.sp,
            text = placeholderText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NonFocusedSearchBarPreview() {
    MovieApplicationTheme {
        NonFocusedSearchBar(placeholderText = "Search for a movie or TV series")
    }
}