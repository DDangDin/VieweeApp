package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.data.source.local.db.UserDao
import com.capstone.vieweeapp.data.source.local.entity.User
import com.capstone.vieweeapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dao: UserDao
): UserRepository {

    override suspend fun getUser(): User {
        return dao.getUser()
    }

    override suspend fun insertResume(user: User) {
        dao.insertResume(user)
    }

    override suspend fun deleteUser(user: User) {
        dao.deleteUser(user)
    }

    override suspend fun deleteAllUser() {
        dao.deleteAllUser()
    }
}