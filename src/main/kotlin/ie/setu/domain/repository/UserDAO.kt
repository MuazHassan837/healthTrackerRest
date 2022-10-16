package ie.setu.domain.repository
import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToUser

class UserDAO {

    private val users = arrayListOf<User>()

    fun getAll(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }


    fun findById(id: Int): User?{
        return null
    }

    fun save(user: User){
    }

    fun findByEmail(email: String) :User?{
        return null
    }

    fun delete(id: Int) {
    }

    fun update(id: Int, user: User){
    }

//    fun getAll() : ArrayList<User>{
//        return users
//    }
//    fun findById(id: Int): User?{
//        return users.find {it.id == id}
//    }
//    fun save(user: User){
//        users.add(user)
//    }
//    fun findByEmail(email: String) :User?{
//        return users.find { it.email == email }
//    }
//    fun delete(id: Int){
//        users.remove(users.find {it.id == id})
//    }
//    fun update(id: Int, user: User) {
//        var reff = users.find {it.id == id}
//        if (reff != null){
//            reff.id = user.id
//            reff.name = user.name
//            reff.email = user.email
//        }
//    }
}