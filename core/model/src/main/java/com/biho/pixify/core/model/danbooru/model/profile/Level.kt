package com.biho.pixify.core.model.danbooru.model.profile

enum class Level(val id: Int) {
    Unknown(id = -1),
    Lurker(id = 0),
    Restricted(id = 10),
    Member(id = 20),
    Gold(id = 30),
    Platinum(id = 31),
    Builder(id = 32),
    Contributor(id = 35),
    Approver(id = 37),
    Moderator(id = 40),
    Admin(id = 50),
    Owner(id = 60),
}

fun Int.toLevel(): Level {
    return Level.entries.associateBy { it.id }[this] ?: Level.Lurker
}