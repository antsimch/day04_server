package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    List<String> cookies = null;

    public void readCookie(String fileName) throws IOException {
        
        // instantiate the cookies collection point
        cookies = new ArrayList<>();

        // instantiate a File object to pass the fileName
        File cookieFile = new File(fileName);

        // instantiate a FileReader followed by a BufferedReader
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = "";

        // while loop to loop through the file
        // read each line and add into the cookies collection object
        while ((line = br.readLine()) != null) {
            cookies.add(line);
        }
        
        // close the BufferedReader and FileReader in reverse order
        br.close();
        fr.close();
    }

    public String getRandomCookie() {
        if (cookies != null) {
            Random random = new Random();
            return cookies.get(random.nextInt(cookies.size() - 1));
        }
        return "No cookie in file";
    }
}
