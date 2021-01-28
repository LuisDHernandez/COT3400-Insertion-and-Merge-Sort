import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        long time_start;
        long time_stop;

        Scanner scan = new Scanner(System.in);
        Path path = null;

        int choose_file;
        System.out.println("How many keys will you like to sort? Enter 10, 100, 1000, 10000, 100000, or 1000000.");
        choose_file = scan.nextInt();

        switch(choose_file){
            case 10:
                path = Paths.get("./src/10.keys.txt");
                break;
            case 100:
                path = Paths.get("./src/100.keys.txt");
                break;
            case 1000:
                path = Paths.get("./src/1000.keys.txt");
                break;
            case 10000:
                path = Paths.get("./src/10000.keys.txt");
                break;
            case 100000:
                path = Paths.get("./src/100000.keys.txt");
                break;
            case 1000000:
                path = Paths.get("./src/1000000.keys.txt");
                break;
            default:
                System.out.println("Please enter one of the specified choices");
                break;
        }
        scan.nextLine();
        System.out.println("Enter m for merge sort or i for insertion sort");
        String choice = scan.nextLine();
        scan.close();

        // reads file and saves inputs for sorting
        assert path != null;
        Scanner sc = new Scanner(new File(path.toString()));
        ArrayList<Integer> List_of_keys = new ArrayList<>();
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                List_of_keys.add(sc.nextInt());
            } else {
                sc.next();
            }
        }
        sc.close();

        //change ArrayList to an array
        Integer[] keys_array = new Integer[List_of_keys.size()];
        keys_array = List_of_keys.toArray(keys_array);

        if(choice.equals("m")) {

            time_start = System.nanoTime();
            mergeSort(keys_array, 0, keys_array.length - 1);
            time_stop = System.nanoTime();
            long total_time = time_stop - time_start;

            System.out.println("The merge sort on " + keys_array.length + " keys took: " + total_time + " ns.");

            switch (choose_file) {
                case 10:
                    path = Paths.get("./src/output.mergeSort.10.keys.txt");
                    break;
                case 100:
                    path = Paths.get("./src/output.mergeSort.100.keys.txt");
                    break;
                case 1000:
                    path = Paths.get("./src/output.mergeSort.1000.keys.txt");
                    break;
                case 10000:
                    path = Paths.get("./src/output.mergeSort.10000.keys.txt");
                    break;
                case 100000:
                    path = Paths.get("./src/output.mergeSort.100000.keys.txt");
                    break;
                case 1000000:
                    path = Paths.get("./src/output.mergeSort.1000000.keys.txt");
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path,StandardCharsets.UTF_8)) {
                for (Integer key : keys_array) {
                    writer.write(key.toString() + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ascending order sort
            time_start = System.nanoTime();
            mergeSort(keys_array, 0, keys_array.length - 1);
            time_stop = System.nanoTime();

            System.out.println("The ascending merge sort on " + keys_array.length + " keys took: " + (time_stop - time_start) +
                    " ns.");

            // descending order sort
            invertArray(keys_array);

            time_start = System.nanoTime();
            mergeSort(keys_array, 0, keys_array.length - 1);
            time_stop = System.nanoTime();

            System.out.println("The descending merge sort on " + keys_array.length + " keys took: " + (time_stop -
                    time_start) + " ns.");
        }
        else{
            time_start = System.nanoTime();
            insertionSort(keys_array);
            time_stop = System.nanoTime();
            long total_time = time_stop - time_start;
            System.out.println("The insertion sort on " + keys_array.length + " keys took: " + total_time + " ns.");

            switch(choose_file){
                case 10:
                    path = Paths.get("./src/output.insertionSort.10.keys.txt");
                    break;
                case 100:
                    path = Paths.get("./src/output.insertionSort.100.keys.txt");
                    break;
                case 1000:
                    path = Paths.get("./src/output.insertionSort.1000.keys.txt");
                    break;
                case 10000:
                    path = Paths.get("./src/output.insertionSort.10000.keys.txt");
                    break;
                case 100000:
                    path = Paths.get("./src/output.insertionSort.100000.keys.txt");
                    break;
                case 1000000:
                    path = Paths.get("./src/output.insertionSort.1000000.keys.txt");
        }

            try (BufferedWriter writer = Files.newBufferedWriter(path,StandardCharsets.UTF_8)) {
                for (Integer key : keys_array) {
                    writer.write(key.toString() + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ascending order sort
            time_start = System.nanoTime();
            insertionSort(keys_array);
            time_stop = System.nanoTime();

            System.out.println("The ascending insertion sort on " + keys_array.length + " keys took: " + (time_stop -
                    time_start) + " ns.");

            // descending order sort
            invertArray(keys_array);
            time_start = System.nanoTime();
            insertionSort(keys_array);
            time_stop = System.nanoTime();

            System.out.println("The descending insertion sort on " + keys_array.length + " keys took: " + (time_stop -
                    time_start) + " ns.");

        }
    }

    private static void mergeSort(Integer[] array, int l, int r) {
        if (r <= l) {
            return;
        }
        int middle = (l + r) / 2;
        mergeSort(array, l, middle);
        mergeSort(array, middle + 1, r);
        merge(array, l, middle, r);
    }

    private static void merge(Integer[] array, int l, int middle, int r) {
        int[] left = new int[middle - l + 1];
        int[] right = new int[r - middle];

        for (int i = 0; i < left.length; i++) {
            left[i] = array[l + i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = array[middle + i + 1];
        }

        int leftIndex = 0;
        int rightIndex = 0;

        // Copying from leftArray and rightArray back into array
        for (int i = l; i < r + 1; i++) {
            if (leftIndex < left.length && rightIndex < right.length) {
                if (left[leftIndex] < right[rightIndex]) {
                    array[i] = left[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = right[rightIndex];
                    rightIndex++;
                }
            } else if (leftIndex < left.length) {
                array[i] = left[leftIndex];
                leftIndex++;
            } else if (rightIndex < right.length) {
                array[i] = right[rightIndex];
                rightIndex++;
            }
        }
    }

    private static void insertionSort(Integer[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i] > key)) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }
    }

    private static void invertArray(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - i - 1] = temp;
        }
    }
}