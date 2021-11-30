package wang.ralph.store.application.account

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.account.Account
import wang.ralph.store.models.account.Accounts
import wang.ralph.store.plugins.SubjectPrincipal

class AccountQuery {
    fun query(dfe: DataFetchingEnvironment): List<AccountDto> = transaction {
        val subjectId = dfe.getLocalContext<SubjectPrincipal>().subjectId
        Account.find { Accounts.subjectId eq subjectId }.map { it.toDto() }
    }
}
