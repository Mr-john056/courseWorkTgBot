package skypro.tgBotReminder.cron;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import skypro.tgBotReminder.model.NotificationTask;
import skypro.tgBotReminder.service.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class NotificationCron {

    private final NotificationTaskService notificationTaskService;

    private final TelegramBot telegramBot;

    public NotificationCron(NotificationTaskService notificationTaskService, TelegramBot telegramBot) {
        this.notificationTaskService = notificationTaskService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void task() {
        for (NotificationTask notificationTask : notificationTaskService.findByLocalDateTime(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
            telegramBot.execute(new SendMessage(notificationTask.getChatId(),
                    notificationTask.getMessageText()));
            notificationTaskService.delete(notificationTask);
        }
    }
}
