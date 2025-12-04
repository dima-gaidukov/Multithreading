package com.example;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private final List<Integer> list;
    private final String name;

    public CalculateSumTask(List<Integer> list, String name) {
        this.list = list;
        this.name = name;
    }


    @Override
    public Integer call() throws Exception {
        System.out.println("Имя задачи: " + name + " " + "Имя потока: " + Thread.currentThread().getName());

        int sum = 0;
        for(Integer number : list){
            sum += number;
            Thread.sleep(500);
        }

        return sum;
    }


}
