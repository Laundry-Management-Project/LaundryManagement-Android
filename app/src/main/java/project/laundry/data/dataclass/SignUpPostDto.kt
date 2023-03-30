package project.laundry.data.dataclass

data class SignUpPostDto(
    val id :String,
    val password:String,
    val name:String,
    val phone:String,
    val userType:String //"CU", "OW"
)

