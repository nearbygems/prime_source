package kz.earbygems.ps.service.impl;

import kz.earbygems.ps.model.Task;
import kz.earbygems.ps.repository.TaskRepository;
import kz.earbygems.ps.service.AuthService;
import kz.earbygems.ps.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final AuthService authService;
  private final TaskRepository repository;

  @Override
  public List<Task> get(Integer offset, Integer limit, String sort) {

    var tasks = repository.findTasksByUsernameEquals(authService.currentUser(), PageRequest.of(offset, limit, Sort.by(sort)));

    if (tasks.hasContent()) {
      return tasks.getContent();
    }

    return Collections.emptyList();
  }

  @Override
  public Task getById(Long id) {
    return repository.findByIdAndUsername(id, authService.currentUser())
                     .orElseThrow(() -> new RuntimeException("No task with id `" + id + "`"));
  }

  @Override
  public Task upsert(Task task) {
    task.setUsername(authService.currentUser());
    return repository.save(task);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

}
