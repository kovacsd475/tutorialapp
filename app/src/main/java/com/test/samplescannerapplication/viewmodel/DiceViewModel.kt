package com.test.samplescannerapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.test.samplescannerapplication.model.DiceRoll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

/**
 * Created by kovacsdavid on 08,november,2024
 */
class DiceViewModel : ViewModel() {
    private val _diceRollUiState = MutableStateFlow(DiceRoll())
    val diceRollUIState: StateFlow<DiceRoll> = _diceRollUiState.asStateFlow()

    fun diceRoll() {
        _diceRollUiState.update { diceRoll: DiceRoll ->
            diceRoll.copy(
                firstDieValue = Random.nextInt(1, 7),
                secondDieValue = Random.nextInt(1, 7),
                numberOfRolls = diceRoll.numberOfRolls + 1
            )
        }
    }
}