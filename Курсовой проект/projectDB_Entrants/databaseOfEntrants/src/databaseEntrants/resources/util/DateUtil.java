package databaseEntrants.resources.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Вспомогательные функции для обработки дат
 */
public class DateUtil {

    /**
     * Формат даты
     */
	private static final String DATE_PATTERN = "dd.MM.yyyy";

	private static final DateTimeFormatter DATE_FORMATTER = 
			DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Возвращает отформатированную даты
     * @param date
     * @return
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Преобразовывает дату в определенный формат
     * Возвращает null если строка не конвертирована
     * @param dateString
     * @return
     */
    public static LocalDate parse(String dateString) {
        try {
        	return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Проверяет строку, является ли дата действительная
     * @param dateString
     * @return
     */
    public static boolean validDate(String dateString) {
    	return DateUtil.parse(dateString) != null;
    }
}
