package fishbot;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

 class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "fishbot.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Digit {
    private Point fisrt;
    private Point second;
    private Point third;
    private Point fourth;

    public Digit(Point fisrt, Point second, Point third, Point fourth) {
        this.fisrt = fisrt;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Digit(Digit c) {
        this.fisrt = c.getFisrt();
        this.second = c.getSecond();
        this.third = c.getThird();
        this.fourth = c.getFourth();
    }


    public Digit(Point fisrt, Point second, Point third) {
        this.fisrt = fisrt;
        this.second = second;
        this.fourth = third;

    }

    public Point getFourth() {
        return fourth;
    }

    public void setFourth(Point fourth) {
        this.fourth = fourth;
    }

    public Point getFisrt() {
        return fisrt;
    }

    public void setFisrt(Point fisrt) {
        this.fisrt = fisrt;
    }

    public Point getSecond() {
        return second;
    }

    public void setSecond(Point second) {
        this.second = second;
    }

    public Point getThird() {
        return third;
    }

    public void setThird(Point third) {
        this.third = third;
    }


}


public class FishBot {
    static int amountOfTakes = 0;

    public static void checkingForTakes(Color activatingColorOnPoint, Point activatingPoint) throws AWTException, InterruptedException, IOException {
        Robot robot = new Robot();
        while (true) {
            if (!activatingColorOnPoint.equals(robot.getPixelColor(activatingPoint.getX(), activatingPoint.getY())))
                break;
        }

        Rectangle rectangle = new Rectangle(0, 31, 200, 18);

        BufferedImage screen = robot.createScreenCapture(rectangle);
        checkNumber(screen);
    }

    public static void cast(Point firstWindow) throws InterruptedException, AWTException {
        Random random = new Random();
        Robot robot = new Robot();

        robot.mouseMove(firstWindow.getX(), firstWindow.getY());
        Thread.sleep(80);
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(150);
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        robot.getAutoDelay();
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(150);
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        robot.getAutoDelay();
//        robot.keyPress(87); //w
//        Thread.sleep(200);
//        robot.keyRelease(87);
        Thread.sleep((200 + random.nextInt(800)));
        if (amountOfTakes < 200) {
            robot.keyPress(49); //worm on 1
            Thread.sleep(30);
            robot.keyRelease(49);
        }
        if (amountOfTakes > 200 && amountOfTakes < 400) {
            robot.keyPress(50); //worm on 2
            Thread.sleep(30);
            robot.keyRelease(50);
        }
        if (amountOfTakes > 400 && amountOfTakes < 600) {
            robot.keyPress(51); //worm on 3
            Thread.sleep(30);
            robot.keyRelease(51);
        }
        if (amountOfTakes > 600 && amountOfTakes < 800) {
            robot.keyPress(52); //worm on 4
            Thread.sleep(30);
            robot.keyRelease(52);
        }
        Thread.sleep((200 + random.nextInt(800)));
        robot.keyPress(32);
        Thread.sleep(30);
        robot.keyRelease(32);

        amountOfTakes++;
    }

    public static void fishing(int amountOfClick) throws InterruptedException, AWTException, IOException {
        Random random = new Random();
        Robot robot = new Robot();


        for (int i = 0; i < amountOfClick; i++) {
            robot.delay(100 + (random.nextInt(50)));
            robot.keyPress(32); //spacja
            robot.delay(random.nextInt(10 + (random.nextInt(30))));
            robot.keyRelease(32);// puszczenie spacji
        }
        robot.delay(3000);
        start();
    }

    public static void checkNumber(BufferedImage screen) throws AWTException, IOException, InterruptedException {
        Robot robot = new Robot();
        ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Tesseract\\tessdata");
        instance.setLanguage("pol");
        Random random = new Random();
        try {
            String result = instance.doOCR(screen);
            System.out.println(result);
            System.out.println(result.charAt(9));
            fishing(Integer.parseInt(String.valueOf(result.charAt(9))));

            System.out.println(result);
        } catch (TesseractException e) {
            System.out.println("exception" + e.getMessage());
        } finally {
            //if screenshot fail and takes photo of another event like player killed boos etc. will generate random value
            fishing(random.nextInt(5) + 1);
        }
    }

    public static void start() throws AWTException, InterruptedException, IOException {
        Robot robot = new Robot();
        Random random = new Random();
        robot.delay(500);
        int windowCornerX = 0;
        int windowCornerY = 0;
        Point firstWindow = new Point(windowCornerX + 100, windowCornerY + 100);
        Point activationPoint = new Point(windowCornerX + 415, windowCornerY + 168);
        while (true) {
            robot.delay(3000);
            cast(firstWindow);
            robot.delay(3000);
            Color colorBeforeCasting = robot.getPixelColor(activationPoint.getX(), activationPoint.getY());
            checkingForTakes(colorBeforeCasting, activationPoint);
            robot.delay(2000);
        }
    }

    public static void main(String[] args) throws AWTException, IOException, TesseractException, InterruptedException {
        start();
    }
}