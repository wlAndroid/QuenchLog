package com.app.quench.log.base

import android.content.Context
import androidx.room.*
import com.app.quench.log.R
import kotlinx.coroutines.flow.Flow

fun createDb(context: Context): HitDatabase {
    return Room.databaseBuilder(context, HitDatabase::class.java, "hit_db").build()
}

@Database(entities = [DrinkEntity::class], version = 2)
abstract class HitDatabase : RoomDatabase() {
    abstract fun dao(): HitDao
}

@Dao
interface HitDao {
    @Query("SELECT * FROM drink order by date desc  ")
    fun list(): Flow<List<DrinkEntity>>

    @Insert
    suspend fun add(entity: DrinkEntity)

    @Delete
    suspend fun delete(entity: DrinkEntity)
}

@Entity(tableName = "drink")
data class DrinkEntity(
    var w: Int,
    var date: Long,
    var c: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int
)

data class Drink(var water: Int = 0, var date: Long = 0, var cup: Int = 0, val id: Int = 0)

val DrinkEntity.bean: Drink get() = Drink(w, date, c, id)
val Drink.entity: DrinkEntity get() = DrinkEntity(water, date, cup, id)

val list by lazy {
    listOf(
        R.drawable.ic_hit_cup_1 to "Water",
        R.drawable.ic_hit_cup_2 to "Spark",
        R.drawable.ic_hit_cup_3 to "Coffee",
        R.drawable.ic_hit_cup_4 to "Milk Tea",
        R.drawable.ic_hit_cup_5 to "Tea",
        R.drawable.ic_hit_cup_6 to "Milk",
    )
}
