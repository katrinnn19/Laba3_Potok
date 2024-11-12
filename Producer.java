package com.company; 

public class Producer implements Runnable { 
    private final int itemNumbers; // Кількість елементів, які буде додавати продюсер
    private final Manager manager; // Об'єкт Manager для керування доступом до сховища
    private final int id; // Ідентифікатор продюсера

    public Producer(int itemNumbers, Manager manager, int id) { // Конструктор класу Producer
        this.itemNumbers = itemNumbers; // Ініціалізуємо кількість елементів
        this.manager = manager; // Ініціалізуємо об'єкт Manager
        this.id = id; // Ініціалізуємо ідентифікатор продюсера

        new Thread(this).start(); // Створюємо новий потік і запускаємо його для виконання методу run
    }

    @Override
    public void run() { // Метод run, який виконується в окремому потоці
        for (int i = 0; i < itemNumbers; i++) { // Цикл для додавання кожного елемента у сховище
            try {
                manager.full.acquire(); // Зменшуємо семафор full, перевіряючи, чи є місце для додавання нового елемента
                manager.access.acquire(); // Захоплюємо семафор access, щоб інші потоки не мали доступу до сховища

                manager.storage.add("item " + i); // Додаємо новий елемент у сховище
                System.out.println("Producer " + id + " Added item " + i); // Виводимо повідомлення про додавання елемента

                manager.access.release(); // Вивільняємо семафор access для інших потоків
                manager.empty.release(); // Збільшуємо семафор empty, вказуючи, що тепер є ще один доступний елемент для споживання
            } catch (InterruptedException e) { // Обробляємо можливе переривання потоку
                e.printStackTrace(); // Виводимо стек викликів у разі помилки
            }
        }
    }
}
