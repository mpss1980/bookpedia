package br.com.coupledev.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListScreenRoot
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListViewModel
import br.com.coupledev.bookpedia.ui.theme.BookpediaTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = koinViewModel<BookListViewModel>()

            BookpediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = {

                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}