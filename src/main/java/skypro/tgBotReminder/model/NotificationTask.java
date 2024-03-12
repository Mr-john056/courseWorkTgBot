package skypro.tgBotReminder.model;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "tasks")
public class NotificationTask {


    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "chat_id")
    private Long chatId;


    @Column(name = "message_text")
    private String messageText;


    @Column(name = "time_and_date")
    private LocalDateTime localDateTime;

    public NotificationTask(){
    }

    public NotificationTask(Long id, Long chatId, String messageText, LocalDateTime localDateTime) {
        this.id = id;
        this.chatId = chatId;
        this.messageText = messageText;
        this.localDateTime = localDateTime;
    }

    public NotificationTask(long chatId, String s) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(messageText, that.messageText) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, messageText, localDateTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", messageText='" + messageText + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }

}
