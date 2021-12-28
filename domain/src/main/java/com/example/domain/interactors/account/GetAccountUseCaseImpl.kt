package com.example.domain.interactors.account

import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCaseImpl @Inject constructor(private val accountRepository: AccountRepository) :
    GetAccountUseCase {
    override suspend fun getAccount(sessionId: String) =
        accountRepository.getAccountDetails(sessionId)
}
