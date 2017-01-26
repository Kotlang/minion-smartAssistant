package com.kotlang.minion.models

import java.sql.Date
import java.util.*
import javax.persistence.*

/**
 * Created by sainageswar on 08/01/17.
 */
@Entity
class Team(
        @Id var teamId: Int = 0,
        var name: String = "",
        @OneToMany(cascade = arrayOf(CascadeType.REMOVE), orphanRemoval = true)
        var users: Set<FlockUser>? = null
)

@Entity
class FlockUser(
        @Id var userId: String = "",
        @Column(length = 150) var token: String? = null,
        var isAdmin: Boolean = false,
        var firstName: String = "",
        var lastName: String = "",
        @Column(unique = true) var email: String? = null,
        @ManyToOne var team: Team? = null
)

@Entity
class OutOfOfficeDetails (
        @Id @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO) var id: Long = 0,
        var fromDate: Date = Date(Calendar.getInstance().time.time),
        var toDate: Date = Date(Calendar.getInstance().time.time),
        var isApproved: Boolean = false,
        @ManyToOne var applicant: FlockUser? = null,
        @ManyToOne var approver: FlockUser? = null
)