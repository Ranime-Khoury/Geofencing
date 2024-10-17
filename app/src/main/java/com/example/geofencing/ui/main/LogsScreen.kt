import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.geofencing.R
import com.example.geofencing.data.model.Log
import com.example.geofencing.ui.main.LogsViewModel
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(
    logsViewModel: LogsViewModel = hiltViewModel()
) {
    val logs by logsViewModel.logs.collectAsState(initial = emptyList())
    val newPosition by logsViewModel.newPosition.collectAsState(null)

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Geofencing") }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }, content = { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White.copy(0.4f), shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = newPosition ?: "no position",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                LazyColumn {
                    items(logs) { log ->
                        LogItem(log)
                    }
                }
            }
        }
    })

}

@SuppressLint("SimpleDateFormat")
@Composable
fun LogItem(log: Log) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(start = 10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(80.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(15.dp))

                Column {
                    Text(
                        text = log.areaName,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Date: ${SimpleDateFormat("EEEE dd MMMM yyyy").format(Date(log.entryTime))}",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Entry: ${SimpleDateFormat("HH:mm:ss").format(Date(log.entryTime))}",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                        Text(
                            text = "|",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold,
                        )

                        log.exitTime?.let {
                            Text(
                                text = "Exit: ${SimpleDateFormat("HH:mm:ss").format(Date(it))}",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        } ?: Text(
                            text = "Still inside the area",
                            color = Color.Gray,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

