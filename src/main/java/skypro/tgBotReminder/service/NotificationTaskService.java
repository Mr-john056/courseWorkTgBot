package skypro.tgBotReminder.service;

import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skypro.tgBotReminder.model.NotificationTask;
import skypro.tgBotReminder.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository){
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public List<NotificationTask> findByLocalDateTime(LocalDateTime localDateTime){
        return notificationTaskRepository.findAllByLocalDateTime(localDateTime);
    }

    public NotificationTask save(NotificationTask notificationTask){
        return  notificationTaskRepository.save(notificationTask);
    }


    public void delete(NotificationTask notificationTask) {
        notificationTaskRepository.delete(notificationTask);
    }
}
