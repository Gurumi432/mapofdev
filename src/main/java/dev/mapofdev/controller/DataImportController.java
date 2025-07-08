package dev.mapofdev.controller;

import dev.mapofdev.dto.ApiResponse;
import dev.mapofdev.dto.JobDataDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// DataImportController.java (새로 생성)
@RestController
@RequestMapping("/api/v1/data")
public class DataImportController {

  @PostMapping("/import/jobs")
  public ApiResponse importJobData(@RequestBody List<JobDataDto> jobs) {
    // 데이터팀 CSV 데이터 받아서 DB 저장
    return ApiResponse.success("Imported " + jobs.size() + " jobs");
  }
}

