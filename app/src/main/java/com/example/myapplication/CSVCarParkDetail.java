package com.example.myapplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVCarParkDetail {
    /**
     * @return  : return a HashMap<String, ArrayList<String>>
     *          : which represents {id : [0 address,
     *                                    1 car_park_type,
     *                                    2 type_of_parking_system,
     *                                    3 short_term_parking,
     *                                    4 free_parking,
     *                                    5 night_parking])
     * @throws IOException
     */
    public static HashMap<String, ArrayList<String>> getDetailInfo()
        throws IOException {
        String path =
                "app/src/main/java/com/example/myapplication/src/hdb-carpark-information.csv";
//            "/mnt/c/Users/lingao/Desktop/parking_data/hdb-carpark-information/hdb-carpark-information.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            HashMap<String, ArrayList<String>> carpark_id =
                new HashMap<String, ArrayList<String>>();

            line = br.readLine();
            String[] arr;
            while (line != null) {
                line = br.readLine();

                if (line == null) {
                    break;
                }
                arr = line.split(",");

                ArrayList<String> value = new ArrayList<String>();
                value.add(arr[1]); // address
                value.add(arr[4]); // car_park_type
                value.add(arr[5]); // type_of_parking_system
                value.add(arr[6]); // short_term_parking
                value.add(arr[7]); // free_parking
                value.add(arr[8]); // night_parking

                carpark_id.put(arr[0], value); // add {id: details}
            }

            return carpark_id;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args) throws IOException {
        HashMap<String, ArrayList<String>> hm = getDetailInfo();
         System.out.println(hm.toString());
    }
}
