package ru.litres.tests.data;

public enum MainMenuButtons {
    NEW_BOOKS("Новинки"),
    POPULAR("Популярное"),
    AUDIOBOOKS("Аудиокниги"),
    WHATS_READ("Что почитать?"),
    SELECTIONS("Подборки"),
    SELF_MAKE("Самиздат");

    String title;

    MainMenuButtons(String title) {
    this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}