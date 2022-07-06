package kz.earbygems.ps.repository;

import kz.earbygems.ps.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

  Optional<Task> findByIdAndUsername(Long id, String username);

  Page<Task> findTasksByUsernameEquals(String username, Pageable pageable);

}
