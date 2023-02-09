package com.example.restautomatique;

import com.example.restautomatique.controller.RestauController;

public class Chronometre extends Thread{

    public int value;

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }

            synchronized (this) {
                RestauController restau = new RestauController();
                value++;
                System.out.println("Valeur du chrono: " + value);
                //restau.changeChrono(Integer.toString(value));
            }
        }
    }

    public int getValue() {
        return value;
    }
}
