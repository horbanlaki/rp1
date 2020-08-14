package databaseEntrants.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import databaseEntrants.model.Entrants;
import databaseEntrants.resources.util.DateUtil;

/**
 * Диалоговое окно для редактирования данных абитуриента
 */
public class EntrantsEditDialogController {

    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField passportField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField averageScoreField;
    @FXML
    private TextField schoolField;


    private Stage dialogStage;
    private Entrants entrants;
    private boolean okClicked = false;

    /**
     * Инициализация класса контроллера, метод вызывается автоматически
     * после fxml файл загружен
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену, как диалоговую
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Возвращает абитуриента, которого нужно изменить.
     * @param entrants
     */
    public void setEntrants(Entrants entrants) {
        this.entrants = entrants;

        lastNameField.setText(entrants.getLastName());
        firstNameField.setText(entrants.getFirstName());
        middleNameField.setText(entrants.getMiddleName());
        birthdayField.setText(DateUtil.format(entrants.getBirthday()));
        birthdayField.setPromptText("дд.мм.рррр");
        passportField.setText(entrants.getPassport());
        postalCodeField.setText(Integer.toString(entrants.getPostalCode()));
        cityField.setText(entrants.getCity());
        addressField.setText(entrants.getAddress());
        averageScoreField.setText(Double.toString(entrants.getAverageScore()));
        schoolField.setText(entrants.getSchool());
    }

    /**
     * Возвращает true если пользователь нажал OK, в противном случае false
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается когда пользователь нажал OK
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            entrants.setLastName(lastNameField.getText());
            entrants.setFirstName(firstNameField.getText());
            entrants.setMiddleName(middleNameField.getText());
            entrants.setBirthday(DateUtil.parse(birthdayField.getText()));
            entrants.setPassport(passportField.getText());
            entrants.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            entrants.setCity(cityField.getText());
            entrants.setAddress(addressField.getText());
            entrants.setAverageScore(Double.parseDouble(averageScoreField.getText()));
            entrants.setSchool(schoolField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается когда пользователь нажал отмена
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет текстовые поля на ввод данных
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Невірне прізвище!\n";
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Невірне ім'я!\n";
        }
        if (middleNameField.getText() == null || middleNameField.getText().length() == 0) {
            errorMessage += "Невірне по батькові!\n";
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Невірна дата народження!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Невірна дата народження. Використовуйте формат дд.мм.рррр!\n";
            }
        }
        if (passportField.getText() == null || passportField.getText().length() == 0) {
            errorMessage += "Невірний паспорт!\n";
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "Невірний поштовий індекс!\n";
        } else {
            // попытка преобразовать почтовый индекс в int
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Невірний поштовий індекс (має бути цілим числом)!\n";
            }
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Невірне місто!\n";
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "Неверна вулиця!\n";
        }
        if (averageScoreField.getText() == null || averageScoreField.getText().length() == 0 ||
                Double.parseDouble(averageScoreField.getText())>12.0) {
            errorMessage += "Невірний середній бал, введіть через крапку!\n";
        } else {
            // попытка преобразовать средний балл в double
            try {
                Double.parseDouble(averageScoreField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Невірний середній бал (має бути дійсним числом)!\n";
            }
        }
        if (schoolField.getText() == null || schoolField.getText().length() == 0) {
            errorMessage += "Невірна школа!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //Показ сообщения об ошибке
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
            alert.setTitle("Попередження");
            alert.setHeaderText("Виправте недійсні поля");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}