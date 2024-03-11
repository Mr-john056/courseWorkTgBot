package skypro.tgBotReminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.tgBotReminder.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findAllByLocalDateTime(LocalDateTime localDateTime);
}
