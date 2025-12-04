package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {

    private final Map<String, Integer> results;
    private final ExecutorService executor;
    private final AtomicInteger activeTasksCounter;
    private final AtomicInteger taskNameCounter;

    public DataProcessor(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.results = new HashMap<>();
        this.taskNameCounter = new AtomicInteger(0);
        this.activeTasksCounter = new AtomicInteger(0);

    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public String submitTask(List<Integer> numbers) {
        String taskName = "Task-" + taskNameCounter.incrementAndGet();
        activeTasksCounter.incrementAndGet();
        CalculateSumTask sumTask = new CalculateSumTask(numbers, taskName);

        executor.submit(() -> {
            try{
                Integer result = sumTask.call();
                synchronized (results) {
                    results.put(taskName, result);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                activeTasksCounter.decrementAndGet();
            }
        });
        return taskName;

    }

    public Optional<Integer> getResult(String taskName) {
        Future<Integer> future;
        synchronized (results) {
            if (results.containsKey(taskName)) {
                return Optional.of(results.get(taskName));
            }
        }
        return  Optional.empty();

    }

    public int getActiveTaskCount() {
        return  activeTasksCounter.get();
    }

    public void shutdown() {
        executor.shutdown();
    }

}
