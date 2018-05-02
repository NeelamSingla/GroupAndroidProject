package com.jailbreackers.soulhunter


import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class Score(@PrimaryKey(autoGenerate = true) val id: Int,
                 val date: String,
                 val score: Int)
@Dao
interface ScoreDao {
    @Query("SELECT * FROM Score ORDER BY date")
    fun selectAllScores(): LiveData<List<Score>>

    @Query("SELECT * FROM Score WHERE id = :id")
    fun selectScore(id: Int): LiveData<Score>

    @Query("SELECT MAX(score) FROM Score")
    fun selectMaxScore(): LiveData<Score>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveScore(score: Score)

    @Delete
    fun deleteScore(score: Score)
}
@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}

class ScoreRepository(application: Application) {
    private val scoreDao: ScoreDao
    init {
        val database = Room
                .databaseBuilder(application,
                        ScoreDatabase::class.java,
                        "score-database")
                .build()
        scoreDao = database.scoreDao()
    }


    fun saveScore(score: Score) {
        scoreDao.saveScore(score)
    }

    fun selectScore(scoreId: Int): LiveData<Score> {
        return scoreDao.selectScore(scoreId)
    }

    fun selectMaxScore(): LiveData<Score> {
        return scoreDao.selectMaxScore()
    }

    fun selectAllScores(): LiveData<List<Score>> {
        return scoreDao.selectAllScores()
    }

    fun deleteScore(score: Score) {
        scoreDao.deleteScore(score)
    }
}