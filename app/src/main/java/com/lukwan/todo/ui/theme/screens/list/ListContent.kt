package com.lukwan.todo.ui.theme.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.lukwan.todo.data.models.Priority
import com.lukwan.todo.data.models.TodoTask
import com.lukwan.todo.ui.theme.*
import com.lukwan.todo.utils.RequestState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    requestState: RequestState<List<TodoTask>>,
    onTaskClick: (taskId: Int) -> Unit
) {
    if (requestState is RequestState.Success) {
        if (requestState.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayTasks(tasks = requestState.data, onTaskClick = onTaskClick)
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<TodoTask>,
    onTaskClick: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(items = tasks, key = { task -> task.id }) { task: TodoTask ->
            TaskItem(todoTask = task, onTaskClick = onTaskClick)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    todoTask: TodoTask,
    onTaskClick: (taskId: Int) -> Unit
) {
    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            onTaskClick(todoTask.id)
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = LARGE_PADDING),
        ) {
            Row {
                Text(
                    text = todoTask.title, color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(9f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = todoTask.priority.color)
                    }
                }
            }
            Text(
                text = todoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        todoTask = TodoTask(
            id = 0,
            title = "Read new books",
            description = "I need to finished learning Jetpack Compose in 2 days",
            priority = Priority.HIGH
        ), onTaskClick = {}
    )
}