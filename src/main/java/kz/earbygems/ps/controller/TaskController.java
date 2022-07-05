package kz.earbygems.ps.controller;

import kz.earbygems.ps.model.Task;
import kz.earbygems.ps.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService service;

  @PostMapping("/tasks")
  public Task create(@RequestBody Task task) {
    return service.create(task);
  }

}
