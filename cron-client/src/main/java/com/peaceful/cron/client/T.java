package com.peaceful.cron.client;

/**
 * Created by Jun on 2018/5/11.
 */
public class T {

    public static void main(String[] args) throws InterruptedException {

        Worker worker = new Worker();
        worker.start();

        while (worker.isAlive()){
            Thread.sleep(1000);
            worker.interrupt();
        }
    }

    static class Worker extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(10000);
                    System.out.println("hello world");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
