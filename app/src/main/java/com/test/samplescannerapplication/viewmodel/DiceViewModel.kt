package com.test.samplescannerapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.samplescannerapplication.model.DiceRoll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/**
 * Created by kovacsdavid on 08,november,2024
 */
class DiceViewModel : ViewModel() {
    private val TAG: String = DiceViewModel::class.java.simpleName
    private val _diceRollUiState = MutableStateFlow(DiceRoll())
    val diceRollUIState: StateFlow<DiceRoll> = _diceRollUiState.asStateFlow()

    fun diceRoll() {
        viewModelScope.launch {
            makeDiceRoll()
        }
        //  Makes another dice roll after 3 secs
        coroutineExample()
    }

    private suspend fun makeDiceRoll(delayMillis: Long = 0L): Boolean {
        delay(delayMillis)
        _diceRollUiState.update { diceRoll: DiceRoll ->
            createAndGetDiceRoll(diceRoll)
        }
        return true
    }

    private fun createAndGetDiceRoll(diceRoll: DiceRoll): DiceRoll {
        return diceRoll.copy(
            firstDieValue = Random.nextInt(1, 7),
            secondDieValue = Random.nextInt(1, 7),
            numberOfRolls = diceRoll.numberOfRolls + 1
        )
    }

    private fun coroutineExample() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Coroutine start")

            delay(3000)

            CoroutineScope(Dispatchers.Default).launch {
                Log.d(TAG, "Embedded Coroutine start")
                runBlocking {
                    delay(5000)
                    Log.d(TAG, "Embedded Coroutine runblocking finish")
                }
                Log.d(TAG, "Embedded Coroutine after runblocking")
            }
            makeDiceRoll()
            Log.d(TAG, "Coroutine after delay...")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "DiceViewModel cleared")
    }
}