package kz.earbygems.ps.repository;

import kz.earbygems.ps.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

  @Autowired
  private TestEntityManager manager;

  @Autowired
  private TaskRepository repository;

  @Test
  public void findByIdAndUsername_thenExpectCorrectTask() {

    var task = new Task();
    task.setDate(LocalDate.of(2022, 8, 27));
    task.setDescription("description");
    task.setDone(true);
    task.setUsername("username");

    var created = manager.persist(task);

    //
    //
    var result = repository.findByIdAndUsername(created.getId(), task.getUsername()).orElse(null);
    //
    //

    assertNotNull(result);
    assertEquals(result.getId(), created.getId());
    assertEquals(result.getDate(), task.getDate());
    assertEquals(result.getDescription(), task.getDescription());
    assertTrue(result.isDone());
    assertEquals(result.getUsername(), task.getUsername());

  }

  @Test
  public void findTasksByUsernameEquals_thenExpectCorrectTasks() {

    var usernameOK = "username";

    var taskOk1 = new Task();
    taskOk1.setDate(LocalDate.of(2022, 8, 27));
    taskOk1.setDescription("description1");
    taskOk1.setDone(true);
    taskOk1.setUsername(usernameOK);

    var idOk1 = manager.persist(taskOk1).getId();

    var taskOk2 = new Task();
    taskOk2.setDate(LocalDate.of(2022, 8, 25));
    taskOk2.setDescription("description2");
    taskOk2.setDone(false);
    taskOk2.setUsername(usernameOK);

    var idOk2 = manager.persist(taskOk2).getId();

    var usernameNo = "usernameNo";

    var taskNo = new Task();
    taskNo.setDate(LocalDate.of(2022, 5, 29));
    taskNo.setDescription("description3");
    taskNo.setDone(true);
    taskNo.setUsername(usernameNo);
    manager.persist(taskNo);

    //
    //
    var page = repository.findTasksByUsername(usernameOK, PageRequest.of(0, 5, Sort.by(Task.Fields.id)));
    //
    //

    assertNotNull(page);
    assertTrue(page.hasContent());

    var list = page.getContent();

    assertEquals(list.size(), 2);

    var map = list.stream().collect(Collectors.toMap(Task::getId, t -> t));

    assertTrue(map.containsKey(idOk1));
    {
      var task = map.get(idOk1);
      assertEquals(task.getDate(), taskOk1.getDate());
      assertEquals(task.getDescription(), taskOk1.getDescription());
      assertTrue(task.isDone());
      assertEquals(task.getUsername(), taskOk1.getUsername());
    }

    assertTrue(map.containsKey(idOk2));
    {
      var task = map.get(idOk2);
      assertEquals(task.getDate(), taskOk2.getDate());
      assertEquals(task.getDescription(), taskOk2.getDescription());
      assertFalse(task.isDone());
      assertEquals(task.getUsername(), taskOk2.getUsername());
    }

  }

  @Test
  public void findTasksByUsernameEquals_thenExpectCorrectPage() {

    var username = "username";

    var taskOk1 = new Task();
    taskOk1.setDate(LocalDate.of(2022, 8, 27));
    taskOk1.setDescription("description1");
    taskOk1.setDone(true);
    taskOk1.setUsername(username);

    var idOk1 = manager.persist(taskOk1).getId();

    var taskOk2 = new Task();
    taskOk2.setDate(LocalDate.of(2022, 8, 28));
    taskOk2.setDescription("description2");
    taskOk2.setDone(false);
    taskOk2.setUsername(username);

    var idOk2 = manager.persist(taskOk2).getId();

    var taskNo = new Task();
    taskNo.setDate(LocalDate.of(2022, 8, 29));
    taskNo.setDescription("description3");
    taskNo.setDone(true);
    taskNo.setUsername(username);
    manager.persist(taskNo);

    //
    //
    var page = repository.findTasksByUsername(username, PageRequest.of(0, 2, Sort.by(Task.Fields.date)));
    //
    //

    assertNotNull(page);
    assertTrue(page.hasContent());

    var list = page.getContent();

    assertEquals(list.size(), 2);

    var map = list.stream().collect(Collectors.toMap(Task::getId, t -> t));

    assertTrue(map.containsKey(idOk1));
    {
      var task = map.get(idOk1);
      assertEquals(task.getDate(), taskOk1.getDate());
      assertEquals(task.getDescription(), taskOk1.getDescription());
      assertTrue(task.isDone());
      assertEquals(task.getUsername(), taskOk1.getUsername());
    }

    assertTrue(map.containsKey(idOk2));
    {
      var task = map.get(idOk2);
      assertEquals(task.getDate(), taskOk2.getDate());
      assertEquals(task.getDescription(), taskOk2.getDescription());
      assertFalse(task.isDone());
      assertEquals(task.getUsername(), taskOk2.getUsername());
    }

  }

}
