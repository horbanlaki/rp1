package databaseEntrants.model;

import java.time.LocalDate;

import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import databaseEntrants.resources.util.LocalDateAdapter;

public class Entrants {

	private final StringProperty lastName;
	private final StringProperty firstName;
	private final StringProperty middleName;
	private final ObjectProperty<LocalDate> birthday;
	private final StringProperty passport;
	private final IntegerProperty postalCode;
	private final StringProperty city;
	private final StringProperty address;
	private final DoubleProperty averageScore;
	private final StringProperty school;

	/**
	 * Конструктор по умолчанию
	 */
	public Entrants() {
		this(null, null, null,
				null,0, null, null,0.0, null);
	}

	/**
	 * Инициализация данных
	 * @param lastName
	 * @param firstName
	 * @param middleName
	 * @param passport
	 * @param postalCode
	 * @param city
	 * @param address
	 */
	public Entrants(String lastName, String firstName, String middleName, String passport,
					Integer postalCode, String city, String address, Double averageScore, String school) {
		//Ссылка на текущий объект
		this.lastName = new SimpleStringProperty(lastName);
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.passport = new SimpleStringProperty(passport);
		this.postalCode = new SimpleIntegerProperty(postalCode);
		this.city = new SimpleStringProperty(city);
		this.address = new SimpleStringProperty(address);
		this.averageScore = new SimpleDoubleProperty(averageScore);
		this.school = new SimpleStringProperty(school);
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(2001, 1, 1));
	}

	/**
	 * Last name
	 * @return
	 */
	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	/**
	 * First name
	 * @return
	 */
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	/**
	 * Middle name
	 * @return
	 */
	public String getMiddleName() {
		return middleName.get();
	}

	public void setMiddleName(String middleName) {
		this.middleName.set(middleName);
	}
	
	public StringProperty middleNameProperty() {
		return middleName;
	}

	/**
	 * Passport
	 * @return
	 */
	public String getPassport() {
		return passport.get();
	}

	public void setPassport(String passport) {
		this.passport.set(passport);
	}

	public StringProperty passportProperty() {
		return passport;
	}

	/**
	 * Postal code
	 * @return
	 */
	public int getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}

	public IntegerProperty postalCodeProperty() {
		return postalCode;
	}

	/**
	 * City
	 * @return
	 */
	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {this.city.set(city);}

	public StringProperty cityProperty() {
		return city;
	}

	/**
	 * Address
	 * @return
	 */
	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) { this.address.set(address); }

	public StringProperty addressProperty() { return address; }

	/**
	 * Average score
	 * @return
	 */
	public Double getAverageScore() { return averageScore.get(); }

	public void setAverageScore(Double averageScore) { this.averageScore.set(averageScore); }

	public DoubleProperty averageScore() { return averageScore; }

	/**
	 * School
	 * @return
	 */
	public String getSchool() { return school.get(); }

	public void setSchool(String school) { this.school.set(school); }

	public StringProperty stringProperty() { return school; }

	/**
	 * Birthday
	 * @return
	 */
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}
	
	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}
}