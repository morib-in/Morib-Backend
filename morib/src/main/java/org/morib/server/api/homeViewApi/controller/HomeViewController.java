package org.morib.server.api.homeViewApi.controller;

import lombok.RequiredArgsConstructor;
import org.morib.server.api.homeViewApi.service.HomeViewFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class HomeViewController {
    private final HomeViewFacade homeViewFacade;

    // 홈뷰 전체 조회
    @GetMapping("/home")
    public ResponseEntity<?> fetchHome() {
        homeViewFacade.fetchHome();
        return null;
    }

    // 오늘 나의 작업시간 조회
    @GetMapping("/today")
    public ResponseEntity<?> fetchTotalTimeOfToday() {
        homeViewFacade.fetchUserTimer();
        return null;
    }

    // 할일 추가 후 타이머 시작
    @PostMapping("/timers/start")
    public ResponseEntity<?> startTimer() {
        homeViewFacade.startTimer();
        return null;
    }


}
