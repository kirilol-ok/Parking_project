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
            if (row[0].equals(id)) {
                empDatabase.remove(i);
                return true;
            }
        }
        return false;
    }

    public void printDatabase() {
        for (Object[] row : empDatabase) {
            for (Object value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    public ArrayList<Object[]> getEmpDatabase() {
        return empDatabase;
    }

    public static void main(String[] args) {

    }
}
