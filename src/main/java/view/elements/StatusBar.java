package view.elements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.interfaces.FlagListener;
import model.interfaces.TimeListener;
import model.util.ImageLoader;
import model.util.image.NumberType;

public class StatusBar extends Canvas implements TimeListener, FlagListener {

    private static final int DEV = 10;
    private static final int NUM_OF_NUMBERS = 5;
    private int currSecond;
    private int currFlags;
    private int heightNum;
    private int widthNum;

    public StatusBar(final double widthBar) {
        this.heightNum = ImageLoader.getInstance().getNumHeight();
        this.widthNum = ImageLoader.getInstance().getNumWidth();
        this.currSecond = 0;
        this.currFlags = 0;
        setWidth(widthBar);
        init();
    }

    private void init() {
        resize();
        drawNumbers(getNumbers(currSecond));
        drawFlags(getNumbers(currFlags));
    }

    private void clear() {
        getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
    }

    private void resize() {
        setHeight(heightNum);
    }

    private int[] getNumbers(final int second) {
        final int[] numbers = new int[NUM_OF_NUMBERS];
        int temp = second;
        int i = NUM_OF_NUMBERS - 1;
        while (true) {
            if (temp == 0) {
                break;
            }
            numbers[i--] = temp % DEV;
            temp /= DEV;
        }
        return numbers;
    }

    private void drawNum(final int num, final int x, final int y) {
        GraphicsContext gc = getGraphicsContext2D();
        ImageLoader im = ImageLoader.getInstance();
        switch (num) {
            case 0:
                gc.drawImage(im.getImage(NumberType.ZERO.getName()), x, y);
                break;
            case 1:
                gc.drawImage(im.getImage(NumberType.ONE.getName()), x, y);
                break;
            case 2:
                gc.drawImage(im.getImage(NumberType.TWO.getName()), x, y);
                break;
            case 3:
                gc.drawImage(im.getImage(NumberType.THREE.getName()), x, y);
                break;
            case 4:
                gc.drawImage(im.getImage(NumberType.FOUR.getName()), x, y);
                break;
            case 5:
                gc.drawImage(im.getImage(NumberType.FIVE.getName()), x, y);
                break;
            case 6:
                gc.drawImage(im.getImage(NumberType.SIX.getName()), x, y);
                break;
            case 7:
                gc.drawImage(im.getImage(NumberType.SEVEN.getName()), x, y);
                break;
            case 8:
                gc.drawImage(im.getImage(NumberType.EIGHT.getName()), x, y);
                break;
            case 9:
                gc.drawImage(im.getImage(NumberType.NINE.getName()), x, y);
                break;
        }
    }

    private void drawNumbers(final int[] numbers) {
        final int y = 0;
        for (int i = 0; i < NUM_OF_NUMBERS; i++) {
            final int x = i * widthNum;
            drawNum(numbers[i], x, y);
        }
    }

    private void drawFlags(final int[] numbers) {
        final int y = 0;
        final int start = (int) (getWidth() - (NUM_OF_NUMBERS * widthNum));
        for (int i = 0; i < NUM_OF_NUMBERS; i++) {
            final int x = start + i * widthNum;
            drawNum(numbers[i], x, y);
        }
    }

    public void rescale() {
        this.heightNum = ImageLoader.getInstance().getNumHeight();
        this.widthNum = ImageLoader.getInstance().getNumWidth();
        clear();
        resize();
        drawNumbers(getNumbers(currSecond));
        drawFlags(getNumbers(currFlags));
    }

    @Override
    public void tick(final int second) {
        currSecond = second;
        drawNumbers(getNumbers(currSecond));
    }

    @Override
    public void updateFlag(final int flags) {
        currFlags = flags;
        drawFlags(getNumbers(currFlags));
    }
}
