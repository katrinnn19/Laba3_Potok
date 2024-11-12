package com.company; 

public class Main { 

    public static void main(String[] args) { 
        Main main = new Main(); // Створюємо новий об'єкт Main
        int storageSize = 10; //  розмір сховища
        int itemNumbers = 30; // кількість елементів
        main.starter(storageSize, itemNumbers); 
    }

    private void starter(int storageSize, int itemNumbers) { 
        Manager manager = new Manager(storageSize); 
        int item = 0; // Ініціалізуємо лічильник item для підрахунку доданих елементів
        int i = 0; // Ініціалізуємо лічильник ітерацій i
        while (item < itemNumbers) { // Цикл, що працює, доки кількість оброблених елементів менша за itemNumbers
            int col = (int) Math.round(Math.random() * storageSize); // Генеруємо випадкову кількість col елементів для обробки
            if (itemNumbers - item > col) { // Перевіряємо, чи залишилося достатньо елементів для створення кола
                new Producer(col, manager, i); // Створюємо новий об'єкт Producer з випадковим числом col, передаємо manager і номер ітерації
                new Consumer(col, manager, i); // Створюємо новий об'єкт Consumer з тими ж параметрами
                item += col; // Збільшуємо загальну кількість оброблених елементів на col
            } else { // Якщо залишилося менше елементів, ніж кол
                new Producer(itemNumbers - item, manager, i); // Створюємо Producer з залишковою кількістю елементів
                new Consumer(itemNumbers - item, manager, i); // Створюємо Consumer з тією ж залишковою кількістю
                break; // Виходимо з циклу, оскільки всі елементи оброблено
            }
            i += 1; // Збільшуємо номер ітерації
            if (i == 20) { // Якщо досягнуто 20 ітерацій
                break; // Виходимо з циклу, щоб уникнути надмірної кількості ітерацій
            }
        }
    }
}
