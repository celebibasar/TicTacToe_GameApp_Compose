package com.basarcelebi.tictactoe

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel:ViewModel() {
    private val _gameLevel:MutableStateFlow<Int> = MutableStateFlow(3)
    val gameLevel = _gameLevel.asStateFlow()

    private val _gameTurn = MutableStateFlow(3)
    val gameTurn = _gameTurn.asStateFlow()

    val listOfMovements = mutableStateListOf(*initialMovements.toTypedArray())
    private val initialMovements:List<Movement> get() = MutableList(gameLevel.value*gameLevel.value){
        Movement()
    }
    fun resetGame(){
        listOfMovements.clear()
        _gameTurn.update { 0 }
        listOfMovements.addAll(initialMovements)

    }
    fun checkWin(index:Int, onWin:(Int?)->Unit){
        val fromLeft = (index%_gameLevel.value)
        val fromRight = _gameLevel.value - ((index%_gameLevel.value)+1)

        val listOfRowIndexes = mutableListOf<Int>()

        for (i in 1..fromLeft){
            val newIndex = index-i
            listOfRowIndexes.add(newIndex)
        }

        listOfRowIndexes.add(index)

        for (i in 1..fromRight){
            val newIndex= index-i
            listOfRowIndexes.add(newIndex)
        }

        val listOfRowTurns= mutableListOf<Int?>()

        listOfRowIndexes.forEach {
            listOfRowTurns.add(listOfMovements[it].turn)
        }

        if(listOfRowTurns.count { it==null } <= 0){
            if (listOfRowTurns.distinct().count() == 1 && listOfRowTurns.count() > 1){
                onWin(listOfRowTurns.first())
            }
        }

        val listOfVerticalIndexes =  mutableListOf(index)
        for (i in 1.._gameLevel.value){
            val newIndexTop = index- (_gameLevel.value*i)
            if(newIndexTop < 0){
                break
            }else{
                listOfVerticalIndexes.add(newIndexTop)
            }
        }

        for (i in 1.._gameLevel.value){
            val newIndexBottom= index + (_gameLevel.value*i)
            if (newIndexBottom > listOfMovements.size-1){
                break
            }else{
                listOfVerticalIndexes.add(newIndexBottom)
            }
        }
        val listOfVerticallyTurns = mutableListOf<Int?>()

        listOfVerticalIndexes.forEach{
            listOfVerticallyTurns.add(listOfMovements[it].turn)
        }

        if(listOfVerticallyTurns.count{ it == null} <= 0){
            if(listOfVerticallyTurns.distinct().count() == 1 && listOfVerticallyTurns.count()>1){
                val winTurn = listOfVerticallyTurns.first()
                onWin(winTurn)
            }
        }

        val listOfBevelIndexes = mutableListOf<Int>()
        listOfBevelIndexes.add(index)
        for (i in 1.._gameLevel.value){
            val newIndexBottom = index+((_gameLevel.value+1)*i)
            if(newIndexBottom<=listOfMovements.size-1){
                listOfBevelIndexes.add(newIndexBottom)
            }else{
                break
            }
        }

        val listOfBevelTurns = mutableListOf<Int?>()

        listOfBevelIndexes.forEach{
            listOfBevelTurns.add(listOfMovements[it].turn)
        }
        if(listOfVerticallyTurns.filterNotNull().count()>=_gameLevel.value&&listOfVerticallyTurns.count { it==null } <= 0){
            if(listOfBevelTurns.distinct().count()==1 && listOfBevelTurns.count()>1){
                onWin(listOfBevelTurns.first())
            }

        }

    }
}

data class Movement(
    val filled:Boolean = false,
    val turn:Int? = null
)