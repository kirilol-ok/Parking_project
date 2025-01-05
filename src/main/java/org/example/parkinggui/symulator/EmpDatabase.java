package org.example.parkinggui.symulator;

import java.util.ArrayList;

public class EmpDatabase {
    ArrayList<Object[]> empDatabase = new ArrayList<>();
    int nextId = 3;

    public EmpDatabase() {
        empDatabase.add(new Object[]{ "1", "Kiryl Baranouski", "admin", "kbaranou" });
        empDatabase.add(new Object[]{ "2", "Bartek Bieda", "admin", "bbieda" });
    }

    public void addEmployee(String name, String role, String password) {
        empDatabase.add(new Object[]{ this.nextId, name, role, password});
        this.nextId++;
    }

    public boolean removeEmployeeById(String id) {
        for (int i = 0; i < empDatabase.size(); i++) {
            Object[] row = empDatabase.get(i);
            if (row[0].equals(id)) { // Проверка ID в первом элементе строки
                empDatabase.remove(i);
                return true; // Успешно удалено
            }
        }
        return false; // ID не найден
    }

    public void printDatabase() {
        for (Object[] row : empDatabase) {
            for (Object value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EmpDatabase db = new EmpDatabase();
        db.printDatabase();

        System.out.println("\nДобавляем сотрудника:");
        db.addEmployee("John Doe", "user", "jdoe");
        db.printDatabase();

        System.out.println("\nУдаляем сотрудника с ID 2:");
        boolean removed = db.removeEmployeeById("2");
        if (removed) {
            System.out.println("Сотрудник с ID 2 удален.");
        } else {
            System.out.println("Сотрудник с ID 2 не найден.");
        }
        db.printDatabase();
    }
}
