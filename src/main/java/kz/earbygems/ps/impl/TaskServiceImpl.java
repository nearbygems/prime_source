package kz.earbygems.ps.impl;

import kz.earbygems.ps.model.Task;
import kz.earbygems.ps.repository.TaskRepository;
import kz.earbygems.ps.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  public Task create(Task task) {
    return taskRepository.save(task);
  }

}
