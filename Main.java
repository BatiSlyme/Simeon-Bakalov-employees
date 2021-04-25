package ReadFromFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    /**
     * @file PLEASE ENTER THE PATH OF YOUR TEXT DOCUMENT!
     * @count count how many lines we have, for the 2d rows
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C://Users//Moni//Desktop//SirmaReadFromText.txt");//ENTER YOUR PATH
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        BufferedReader buffer = new BufferedReader(new FileReader(file));
//      count how many rows we have
        int count = 0;
        String line1;
        String employee1 = "";
        String employee2 = "";
        long longestCollaboration = 0;
        try {
            while ((line1 = buffer.readLine()) != null) {
                count++;
                sc.hasNextLine();
            }
        } catch (IOException e) {

            e.printStackTrace();

        }

        int rows = count;
        int columns = 4;
        String[][] myArray = new String[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < myArray.length; i++) {
                String[] line = sc.nextLine().trim().split(", ");
                for (int j = 0; j < line.length; j++) {
                    myArray[i][j] = (line[j]);
                }
            }
        }

        for (int i = 0; i < count; i++) {
            for (int j = 1; j <count; j++) {
                if (calculateDuration(myArray, i, j) > longestCollaboration) {
                    longestCollaboration = calculateDuration(myArray, i, j);
                    employee1 = myArray[i][0];
                    employee2 = myArray[j][0];

                }
            }
        }
        System.out.println("The partners who have worked together for the longest time on a project are: " + employee1 + " and " + employee2);


    }

    /**
     * Find how long the employees have worked together
     *
     * @param arr       the array containing the txt file information
     * @param employee1 employee 1
     * @param employee2 employee 2
     * @return return the duration of the 2 employees who have worked together in ms
     */

    public static long calculateDuration(String[][] arr, int employee1, int employee2) {
        if (!hasCollaborated(arr, employee1, employee2))
            return 0;// have't collaborated so we don't calculate the duration of the work together

        long dates = 0;
        long lowerThreshold = 0;
        long upperThreshold = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate1 = myFormat.parse(arr[employee1][2]);
            Date endDate1 = myFormat.parse(arr[employee1][3]);

            Date startDate2 = myFormat.parse(arr[employee2][2]);
            Date endDate2 = myFormat.parse(arr[employee2][3]);
            if (startDate1.getTime() - startDate2.getTime() < 0) {
                lowerThreshold = startDate2.getTime();
            } else
                lowerThreshold = startDate1.getTime();

            if (endDate1.getTime() - endDate2.getTime() < 0) {
                upperThreshold = endDate1.getTime();
            } else
                upperThreshold = endDate2.getTime();

            dates = upperThreshold - lowerThreshold;

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dates;// return in milliseconds
    }

    /**
     * Finds which employees have worked together
     *
     * @param arr       the array containing the txt file information
     * @param employee1 employee 1
     * @param employee2 employee 2
     * @return true or false
     */
    public static boolean hasCollaborated(String[][] arr, int employee1, int employee2) {
        if (!(arr[employee1][1]).equals(arr[employee2][1]) || arr[employee1][0].equals(arr[employee2][0])) {
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();//current date

        if ((arr[employee1][3]).equals("NULL")) {
            arr[employee1][3] = formatter.format(date);
        }
        if ((arr[employee2][3]).equals("NULL")) {
            arr[employee2][3] = formatter.format(date);
        }
        String[] endDate1 = arr[employee1][3].split("-");
        String[] startDate1 = arr[employee1][2].split("-");
        String[] endDate2 = arr[employee2][3].split("-");
        String[] startDate2 = arr[employee2][2].split("-");

        if (((Integer.parseInt(endDate1[0]) - Integer.parseInt(startDate2[0]) < 0)) || ((Integer.parseInt(endDate2[0]) - Integer.parseInt(startDate1[0]) < 0))){
            return false;
        }
        else if (Integer.parseInt(endDate1[0]) - Integer.parseInt(startDate2[0]) == 0) {
            if (Integer.parseInt(endDate1[1]) - Integer.parseInt(startDate2[1]) < 0) {
                return false;
            } else if (Integer.parseInt(endDate1[1]) - Integer.parseInt(startDate2[2]) == 0) {
                return Integer.parseInt(endDate1[2]) - Integer.parseInt(startDate2[2]) >= 0;
            }
        } else if (Integer.parseInt(endDate2[0]) - Integer.parseInt(startDate1[0]) == 0) {
            if (Integer.parseInt(endDate2[1]) - Integer.parseInt(startDate1[1]) < 0) {
                return false;
            } else if (Integer.parseInt(endDate2[1]) - Integer.parseInt(startDate1[2]) == 0) {
                return Integer.parseInt(endDate2[2]) - Integer.parseInt(startDate1[2]) >= 0;
            }
        }
        return true;
    }
}