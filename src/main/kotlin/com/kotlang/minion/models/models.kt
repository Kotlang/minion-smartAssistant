package com.kotlang.minion.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by sainageswar on 08/01/17.
 */
@Entity
class UserToken(
    @Id var userId: String = "",
    @Column(length = 150) var token: String = ""
)