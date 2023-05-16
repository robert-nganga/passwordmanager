package com.robert.passwordmanager.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.robert.passwordmanager.models.Account
import kotlinx.coroutines.flow.Flow

class FakeAccountRepository(val accounts: MutableList<Account>): AccountRepository {

    private var observableAccounts = MutableLiveData<List<Account>>(accounts)





    override fun observeAllAccounts(): Flow<List<Account>> {
        return observableAccounts.asFlow()
    }

    override fun searchPasswords(query: String): Flow<List<Account>> {
        return observableAccounts.map { accounts-> accounts.filter { it.websiteName == query } }.asFlow()
    }

    override fun getAccountById(id: Int): Flow<Account> {
        return observableAccounts.map { accounts->
            accounts.first { it.id == id }
        }.asFlow()
    }

    override suspend fun insert(account: Account) {
        accounts.add(account)
        observableAccounts.postValue(accounts)
    }

    override suspend fun update(account: Account) {
        accounts.removeIf { it.id == account.id }
        accounts.add(account)
        observableAccounts.postValue(accounts)
    }

    override suspend fun delete(account: Account) {
        accounts.remove(account)
        observableAccounts.postValue(accounts)
    }

    override suspend fun deleteAll() {
        accounts.clear()
        observableAccounts.postValue(accounts)
    }
}