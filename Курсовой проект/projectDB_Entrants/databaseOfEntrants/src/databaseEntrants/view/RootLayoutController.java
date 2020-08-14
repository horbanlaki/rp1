package databaseEntrants.view;

import databaseEntrants.MainApp;

/**
 * Контролер для корневого макета. В корневом макете находятся основные макеты приложения
 */
public class RootLayoutController {

    /**
     * Ссылка на основное приложение
     */
    private MainApp mainApp;

    /**
     * Вызывается основное приложение, чтобы вернуть себе ссылку
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}