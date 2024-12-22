package br.com.coupledev.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import br.com.coupledev.bookpedia.book.presentation.SelectedBookViewModel
import br.com.coupledev.bookpedia.book.presentation.book_detail.BookDetailAction
import br.com.coupledev.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import br.com.coupledev.bookpedia.book.presentation.book_detail.BookDetailViewModel
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListScreenRoot
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListViewModel
import br.com.coupledev.bookpedia.ui.theme.BookpediaTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Route.BookGraph
            ) {
                navigation<Route.BookGraph>(
                    startDestination = Route.BookList
                ) {
                    composable<Route.BookList>(
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() }
                    ) {
                        val viewModel = koinViewModel<BookListViewModel>()
                        val selectedBookViewModel =
                            it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                        LaunchedEffect(true) {
                            selectedBookViewModel.onSelectBook(null)
                        }

                        BookpediaTheme {
                            Scaffold(
                                modifier = Modifier.fillMaxSize()
                            ) { innerPadding ->
                                BookListScreenRoot(
                                    viewModel = viewModel,
                                    onBookClick = { book ->
                                        selectedBookViewModel.onSelectBook(book)
                                        navController.navigate(
                                            Route.BookDetail(book.id)
                                        )
                                    },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                    composable<Route.BookDetail>(
                        enterTransition = {
                            slideInHorizontally { initialOffset ->
                                initialOffset
                            }
                        },
                        exitTransition = {
                            slideOutHorizontally { initialOffset ->
                                initialOffset
                            }
                        },
                    ) {
                        val selectedBookViewModel =
                            it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                        val viewModel = koinViewModel<BookDetailViewModel>()
                        val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                        LaunchedEffect(selectedBook) {
                            selectedBook?.let { book ->
                                viewModel.onAction(
                                    BookDetailAction.OnSelectedBookChange(book)
                                )
                            }
                        }

                        BookDetailScreenRoot(
                            viewModel = viewModel,
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}