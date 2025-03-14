package com.laskapi.myradio.data

import com.laskapi.myradio.model.MirrorModel
import com.laskapi.myradio.room.MirrorDao
import java.net.InetAddress
import javax.inject.Inject

class MirrorsLocalDataSource @Inject constructor(
    private val mirrorDao: MirrorDao
) {
    suspend fun save(mirrors: Array<out InetAddress>) {
        mirrors.map { MirrorModel(address = it.hostAddress) }
            .also {
                mirrorDao.insertAll(it)
            }
    }

    suspend fun getMirrors():List<MirrorModel>{
        return mirrorDao.getAll()
    }

}