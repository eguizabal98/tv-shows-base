package com.example.domain.interactors.account

import com.example.domain.repository.AccountRepository

class GetAccountUseCaseImpl(private val accountRepository: AccountRepository) : GetAccountUseCase {
    override suspend fun getAccount(sessionId: String) =
        accountRepository.getAccountDetails(sessionId)
}