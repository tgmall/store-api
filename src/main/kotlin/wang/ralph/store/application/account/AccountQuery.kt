package wang.ralph.store.application.account

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.account.Account
import wang.ralph.store.models.account.Accounts
import wang.ralph.store.plugins.subjectId

class AccountQuery {
    fun query(dfe: DataFetchingEnvironment): List<AccountDto> = transaction {
        val subjectId = dfe.subjectId
        Account.find { Accounts.subjectId eq subjectId }.map { it.toDto() }
    }
}
