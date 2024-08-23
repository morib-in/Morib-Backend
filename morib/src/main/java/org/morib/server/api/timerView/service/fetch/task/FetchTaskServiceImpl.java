package org.morib.server.api.timerView.service.fetch.task;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.morib.server.domain.category.infra.Category;
import org.morib.server.domain.task.TaskManager;
import org.morib.server.domain.task.infra.Task;
import org.morib.server.domain.task.infra.TaskGateway;
import org.morib.server.domain.timer.infra.Timer;
import org.morib.server.domain.todo.infra.Todo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchTaskServiceImpl implements FetchTaskService {

    private final TaskManager taskManager;
    private final TaskGateway taskGateway;

    @Override
    public void fetch() {
        //timer가 멈췄을때 계산해주는 역할을 해야함!

    }

    @Override
    public Task fetchByTaskIdInCategories(Set<Category> categories, Long taskId) {
        //taskManager.findByTaskIdInCategories(categories, taskId);
        return categories.stream()
                .flatMap(category -> category.getTasks().stream())
                .filter(task -> taskManager.isTaskHaveSameTaskId(task, taskId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 task가 없습니다."));
    }

    @Override
    public LinkedHashSet<Task> fetchByTodoAndSameTargetDate(Todo todo, LocalDate targetDate) {
        Set<Task> tasks = todo.getTasks();
        return tasks.stream().
                filter(task -> isTimerInTaskPresentTargetDate(task, targetDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private boolean isTimerInTaskPresentTargetDate(Task task, LocalDate targetDate) {
        Set<Timer> timers = task.getTimers();
        return timers.stream().anyMatch(t -> t.getTargetDate().equals(targetDate));
    }




}
