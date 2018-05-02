package com.jailbreackers.soulhunter



import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.reactivex.Single
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScoreRepository(application)


    fun saveScore(currentScore: Score): Single<Unit> {
        return Single.fromCallable {
            if (currentScore.score <= 0) {
                throw IllegalArgumentException("Invalid data")
            }


            repository.saveScore(currentScore)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun selectScore(scoreId: Int): LiveData<Score> {
        return repository.selectScore(scoreId)
    }

    fun selectMaxScore(): LiveData<Score> {
        return repository.selectMaxScore()
    }

    fun deleteScore(score: Score): Single<Unit> {
        return Single.fromCallable { repository.deleteScore(score) }
    }
}