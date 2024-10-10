import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.healthtracker.AppointmentReminder
import com.hci.healthtracker.ButtonItem
import com.hci.healthtracker.MedicationReminder
import com.hci.healthtracker.RemindersScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Screen {
    Main, Reminders, Overview, Medication, Activities,MedicationReminder, AppointmentReminder
}

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Main) }

    MaterialTheme {
        when (currentScreen) {
            Screen.Main -> MainScreen(onNavigate = { screen -> currentScreen = screen })
            Screen.Reminders -> RemindersScreen(
                onNavigate = { screen -> currentScreen = screen },
                onBack = {currentScreen = Screen.Main}
            )
            Screen.Overview -> OverviewScreen(onBack = { currentScreen = Screen.Main })
            Screen.Medication -> MedicationDetailsScreen(onBack = { currentScreen = Screen.Main })
            Screen.Activities -> PastActivitiesScreen(onBack = { currentScreen = Screen.Main })
            Screen.MedicationReminder -> MedicationReminder(onBack = { currentScreen = Screen.Main })
            Screen.AppointmentReminder -> AppointmentReminder(onBack = { currentScreen = Screen.Main })
        }
    }
}

@Composable
fun MainScreen(onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()
        Spacer(modifier = Modifier.weight(1f))
        ButtonList(onNavigate)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Title() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "CARE",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "CONNECT",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonList(onNavigate: (Screen) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        ButtonItem("Set Reminders") { onNavigate(Screen.Reminders) }
        ButtonItem("Overview") { onNavigate(Screen.Overview) }
        ButtonItem("View Medication Details") { onNavigate(Screen.Medication) }
        ButtonItem("View Past Activities") { onNavigate(Screen.Activities) }
    }
}





@Composable
fun OverviewScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Overview Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Main")
        }
    }
}

@Composable
fun MedicationDetailsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Medication Details Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Main")
        }
    }
}

@Composable
fun PastActivitiesScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Past Activities Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Main")
        }
    }
}


