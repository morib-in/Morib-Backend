package org.morib.server.domain.task.application;

import lombok.RequiredArgsConstructor;

import org.morib.server.api.homeView.vo.TaskWithTimers;
import org.morib.server.domain.task.TaskManager;
import org.morib.server.domain.task.infra.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClassifyTaskServiceImpl implements ClassifyTaskService {
    private final TaskManager taskManager;

    @Override
    public List<TaskWithTimers> classifyTimerByTask(LocalDate date, List<Task> tasks) {
        return tasks.stream().map(task -> taskManager.classifyTimerByTask(date, task)).toList();
    }

    @Override
    public LinkedHashSet<Task> sortTasksByCreatedAt(Set<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedAt)) // createdAt 필드를 기준으로 정렬
                .collect(Collectors.toCollection(LinkedHashSet::new)); // LinkedHashSet으로 수집
    }
}
