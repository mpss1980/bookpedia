package br.com.coupledev.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.coupledev.bookpedia.book.domain.Book
import br.com.coupledev.bookpedia.ui.theme.BookpediaTheme

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            BookListItem(
                book = book,
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onBookClick(book)
                }
            )
        }
    }
}


@Preview
@Composable
private fun BookListPreview() {
    BookpediaTheme {
        BookList(
            books = listOf(
                Book(
                    id = "123",
                    title = "Kotlin Programming",
                    imageUrl = "https://placehold.co/400x600",
                    authors = listOf("Mathew Mathias"),
                    description = "Description",
                    languages = listOf("English"),
                    firstPublishYear = "2023",
                    averageRating = 3.2,
                    ratingCount = 1,
                    numPages = 300,
                    numEditions = 3
                ),
                Book(
                    id = "122",
                    title = "Kotlin Programming",
                    imageUrl = "https://placehold.co/400x600",
                    authors = listOf("Mathew Mathias"),
                    description = "Description",
                    languages = listOf("English"),
                    firstPublishYear = "2023",
                    averageRating = 3.2,
                    ratingCount = 1,
                    numPages = 300,
                    numEditions = 3
                )
            ),
            onBookClick = {},
        )
    }
}