package com.example;
import java.util.*;

public class Main {

    public static void main(String[] args) {


        DataProcessor processor = new DataProcessor(10);

        List<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            String name = processor.submitTask(numbers);
            list.add(name);
        }

        while(processor.getActiveTaskCount()>0) {
            System.out.println(" Активных задач " + processor.getActiveTaskCount());
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(" Все задачи сделаны ");

        for(String  taskName : list) {
            Optional<Integer> result = processor.getResult(taskName);
            if(result.isPresent()) {
                System.out.println(taskName + ": " + result.get());
            }else{
                System.out.println(taskName + ": результат не найден");
            }
        }
        processor.getExecutor().shutdown();



    }
}