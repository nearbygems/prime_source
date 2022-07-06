package kz.earbygems.ps.service;

import kz.earbygems.ps.model.Task;

import java.util.List;

public interface TaskService {

  List<Task> get(Integer offset, Integer limit, String sort);

  Task getById(Long id);

  Task upsert(Task task);

  void delete(Long id);

}
