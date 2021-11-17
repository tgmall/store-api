package wang.ralph.store.models.auth

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@GraphQLDescription("行为主体的类型")
enum class SubjectType {
    @GraphQLDescription("个人")
    Person,

    @GraphQLDescription("公司")
    Company,
}

object Subjects : UUIDTable("subject") {
    val name = varchar("name", 255)
    val type = enumerationByName("type", 32, SubjectType::class)
}

@GraphQLDescription("行为主体")
class Subject(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Subject>(Subjects) {
        @GraphQLDescription("新建个人")
        fun newPerson(name: String) = Subject.new {
            this.type = SubjectType.Person
            this.name = name
        }

        @GraphQLDescription("新建公司")
        fun newCompany(name: String) = Subject.new {
            this.type = SubjectType.Company
            this.name = name
        }
    }

    @GraphQLDescription("名称")
    var name: String by Subjects.name

    @GraphQLDescription("类型")
    var type: SubjectType by Subjects.type
}


