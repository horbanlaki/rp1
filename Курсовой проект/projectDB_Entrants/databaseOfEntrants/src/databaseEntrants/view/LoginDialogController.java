package databaseEntrants.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Диалоговое окно для авторизации
 */
public class LoginDialogController {

    @FXML
    private TextField passworldField;

    private Stage passwordStage;
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
     * @param passwordStage
     */
    public void setPasswordStage(Stage passwordStage) {
        this.passwordStage=passwordStage;
    }

    /**
     * Возвращает true если пользователь нажал Login, в противном случае false
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается когда пользователь нажал Login
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            passwordStage.close();
        }
    }

    /**
     * Проверяет правильность ввода пароля
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (!passworldField.getText().equals("root") || passworldField.getText() == null || passworldField.getText().length() == 0)
        {
            errorMessage += "Невірний пароль!";
        }
        if (errorMessage.length() == 0){
            return true;
        } else {
            //Показ сообщения об ошибке
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/databaseEntrants/resources/images/icon.png"));
            alert.setTitle("Помилка");
            alert.setHeaderText("Пароль недійсний.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}