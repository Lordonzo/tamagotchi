package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.FileInputStream;

import org.junit.*;

import javafx.scene.image.Image;
import models.Place;
import models.Status.EPlace;
import models.Status.MentalState;
import models.tamagotchi.*;
import controller.*;

public class TamagotchiTest {
    /*
    @Test
    public void setFakeImage_whenCat(){
        Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM),1);
        test.setImage("src/resources/tama_sprites/cat.png");
        assertThrows(FileNotFoundException.class, () -> {test.getImage();});
    } */

    @Test
    public void setRealImage_whenCat() throws Exception {
        Image expectedImage = new Image(new FileInputStream("src/resources/tama_sprites/cat.png"));
        Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM), 1);
        try {
            test.setImage("src/resources/tama_sprites/cat.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    assertEquals(expectedImage.getUrl(), test.getImage().getUrl());
}

    @Test
    public void setRealImage_whenDog() throws Exception {
        Image expectedImage = new Image(new FileInputStream("src/resources/tama_sprites/dog.png"));
        Tamagotchi test = new Dog("Jordan", new Place(EPlace.BEDROOM), 1);
        try {
            test.setImage("src/resources/tama_sprites/dog.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    assertEquals(expectedImage.getUrl(), test.getImage().getUrl());
    }

    @Test
    public void setRealImage_whenRabbit() throws Exception {
        Image expectedImage = new Image(new FileInputStream("src/resources/tama_sprites/rabbit.png"));
        Tamagotchi test = new Rabbit("Jordan", new Place(EPlace.BEDROOM), 1);
        try {
            test.setImage("src/resources/tama_sprites/rabbit.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    assertEquals(expectedImage.getUrl(), test.getImage().getUrl());
    }

    @Test
    public void setRealImage_whenRobot() throws Exception {
        Image expectedImage = new Image(new FileInputStream("src/resources/tama_sprites/robot.png"));
        Tamagotchi test = new Robot("Jordan", new Place(EPlace.BEDROOM), 1);
        try {
            test.setImage("src/resources/tama_sprites/robot.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    assertEquals(expectedImage.getUrl(), test.getImage().getUrl());
}

//     @Test
//     public void setFakeImage_whenCat() {
//         Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM),1);
//         //test.setImage("src/resources/tama/cat.png");
//         assertThrows(FileNotFoundException.class, () -> {test.setImage("src/resources/tama/cat.png"); test.getImage();});
//     }

    @Test
    public void difficultyTest(){
        Tamagotchi dif1 = new Cat("Jordan", new Place(EPlace.BEDROOM), 1);
        assertEquals(dif1.getDifficulty(), 1);
        Tamagotchi dif2 = new Dog("Jonathan", new Place(EPlace.BEDROOM), 2);
        assertEquals(dif2.getDifficulty(), 2);
        Tamagotchi dif3 = new Robot("BipBoup", new Place(EPlace.BEDROOM), 3);
        assertEquals(dif3.getDifficulty(), 3);
    }

    @Test
    public void currentPlaceTest(){
        Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM), 1);
        assertEquals(test.getCurrentPlace().getCurrentPlace(), EPlace.BEDROOM);
    }

    @Test
    public void goToLeftPlaceTest(){
        PlaceController pController = new PlaceController();
        Tamagotchi test = new Dog("Jonathan", pController.getPlaces().get(1), 1);
        test.goToLeftPlace();
        assertEquals(EPlace.BEDROOM, test.getCurrentPlace().getCurrentPlace());
        test.goToLeftPlace();
        assertEquals(EPlace.TOILET, test.getCurrentPlace().getCurrentPlace());
        test.goToLeftPlace();
        assertEquals(EPlace.KITCHEN, test.getCurrentPlace().getCurrentPlace());
        test.goToLeftPlace();
        assertEquals(EPlace.GARDEN, test.getCurrentPlace().getCurrentPlace());
        test.goToLeftPlace();
        assertEquals(EPlace.LIVINGROOM, test.getCurrentPlace().getCurrentPlace());
    }

    @Test
    public void goToRightPlaceTest(){
        PlaceController pController = new PlaceController();
        Tamagotchi test = new Robot("BipBoup", pController.getPlaces().get(1), 1);
        test.goToRightPlace();
        assertEquals(EPlace.GARDEN, test.getCurrentPlace().getCurrentPlace());
        test.goToRightPlace();
        assertEquals(EPlace.KITCHEN, test.getCurrentPlace().getCurrentPlace());
        test.goToRightPlace();
        assertEquals(EPlace.TOILET, test.getCurrentPlace().getCurrentPlace());
        test.goToRightPlace();
        assertEquals(EPlace.BEDROOM, test.getCurrentPlace().getCurrentPlace());
        test.goToRightPlace();
        assertEquals(EPlace.LIVINGROOM, test.getCurrentPlace().getCurrentPlace());
    }

    @Test
    public void playTest(){
        InGameController obs = new InGameController();
        Tamagotchi test = new Rabbit("Dodo", new Place(EPlace.GARDEN), 1);
        test.setObserver(obs);
        test.setMentalState(MentalState.SAD);
        MentalState happiness = test.getMentalState();
        test.gardenAction();
        assertFalse((happiness == test.getMentalState()));
    }
}
