package repositories

import com.kotlang.minion.models.UserToken
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by sainageswar on 08/01/17.
 */
interface UserTokenRepository: JpaRepository<UserToken, String> {

}