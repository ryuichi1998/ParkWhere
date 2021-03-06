package com.example.myapplication.model;

import android.content.Context;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
    public static HashMap<String, ArrayList<String>> getDetailInfo(Context context)
        throws IOException {
        //TODO: FIX PATH
        String path =
                "hdb_carpark_information.csv";

        String line = "";
        try {
            InputStream my_data = context.getResources().openRawResource(R.raw.hdb_carpark_information);
            BufferedReader br = new BufferedReader(new InputStreamReader(my_data, Charset.forName("UTF-8")));

            HashMap<String, ArrayList<String>> carpark_id =
                new HashMap<String, ArrayList<String>>();

            line = br.readLine();
            String[] arr;
            int count = 0;
            while (line != null) {
                line = br.readLine();

                if (line == null) {
                    break;
                }
                //TODO: remove

                arr = line.split("\"");

                ArrayList<String> value = new ArrayList<String>();
                value.add(arr[3]); // address
                value.add(arr[5]); // longitude
                value.add(arr[7]); // latitude
                value.add(arr[9]); // car_park_type
                value.add(arr[11]); // type_of_parking_system
                value.add(arr[13]); // short_term_parking
                value.add(arr[15]); // free_parking
                value.add(arr[17]); // night_parking
                value.add("NO");    // is_bookmarked
                value.add(arr[25]); // category
                value.add(arr[27]); // week_rate_1
                value.add(arr[29]); // week_rate_2
                value.add(arr[31]); // sat_rate
                value.add(arr[33]); // sun_rate

                if (!arr[1].equals("")){
                    carpark_id.put(arr[1], value); // add {id: details}
                }
                else{
                    String tmp_id = "null " + count++;
                    carpark_id.put(tmp_id, value); // add {id: details}
                }
            }

            return carpark_id;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) throws IOException {
//        HashMap<String, ArrayList<String>> hm = getDetailInfo();
//         System.out.println(hm.toString());
//    }
}
