package bondarenko;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int min = 1;
        int max = 1000;
        int diff = max - min;
        Random random = new Random();

        int[] array = new int[20000000];
        for (int i = 0; i < array.length; i++){
            array[i] = random.nextInt(diff + 1) + min;
        }

        int[] array1 = new int[5000000];
        int[] array2 = new int[5000000];
        int[] array3 = new int[5000000];
        int[] array4 = new int[5000000];

        for (int i = 0; i < 5000000; i++){
            array1[i] = array[i];
            array2[i] = array[i+5000000];
            array3[i] = array[i+10000000];
            array4[i] = array[i+15000000];
        }

        long time = System.currentTimeMillis();

        Sort sort;
        sort = new Sort(array);
        sort.start();

        try {
            sort.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Время выполнения при одном потоке = " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();

        ArrayList<Sort> list = new ArrayList<Sort>();

        Sort sortArray1 = new Sort(array1);
        Sort sortArray2 = new Sort(array2);
        Sort sortArray3 = new Sort(array3);
        Sort sortArray4 = new Sort(array4);

        list.add(sortArray1);
        list.add(sortArray2);
        list.add(sortArray3);
        list.add(sortArray4);

        sortArray1.start();
        sortArray2.start();
        sortArray3.start();
        sortArray4.start();

        for(Sort one : list) {
            try {
                one.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Время выполнения в четыре потока = " + (System.currentTimeMillis() - time));
    }

}
