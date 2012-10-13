package com.nkrecklow.herobrine;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;

public class Util {

    public static int getRandomInteger(int max) {
        if (new Random().nextBoolean()) {
            return -new Random().nextInt(max) + 1;
        } else {
            return new Random().nextInt(max) + 1;
        }
    }

    public static ArrayList<String> getWebsiteContents(URL url) throws Exception {
        ArrayList<String> fileContents = new ArrayList<String>();
        if (url != null) {
            DataInputStream fileReader = new DataInputStream(new BufferedInputStream(url.openStream()));
            String fileLine = "";
            while ((fileLine = fileReader.readLine()) != null) {
                if (!fileLine.equals("")) {
                    fileContents.add(fileLine);
                }
            }
            fileReader.close();
        } else {
            throw new Exception();
        }
        return fileContents;
    }

    public static Location getNearbyLocation(Location loc) {
        return loc.add(Util.getRandomInteger(3), 0, Util.getRandomInteger(3));
    }
}
