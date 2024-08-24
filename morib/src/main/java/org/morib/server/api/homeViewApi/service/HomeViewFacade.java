package org.morib.server.api.homeViewApi.service;

import lombok.RequiredArgsConstructor;
import org.morib.server.api.homeViewApi.dto.fetch.*;
import org.morib.server.api.homeViewApi.service.aggregate.timer.AggregateTimerService;
import org.morib.server.api.homeViewApi.vo.CategoriesByDate;
import org.morib.server.api.homeViewApi.vo.CombinedByDate;
import org.morib.server.api.homeViewApi.vo.TaskWithElapsedTime;
import org.morib.server.domain.category.application.ClassifyCategoryService;
import org.morib.server.domain.task.application.ClassifyTaskService;
import org.morib.server.domain.timer.application.ClassifyTimerService;
import org.morib.server.api.homeViewApi.service.create.task.CreateTaskService;
import org.morib.server.api.homeViewApi.service.create.timer.CreateTimerService;
import org.morib.server.api.homeViewApi.service.create.todo.CreateTodoService;
import org.morib.server.domain.category.application.FetchCategoryService;
import org.morib.server.domain.task.application.FetchTaskService;
import org.morib.server.domain.timer.application.FetchTimerService;
import org.morib.server.domain.task.application.ToggleTaskStatusService;
import org.morib.server.domain.category.infra.Category;
import org.morib.server.domain.task.infra.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeViewFacade {
    private final FetchCategoryService fetchCategoryService;
    private final ClassifyCategoryService classifyCategoryService;
    private final FetchTaskService fetchTaskService;
    private final ClassifyTaskService classifyTaskService;
    private final FetchTimerService fetchTimerService;
    private final ClassifyTimerService classifyTimerService;
    private final AggregateTimerService aggregateTimerService;
    private final CreateTaskService createTaskService;
    private final ToggleTaskStatusService toggleTaskStatusService;
    private final CreateTimerService createTimerService;
    private final CreateTodoService createTodoService;

    public List<HomeViewResponseDto> fetchHome(HomeViewRequestDto request) {
        List<CategoriesByDate> categories = classifyCategoryService.classifyByDate(
                fetchCategoryService.fetchByUserIdInRange(request.userId(), request.startDate(), request.endDate()),
                request.startDate(), request.endDate());
        List<CombinedByDate> combined = classifyCategoryService.classifyTaskByCategory(categories);
        List<TaskWithElapsedTime> tasks = combined.stream()
                .flatMap(combinedByDate -> combinedByDate.getCombined().stream()
                .flatMap(tasksByCategory -> classifyTaskService.classifyTimerByTask(combinedByDate.getDate(), tasksByCategory.getTasks()).stream()
                )).toList();
        return convertToHomeViewResponseDto(createFetchCombinedDtoMap(combined, tasks));
    }

    private Map<LocalDate, List<FetchCombinedDto>> createFetchCombinedDtoMap(
            List<CombinedByDate> combined, List<TaskWithElapsedTime> tasks) {
        return combined.stream()
                .collect(Collectors.toMap(
                        CombinedByDate::getDate,
                        combinedByDate -> combinedByDate.getCombined().stream()
                                .map(entry -> {
                                    FetchCategoryDto categoryDto = FetchCategoryDto.of(entry.getCategory());
                                    List<FetchTaskDto> taskDtos = createFetchTaskDtoList(entry.getTasks(), tasks);
                                    return FetchCombinedDto.of(categoryDto, taskDtos);
                                })
                                .collect(Collectors.toList())
                ));
    }

    private List<FetchTaskDto> createFetchTaskDtoList(List<Task> tasks, List<TaskWithElapsedTime> taskWithElapsedTimes) {
        return tasks.stream()
                .map(task -> {
                    int elapsedTime = taskWithElapsedTimes.stream()
                            .filter(taskWithElapsedTime -> taskWithElapsedTime.getTask().equals(task))
                            .map(TaskWithElapsedTime::getElapsedTime)
                            .findFirst()
                            .orElse(0);
                    return FetchTaskDto.of(task, elapsedTime);
                })
                .collect(Collectors.toList());
    }

    private List<HomeViewResponseDto> convertToHomeViewResponseDto(Map<LocalDate, List<FetchCombinedDto>> combinedDtoMap) {
        return combinedDtoMap.entrySet().stream()
                .map(entry -> HomeViewResponseDto.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void fetchUserTimer() {
        fetchTaskService.fetch();
        fetchTimerService.fetch();
        aggregateTimerService.aggregate();
    }

    public void createTask() {
        createTaskService.create();
    }

    public void toggleTaskStatus() {
        fetchTaskService.fetch();
        toggleTaskStatusService.toggle();
    }

    public void startTimer() {
        createTodoService.create();
        createTimerService.create();
    }
}
