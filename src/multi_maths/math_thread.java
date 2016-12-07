package multi_maths;

/**
 * Created by tomclay on 31/10/2016.
 */

public class math_thread extends Thread {

    private math_data myMathData;
    private String myThreadName;

    //Setup the thread

    math_thread(String name, math_data sharedstuff) {
        super(name);
        myMathData = sharedstuff;
        myThreadName = name;
    }

    //This is called when "start" is used in the calling method
    public void run() {

    }
}
