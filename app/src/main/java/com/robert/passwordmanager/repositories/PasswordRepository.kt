package com.robert.passwordmanager.repositories

import com.robert.passwordmanager.models.Account
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun searchPasswords(query: String): Flow<List<Account>>

    fun getAccountById(id: Int): Flow<Account>

    suspend fun upsert(passwordDetails: Account)

    suspend fun delete(passwordDetails: Account)

    suspend fun deleteAll()
}