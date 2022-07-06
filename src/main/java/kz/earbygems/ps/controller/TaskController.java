package kz.earbygems.ps.controller;

import kz.earbygems.ps.model.Task;
import kz.earbygems.ps.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService service;

  @GetMapping
  public List<Task> get(@RequestParam Integer offset,
                        @RequestParam Integer limit,
                        @RequestParam String sort) {
    return service.get(offset, limit, sort);
  }

  @GetMapping("/{id}")
  public Task getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PostMapping
  public Task upsert(@RequestBody Task task) {
    return service.upsert(task);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

}
