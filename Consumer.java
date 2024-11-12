package com.company; 

public class Consumer implements Runnable { 
    private final int itemNumbers; // Кількість елементів, які буде забирати споживач
    private final Manager manager; // Об'єкт Manager для керування доступом до сховища
    private final int id; // Ідентифікатор споживача

    public Consumer(int itemNumbers, Manager manager, int id) { // Конструктор класу Consumer
        this.itemNumbers = itemNumbers; // Ініціалізуємо кількість елементів для споживача
        this.manager = manager; // Ініціалізуємо об'єкт Manager для доступу до спільного сховища
        this.id = id; // Ініціалізуємо ідентифікатор споживача

        new Thread(this).start(); // Створюємо новий потік і запускаємо його для виконання методу run
    }

    @Override
    public void run() { // Метод run, який виконується в окремому потоці
        for (int i = 0; i < itemNumbers; i++) { // Цикл для вилучення кожного елемента зі сховища
            String item; // Змінна для збереження взятого елемента
            try {
                manager.empty.acquire(); // Зменшуємо семафор empty, перевіряючи, чи є елементи для вилучення
                Thread.sleep(100); // Затримка, що імітує час обробки елемента
                manager.access.acquire(); // Захоплюємо семафор access, щоб інші потоки не мали доступу до сховища

                item = manager.storage.get(0); // Отримуємо перший елемент із сховища
                manager.storage.remove(0); // Видаляємо отриманий елемент зі сховища
                System.out.println("Consumer " + id + " Took " + item); // Виводимо повідомлення про вилучення елемента

                manager.access.release(); // Вивільняємо семафор access для інших потоків
                manager.full.release(); // Збільшуємо семафор full, вказуючи, що тепер є додаткове місце для додавання елемента
            } catch (InterruptedException e) { // Обробляємо можливе переривання потоку
                e.printStackTrace(); // Виводимо стек викликів у разі помилки
            }
        }
    }
}
