package com.example.film

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.io.Serializable
@Entity (tableName = "filmtable")
data class film(
   val posterurl : String,
   val isim : String,
   val puan : Float,
   val fiyat : Float,
   val uzunluk : String,
   val contentDesc : String,
   val yapımyil : String,
   val tur : String,
   var isbought: Boolean = false,
   val agerange : Boolean = false,
   @PrimaryKey (autoGenerate = true) val id : Int = 0
   )

@Dao
interface FilmDao{
   @Insert
   suspend fun filmekle(film: film)

   @Insert
   suspend fun filmlistesiekle(list: List<film>)

   @Query("SELECT * FROM filmtable WHERE id = :id LIMIT 1 ")
   suspend fun getmoviebyid(id: Int) : film

   @Query("SELECT MAX(id) FROM filmtable")
   suspend fun getmaxid() : Int?

   @Query("SELECT * FROM filmtable WHERE tur LIKE '%' || :genre || '%' AND (isbought=0 OR isbought = :isbought)")
   fun getmoviebygenre(genre:String, isbought: Int? = 0) : LiveData<List<film>>

   @Query("SELECT * FROM filmtable WHERE tur LIKE '%' || :genre || '%' AND id != :id")
   fun getmoviebygenreaslist(genre:String, id : Int) : List<film>

   @Query("SELECT tur FROM filmtable WHERE tur LIKE '%' || :tur || '%'")
   suspend fun getTheGenre(tur:String) : String

   @Query("SELECT * FROM filmtable WHERE isbought = 0 ORDER BY id DESC LIMIT 10")
   fun getTheWhatsNewList() : LiveData<List<film>>

   @Query("SELECT posterurl FROM filmtable WHERE id = :id LIMIT 1")
   suspend fun gettheposter(id:Int) : String

   @Query("SELECT * FROM filmtable WHERE isim LIKE '%' || :aramametni || '%'")
   suspend fun getthefilteredlist(aramametni : String) : List<film>

   @Query("UPDATE filmtable SET isbought=1 WHERE id IN (:ids)")
   suspend fun updatebought(ids : List<Int>)

   @Query("SELECT * FROM filmtable WHERE isbought=1")
   fun getbought() : LiveData<List<film>>
}

@Database(entities = [film::class], version = 1)
abstract class filmdatabase : RoomDatabase(){
   abstract fun filmDao() : FilmDao

   companion object{
      private var data : filmdatabase ?= null

      @Synchronized
      fun getthetable(contx : Context) : filmdatabase{
         if (data == null){
            data = Room.databaseBuilder(contx.applicationContext, filmdatabase::class.java, "data").fallbackToDestructiveMigration().build()
         }
         return data!!
      }

   }
}



