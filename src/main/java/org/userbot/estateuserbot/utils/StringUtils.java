package org.userbot.estateuserbot.utils;

import org.userbot.estateuserbot.dto.ChatInfo;
import org.userbot.estateuserbot.dto.TelegramMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static String trimString(String text){
        if(text == null) return  null;
        text = text.replace("\"", "")       // убираем кавычки
                .replace("\n", " ")       // убираем перевод строки (Linux/Unix)
                .replace("\r", " ")       // убираем перевод строки (Windows)
                .replaceAll("\\s+", " ") // заменяем несколько пробелов на один
                .trim();                 // удаляем пробелы в начале и в конце
        if(text.length() >200){
            return text.substring(0,200);
        }
        return text;
    }
    public static String formatNotification(TelegramMessage message) {
        String chatTitle = message.getChat().getTitle();
        String groupLink = generateGroupLink(message.getChat()); // новая функция
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return String.format(
                "🔔 *Новый лид!*\n\n" +
                        "🗓 Дата: %s\n" +
                        "👤 Отправитель: %s\n" +
                        "💬 Сообщение: %s\n" +
                        "💬 Чат: %s\n" +
                        "🔗 Ссылка: %s\n" +
                        "📊 Потенциал: %d\n" +
                        "📊 Вероятность: %.3f",
                dateTime,
                message.getSender().getDisplayName(),
                message.getText(),
                chatTitle,
                groupLink,
                message.getLeadPotential(),
                message.getLeadProbability()
        );
    }

    public static String generateGroupLink(ChatInfo chat) {
        if (chat.getTitle() != null && chat.getUsername() !=null && !chat.getUsername().isEmpty()) {
            return "https://t.me/" + chat.getUsername();
        } else {
            return "Ссылка недоступна (приватный чат)";
        }
    }
}
