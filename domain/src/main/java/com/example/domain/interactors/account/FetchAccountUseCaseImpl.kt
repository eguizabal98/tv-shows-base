package com.example.domain.interactors.account

import com.example.domain.model.Profile
import com.example.domain.model.RequestResult
import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class FetchAccountUseCaseImpl @Inject constructor(private val accountRepository: AccountRepository) :
    FetchAccountUseCase {
    override suspend fun fetchAccount(sessionID: String): RequestResult<Profile> =
        accountRepository.fetchAccount(sessionID)
}
