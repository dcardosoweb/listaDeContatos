package com.picpay.desafio.android

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.core.MockServerRule
import com.picpay.desafio.android.presentation.fragment.ContactsFragment
import com.picpay.desafio.android.provider.MockServiceResponseProvider
import com.picpay.desafio.android.provider.MockResponseDataProvider
import com.picpay.desafio.android.provider.RecyclerMatcherProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsFragmentTest {

    private lateinit var scenario: FragmentScenario<ContactsFragment>

    @get:Rule
    val mockRule = MockServerRule()

    @Before
    fun setup() {
        mockRule.server.enqueue(MockServiceResponseProvider.usersContactMockResponse())
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun shouldDisplayListItemText() {
        RecyclerMatcherProvider.checkRecyclerViewItem(
            R.id.recyclerView,
            position = 0,
            withText(MockResponseDataProvider.mockedUserContact().name)
        )
    }
}