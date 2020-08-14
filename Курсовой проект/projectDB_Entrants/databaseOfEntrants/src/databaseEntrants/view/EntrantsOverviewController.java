package databaseEntrants.view;

import databaseEntrants.MainApp;
import databaseEntrants.model.Entrants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import databaseEntrants.resources.util.DateUtil;

import java.io.File;

public class EntrantsOverviewController {
    @FXML
    private TableView<Entrants> entrantsTable;
    @FXML
    private TableColumn<Entrants, String> firstNameColumn;
    @FXML
    private TableColumn<Entrants, String> lastNameColumn;

	@FXML
	private Label lastNameLabel;
	@FXML
    private Label firstNameLabel;
    @FXML
	private Label middleNameLabel;
	@FXML
	private Label passportLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
    private Label cityLabel;
	@FXML
    private Label addressLabel;
	@FXML
    private Label averageScoreLabel;
	@FXML
	private Label schoolLabel;
    @FXML
    private Label birthdayLabel;

    //Ссылка на основное приложение
    private MainApp mainApp;

	/**
	 * Конструктор
	 * Конструктор вызывается методом инициализации
	 */
	public EntrantsOverviewController() {
    }

	/**
	 * Инициализация класса контроллера. Этот метод вызывается автоматически
	 * после файл fxml будет загружен
	 */
	@FXML
    private void initialize() {
    	//Инициализация таблицы Entrants с двумя колонками
        firstNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().lastNameProperty());
        //Очистка персональных данных
        showEntrantsDetails(null);
        //Получение об изминениях и показ персональных данных которые были изминенные
		entrantsTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showEntrantsDetails(newValue));
    }

	/**
	 * Вызывается основным приложением, чтобы вернуть себе ссылку
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //Добавить данные наблюдаемого списка в таблицу
        entrantsTable.setItems(mainApp.getEntrantsData());
    }

	/**
	 * Заполняет все текстовые поля, чтобы показать подробности об абитуриенте
	 * Если указанное лицо имеет значение NULL, все текстовые поля будут удалены
	 * @param entrants
	 */
	public void showEntrantsDetails(Entrants entrants) {
    	if (entrants != null) {
    		//Заполнить labels информацией из объекта entrants.
    		firstNameLabel.setText(entrants.getFirstName());
    		lastNameLabel.setText(entrants.getLastName());
			middleNameLabel.setText(entrants.getMiddleName());
			birthdayLabel.setText(DateUtil.format(entrants.getBirthday()));
			passportLabel.setText(entrants.getPassport());
			postalCodeLabel.setText(Integer.toString(entrants.getPostalCode()));
			cityLabel.setText(entrants.getCity());
			addressLabel.setText(entrants.getAddress());
			averageScoreLabel.setText(Double.toString(entrants.getAverageScore()));
			schoolLabel.setText(entrants.getSchool());
    	} else {
    		//Entrants имеет null, удалить весь текст
    		firstNameLabel.setText("");
    		lastNameLabel.setText("");
			middleNameLabel.setText("");
			birthdayLabel.setText("");
			passportLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			addressLabel.setText("");
			averageScoreLabel.setText("");
			schoolLabel.setText("");
    	}
    }

    /**
     * Вызов окна с предуприждением если нужно удалить абитуриента, а в списке он не выбран
     */
    private void handleDeleteError() {
        //ничего не выбрано
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
        alert.setTitle("Попередження");
        alert.setHeaderText("Абітуриент не обран");
        alert.setContentText("Будь ласка оберіть абітурієнта в таблиці.");
        alert.showAndWait();
    }

    /**
	 * Создание пустой бд
	 */
	@FXML
	private void handleNew(){
		mainApp.getEntrantsData().clear();
		mainApp.setEntrantsFilePath(null);
	}

	/**
	 * Открывает Проводник, чтобы позволить пользователю выбрать бд для загрузки
	 */
	@FXML
	private void handleOpen(){
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFiltr = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFiltr);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file!=null){
			mainApp.loadEntrantsDataFromFile(file);
		}
	}

	/**
	 * Сохраняет файл бд, который открыт.
	 * Если нету открытого файла, отображается диалоговое окно "Сохранить как..."
	 */
	@FXML
	private void handleSave(){
		File entrantsFile = mainApp.getEntrantsFilePath();
		if (entrantsFile!= null){
			mainApp.savePefrsonDataToFile(entrantsFile);
		} else {
			handleSaveAs();
		}
	}

	/**
	 * Открытие Проводника чтобы позволить пользователю выбрать файл для сохранения
	 */
	@FXML
	private void handleSaveAs(){
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)","*.xml");
		fileChooser.getExtensionFilters().add(extensionFilter);

		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file!=null){
			if(!file.getPath().endsWith(".xml")){
				file = new File(file.getPath()+".xml");
			}
			mainApp.savePefrsonDataToFile(file);
		}
	}

	/**
	 * Закрытие приложения
	 */
	@FXML
	private void handleExit(){
		System.exit(0);
	}

	/**
	 * Открытие диалогового окна "О программе"
	 */
	@FXML
	private void handleAbout(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
		alert.setTitle("База даних Абітурієнт 2019");
		alert.setHeaderText("Про програму");
		alert.setContentText("Автор курсового проекту: Горбань Лакі" + " студентка 2 курсу 1 групи КН");
		Image image = new Image("file:src/databaseEntrants/resources/images/about.jpg");
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);
		alert.showAndWait();
	}

	/**
	 * Добавление нового абитуриента через Menu
	 * Открывается диалоговое окно для добавления нового абитуриента
	 */
	@FXML
	private void addEntrants(){
		Entrants tempEntrants = new Entrants();
		boolean okClicked = mainApp.showEntrantsAddDialog(tempEntrants);
		if (okClicked) {
			mainApp.getEntrantsData().add(tempEntrants);
		}
	}

	/**
	 * Редактирование абитуриента через Menu
	 * Открывется диалоговое окно редактирования сведений для выбраного абитуриента
	 */
	@FXML
	private void editEntrants(){
		Entrants selectedEntrants = entrantsTable.getSelectionModel().getSelectedItem();
		if (selectedEntrants != null) {
			boolean okClicked = mainApp.showEntrantsEditDialog(selectedEntrants);
			if (okClicked) {
				showEntrantsDetails(selectedEntrants);
			}
		} else {
			//ничего не выбрано
			handleDeleteError();
		}
	}

	/**
	 * Удаление абитуриента через Menu
	 */
	@FXML
	private void deleteEntrants(){
		int selectedIndex = entrantsTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			entrantsTable.getItems().remove(selectedIndex);
		} else {
			handleDeleteError();
		}
	}


}