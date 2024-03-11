package skypro.tgBotReminder.listener;


import com.fasterxml.jackson.core.json.DupDetector;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import skypro.tgBotReminder.model.NotificationTask;
import skypro.tgBotReminder.repository.NotificationTaskRepository;
import skypro.tgBotReminder.service.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger((TelegramBotUpdatesListener.class));

    private final TelegramBot telegramBot;

    private final NotificationTaskService notificationTaskService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskService notificationTaskService) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
    }

    private static final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String text = update.message().text();
            long chatId = update.message().chat().id();
            Matcher matcher = PATTERN.matcher(text);

            if ("/start".equals(text)) {
                telegramBot.execute(new SendMessage(chatId, "Привет, "
                        + update.message().chat().firstName()
                        + "! Это бот-напоминалка!" +
                        "Чтобы создать новое напоминание, напишите его в формате" +
                        " \"01..01.2022 20:00 Сделать домашнюю работу\""));
            } else if (matcher.matches()) {
                String messageText = matcher.group(3);
                LocalDateTime localDateTime = LocalDateTime.parse(matcher.group(1),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                NotificationTask notificationTask = new NotificationTask();
                notificationTask.setChatId(chatId);
                notificationTask.setMessageText(messageText);
                notificationTask.setLocalDateTime(localDateTime);
                notificationTaskService.save(notificationTask);
                telegramBot.execute(new SendMessage(chatId, "Ваше напоминание добавлено!"));
            } else {
                telegramBot.execute(new SendMessage(chatId, "Не удалось распознать ваше сообщение!" +
                        "Чтобы создать новое напоминание, напишите его в формате" +
                        " \"01.01.2022 20:00 Сделать домашнюю работу\""));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
