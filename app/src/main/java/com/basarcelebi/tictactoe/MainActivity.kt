package com.basarcelebi.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basarcelebi.tictactoe.ui.theme.Pink200
import com.basarcelebi.tictactoe.ui.theme.Pink300
import com.basarcelebi.tictactoe.ui.theme.Pink500
import com.basarcelebi.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {

    private val viewModel:AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var win by remember{
                        mutableStateOf<Int?>(null)
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Pink200,
                                    Pink300,
                                    Pink500
                                )
                            )
                        )){
                        val gameLevel by viewModel.gameLevel.collectAsState()
                        val gameTurn by viewModel.gameTurn.collectAsState()

                        val listOfMovements = viewModel.listOfMovements

                        Column(modifier = Modifier.padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                ProfileItem(text = "You", avatar = R.drawable.ic_profile, taw = R.drawable.ic_circle, tawTint = Color.White)
                                ProfileItem(text = "System", avatar = R.drawable.ic_robot, taw = R.drawable.ic_close, tawTint = Color.Yellow)

                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.ProfileItem(
    text:String,
    avatar:Int,
    taw:Int,
    tawTint:Color
) {
    
    Box(modifier = Modifier
        .weight(1f)
        .height(200.dp), contentAlignment = Alignment.Center){
        Column(modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(start = 35.dp, end = 35.dp, bottom = 15.dp, top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier
                .size(65.dp, 45.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Pink500
                ), contentAlignment = Alignment.Center){
                Icon(modifier = Modifier.size(20.dp),painter = painterResource(id = taw), contentDescription = null, tint = tawTint)
            }

        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
            Box(modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .background(Color.White)
                .border(3.dp, Pink500, CircleShape)){
                Image(modifier = Modifier.size(40.dp),painter = painterResource(id = avatar) , contentDescription = null)
            }
        }

    }
    
}

