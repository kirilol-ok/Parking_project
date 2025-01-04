package org.example.parkinggui.symulator;

import java.util.ArrayList;

public class EmpDatabase {
    ArrayList<Object[]> empDatabase = new ArrayList<>();
    int nextId = 3;

    public EmpDatabase() {
        empDatabase.add(new Object[]{ "1", "Kiryl Baranouski", "admin", "kbaranou" });
        empDatabase.add(new Object[]{ "2", "Bartek Bieda", "admin", "bbieda" });
    }

    public void addEmployee(String name, String password, String passcode) {
        empDatabase.add(new Object[]{ this.nextId, name, password, passcode });
    }


}
