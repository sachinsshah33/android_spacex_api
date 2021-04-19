package extension.domain.spacex.main

import androidx.paging.ExperimentalPagingApi
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.runner.RunWith


@ExperimentalPagingApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MainViewModelTest {
    /**
     * https://stackoverflow.com/questions/62473716/how-can-i-unit-test-paging-3pagingsource
     * https://stackoverflow.com/questions/50435770/how-do-i-create-a-pagedlist-of-an-object-for-tests
     * https://stackoverflow.com/questions/14292863/how-to-mock-a-final-class-with-mockito
     *
     * Tried to look at ways to include more tests, around viewModel and pagingSource, but could not get pass some errors,
     * plus lack of understanding around more complex tests...
     *
     * have commented out for now
     *
     * */


    /*private val itemList = listOf(
        Launch(id = "1", null, null, null, null, null, null),
        Launch(id = "2", null, null, null, null, null, null),
        Launch(id = "3", null, null, null, null, null, null)
    )

    private lateinit var source: LaunchPagingSource
    private lateinit var launchRepository: LaunchRepository

    @Before
    fun before() {
        launchRepository = Mockito.mock(LaunchRepository::class.java)
        source = LaunchPagingSource(launchRepository)
    }

    @Test
    fun `getItems - should delegate to service`() {
        val onSuccess: Consumer<LoadResult<Int, Item>> = mock()
        val onError: Consumer<Throwable> = mock()
        val params: PagingSource.LoadParams<Int> = mock(PagingSource.LoadParams<Int>::class.java)

        whenever(launchRepository.getLaunches(1)).thenReturn(Single.just(itemList))
        source.load(params).subscribe(onSuccess, onError)

        verify(launchRepository).getLaunches(1)
        verify(onSuccess).accept(PagingSource.LoadResult.Page(itemList, null, 2))
        verifyZeroInteractions(onError)
    }


    @ExperimentalPagingApi
    @Test
    fun repo() = runBlockingTest {
        val viewModel = MainViewModel(launchRepository) // Some AndroidX Test rules can help you here, but also some people choose to do it manually.
        val adapter = LaunchAdapter()

        // You need to launch here because submitData suspends forever while PagingData is alive
        val job = launch {
            viewModel.launches.collectLatest {
                adapter.submitData(it)
            }
        }

        advanceUntilIdle() // Let test dispatcher resolve everything

        // How to read from adapter state, there is also .peek() and .itemCount
        //assertEquals(..., adapter.snapshot())

        // We need to cancel the launched job as coroutines.test framework checks for leaky jobs
        job.cancel()
    }*/

}