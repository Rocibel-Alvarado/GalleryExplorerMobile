package com.galleryExplorerMobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galleryExplorerMobile.data.local.dao.FavoriteDao
import com.galleryExplorerMobile.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritoDao(): FavoriteDao
}