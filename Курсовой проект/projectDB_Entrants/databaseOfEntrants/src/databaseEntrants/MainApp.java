package databaseEntrants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.prefs.Preferences;

import databaseEntrants.model.Entrants;
import databaseEntrants.model.EntrantsListWrapper;
import databaseEntrants.view.EntrantsEditDialogController;
import databaseEntrants.view.EntrantsOverviewController;
import databaseEntrants.view.LoginDialogController;
import databaseEntrants.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	public Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * Список абитуриентов
	 */
	private ObservableList<Entrants> entrantsData = FXCollections
			.observableArrayList();

	/**
	 * Конструктор по умолчанию
	 */
	public MainApp() {
		entrantsData.add(new Entrants("Єгоров", "Михайло","Віталійович",
				"МТ000001",61000,"Харків","вул. Сумська, 40",9.8,"Гімназія 4"));
		entrantsData.add(new Entrants("Михайлова", "Дарина","Володимирівна",
				"МТ000020",61000,"Харків","вул. Космічна, 12",9.6,"Гімназія 1"));
		entrantsData.add(new Entrants("Петров", "Ілля", "Олександрович",
				"МТ000300",49000,"Вінниця","вул. Перша, 7",10.8,"Ліцей 11"));
		entrantsData.add(new Entrants("Петрищев", "Володимир","Володимирович",
				"МТ004000",10000,"Київ","вул. Клочківська, 10",7.0,"Гімназія 3"));
		entrantsData.add(new Entrants("Оніщенко", "Анна","Романівна",
				"МТ050000",49000,"Харків","вул. Пушкінська, 47",9.7,"НВК 27"));
		entrantsData.add(new Entrants("Попов", "Олег", "Олексійович",
				"МТ600000",69000,"Краматорськ","вул. Громова, 32",11.5,"Ліцей 7"));
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("База даних Абітурієнт 2019");

		//загрузка иконки приложения
		this.primaryStage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));

		//вызов методов для загрузки корневого макета, основного макета и макета для авторизации
		initRootLayout();
		showEntrantsOverview();
		showLoginDialog();
	}

	/**
	 * Инициализация корневого макета и загрузка последних данных о всех абитуриентах
	 */
	public void initRootLayout() {
		try {
			//загрузка корневого слоя fxml файла
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//показ корневой сцены
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			//предоставление контроллеру доступа к приложению
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = getEntrantsFilePath();
		if (file != null) {
			loadEntrantsDataFromFile(file);
		}
	}

	/**
	 * Показ корневого макета
	 */
	public void showEntrantsOverview() {
		try {
			//загрузка макета
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EntrantsOverview.fxml"));
			AnchorPane entrantsOverview = (AnchorPane) loader.load();

			//возвращение информации об абитуриенте в корневой макет
			rootLayout.setCenter(entrantsOverview);

			//предоставление контроллеру доступ к приложению
			EntrantsOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Открытие окна для редактирования информации абитуриента
	 * @param entrants
	 * @return
	 */
	public boolean showEntrantsEditDialog(Entrants entrants) {
		try {
			//загрузка fxml файла и создание новой сцены
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/EntrantsEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Редагування даних");
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.getIcons().add(
					new Image("file:src/databaseEntrants/resources/images/icon.png"));
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			EntrantsEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setEntrants(entrants);
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

    /**
     * Открытие окна для добавления абитуриента
     * @param entrants
     * @return
     */
    public boolean showEntrantsAddDialog(Entrants entrants) {
        try {
            //загрузка fxml файла и создание новой сцены
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/EntrantsEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Додавання абітурієнта");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(
                    new Image("file:src/databaseEntrants/resources/images/icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EntrantsEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEntrants(entrants);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	/**
	 * Открытие окна для авторизации
	 * @return
	 */
	public boolean showLoginDialog() {
		try {
			//загрузка fxml файла и создание новой сцены
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/LoginDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage passwordStage = new Stage();
			passwordStage.setResizable(false);
			passwordStage.initModality(Modality.WINDOW_MODAL);
			passwordStage.initOwner(primaryStage);
			passwordStage.initStyle(StageStyle.UNDECORATED);

			//настройка эффекта
			DropShadow dropShadow = new DropShadow();
			dropShadow.setBlurType(BlurType.THREE_PASS_BOX);
			dropShadow.setColor(Color.BLACK);
			dropShadow.setWidth(21);
			dropShadow.setHeight(21);
			dropShadow.setRadius(10);
			dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setSpread(0);

			Scene scene = new Scene(page);
			passwordStage.setScene(scene);

			passwordStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					primaryStage.close();
				}
			});
			LoginDialogController controller = loader.getController();
			controller.setPasswordStage(passwordStage);

			passwordStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Получение пути к файлу
	 * @return
	 */
	public File getEntrantsFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Задает путь к загруженному файлу
	 * Путь сохраняется в реестре операционной системы
	 * @param file
	 */
	public void setEntrantsFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			//Обновление название сцены
			primaryStage.setTitle("База даних Абітурієнт 2019 - " + file.getName());
		} else {
			prefs.remove("filePath");

			//Обновление названия сцены
			primaryStage.setTitle("База даних Абітурієнт 2019");
		}
	}

	/**
	 * Данные текущей бд будут изменены
	 * Загрузка данных из указанного файла
	 * @param file
	 */
	public void loadEntrantsDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(EntrantsListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			//Чтение XML-файла из файла и удаление
			EntrantsListWrapper wrapper = (EntrantsListWrapper) um.unmarshal(file);
			entrantsData.clear();
			entrantsData.addAll(wrapper.getEntrants());

			//Сохранение пути файла в реестре
			setEntrantsFilePath(file);

		} catch (Exception e) { //ловит любое исключение
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
            alert.setTitle("Помилка");
            alert.setHeaderText("Не може завантажити дані з файлу:\n" + file.getPath());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            TextArea textArea = new TextArea(exceptionText);
            alert.showAndWait();
		}
	}

	/**
	 * Сохраняет текущие данные бд в указанный файл
	 * @param file
	 */
	public void savePefrsonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(EntrantsListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//упаковка информации о всех абитуриентах
			EntrantsListWrapper wrapper = new EntrantsListWrapper();
			wrapper.setEntrants(entrantsData);

			//сортировка и сохранение XML в файл
			m.marshal(wrapper, file);

			//сохраняет путь к файлу в реестр
			setEntrantsFilePath(file);

		} catch (Exception e) { //ловит разные ошибки

            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
            alert.setTitle("Помилка");
            alert.setHeaderText("Не може зберегти дані в файл:\n" + file.getPath());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            TextArea textArea = new TextArea(exceptionText);
            alert.showAndWait();
		}
	}

	/**
	 * Возвращает главную сцену
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * возвращает всех абитуриентов
	 * @return
	 */
	public ObservableList<Entrants> getEntrantsData() {
		return entrantsData;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}