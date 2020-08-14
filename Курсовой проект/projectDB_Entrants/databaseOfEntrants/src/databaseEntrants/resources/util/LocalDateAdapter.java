package databaseEntrants.resources.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Адаптер для JAXB для конвертирования LocalDate и ISO 8601
 * Строковые представление даты, например 'yyyy-mm-dd'.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}
