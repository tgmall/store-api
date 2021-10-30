package wang.ralph.blog.demo.services

data class UserData(
    val name: String,
    val password: String,
    val nickName: String = name,
    val avatarUrl: String = "",
)
