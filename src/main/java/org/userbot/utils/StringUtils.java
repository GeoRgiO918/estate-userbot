package org.userbot.utils;

import org.userbot.dto.TelegramMessage;

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
    public static String formatNotification(TelegramMessage message){
        return String.format(
                "🔔 Новый лид!\n\n" +
                        "👤 Отправитель: %s\n" +
                        "💬 Сообщение: %s\n" +
                        "💬 Чат: %s\n" +
                        "📊 Потенциал: %d\n" +
                        "📊 Вероятность: %.3f",
                message.getSender().getDisplayName(),
                message.getText(),
                message.getChat().getTitle(),
                message.getLeadPotential(),
                message.getLeadProbability()
        );

    }
}
