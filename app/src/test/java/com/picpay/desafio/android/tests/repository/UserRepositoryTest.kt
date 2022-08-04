package com.picpay.desafio.android.tests.repository

import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.data.irepository.IUserRepository
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.service.IUsersService
import com.picpay.desafio.android.data.service.response.GetUsersResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private val service = mockk<IUsersService>()
    private val repository: IUserRepository = UserRepository(service)

    @DisplayName("GIVEN")
    @Nested
    inner class Given {

        @DisplayName("service has filled list")
        @Nested
        inner class HasList {

            private val expectedServiceResult = Response.success(listOf(
                    GetUsersResponse(
                        MOCK_ID,
                        MOCK_STRING,
                        MOCK_STRING,
                        MOCK_STRING
                    )
                )
            )

            private val expectedResult = MutableLiveData<Result<List<GetUsersResponse>>>().apply { expectedServiceResult }

            @BeforeEach
            fun setup() {
                coEvery { service.getUsers() } returns expectedServiceResult
            }

            @DisplayName("WHEN")
            @Nested
            inner class When {

                @DisplayName("calls repository get user list")
                @Nested
                inner class RepositoryGetUserList {

                    @DisplayName("THEN")
                    @Nested
                    inner class Then {

                        @DisplayName("verify returned list contains the element")
                        @Test
                        fun returnsFilteredElement() = runTest {
                            runBlocking {
                                repository.getUsersFromServer()
                                repository.statusGetUsersFromServer.apply {
                                    Assertions.assertEquals(expectedResult.value, this.value)
                                }
                            }
                        }
                    }
                }
            }
        }

        @DisplayName("service has returned empty list")
        @Nested
        inner class EmptyList {

            private val expectedServiceResult = Response.success<List<GetUsersResponse>>(
                emptyList()
            )

            private val expectedResult = MutableLiveData<Result<List<GetUsersResponse>>>().apply { expectedServiceResult }

            @BeforeEach
            fun setup() {
                coEvery { service.getUsers() } returns expectedServiceResult
            }

            @DisplayName("WHEN")
            @Nested
            inner class When {

                @DisplayName("calls repository get user list")
                @Nested
                inner class RepositoryGetUserList {

                    @DisplayName("THEN")
                    @Nested
                    inner class Then {

                        @DisplayName("verify returned list is empty")
                        @Test
                        fun returnsEmptyList() = runTest {
                            runBlocking {
                                repository.getUsersFromServer()
                                repository.statusGetUsersFromServer.apply {
                                    Assertions.assertEquals(expectedResult.value, this.value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private companion object {
        const val MOCK_STRING = "MOCK_STRING"
        const val MOCK_ID = 1
    }
}