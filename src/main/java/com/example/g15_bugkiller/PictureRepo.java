package com.example.g15_bugkiller;

import java.io.InputStream;
import java.util.HashMap;
import javafx.scene.image.Image;

public class PictureRepo {

    private static HashMap<String, Image> loadedImages = new HashMap();
    private static final Image DEFAULT = new Image(PictureRepo.class.getResourceAsStream("BildDateien/PATH.png"));

    public PictureRepo() {
    }

    public static Image getImage(String name) {
        if (loadedImages.containsKey(name)) {
            return (Image)loadedImages.get(name);
        } else {
            InputStream resourceAsStream = PictureRepo.class.getResourceAsStream("BildDateien/" + name + ".png");
            if (resourceAsStream == null) {
                return DEFAULT;
            } else {
                Image image = new Image(resourceAsStream);
                loadedImages.put(name, image);
                return image;
            }
        }
    }
}
