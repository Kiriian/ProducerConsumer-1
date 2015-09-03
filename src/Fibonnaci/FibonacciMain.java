package Fibonnaci;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jeanette
 */
public class FibonacciMain
{

    private static Scanner sc = new Scanner(System.in);
    private static BlockingQueue<Integer> s1 = new ArrayBlockingQueue<>(12);
    private static BlockingQueue<Long> s2 = new ArrayBlockingQueue<>(12);
    private static ConsumerThread c1 = new ConsumerThread();

    public static void main(String[] args)
    {
        s1.add(4);
        s1.add(5);
        s1.add(8);
        s1.add(12);
        s1.add(21);
        s1.add(22);
        s1.add(34);
        s1.add(35);
        s1.add(36);
        s1.add(37);
        s1.add(42);

//        ProducerThread p1 = new ProducerThread();
//        ProducerThread p2 = new ProducerThread();
//        ProducerThread p3 = new ProducerThread();
//        ProducerThread p4 = new ProducerThread();
//        ConsumerThread c1 = new ConsumerThread();
//        
//        p1.start();
//        p2.start();
//        p3.start();
//        p4.start();
//        c1.start();

        int numberOfThreads = sc.nextInt();
        numberOfThreads(numberOfThreads);
        c1.start();

    }

    public static void numberOfThreads(int numberOfThreads)
    {
        ProducerThread[] threads;

        threads = new ProducerThread[numberOfThreads];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new ProducerThread();
            threads[i].start();
        }
    }

    private static long fib(long n)
    {
        if ((n == 0) || (n == 1))
        {
            return n;
        } else
        {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static class ProducerThread extends Thread
    {

        long start = System.currentTimeMillis();

        @Override
        public void run()
        {
            while (!s1.isEmpty())
            {
                try
                {
                    long result = fib(s1.poll());
                    s2.put(result);

                } catch (InterruptedException ex)
                {
                    Logger.getLogger(FibonacciMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            long time = System.currentTimeMillis() - start;
            System.out.println("Execution time: " + time);
        }
    }

    public static class ConsumerThread extends Thread
    {

        long start = System.currentTimeMillis();
        int totalSum = 0;
        int counter = 0;

        @Override
        public void run()
        {
            try
            {
                while (!s2.isEmpty() || counter < 11)
                {
                    int number = s2.take().intValue();

                    totalSum = totalSum + number;
                    System.out.println(number);
                    counter++;
                }

                System.out.println("Total sum: " + totalSum);
                long time = System.currentTimeMillis() - start;
                System.out.println("Execution time for c1: " + time);

            } catch (InterruptedException ex)
            {
                Logger.getLogger(FibonacciMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
