package mgr.robert.test.gnssserver;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<Integer> integers = new CopyOnWriteArrayList<>();
        integers.add(2);
        List<Integer> check = integers;
        integers.add(5);
        System.out.println(integers);
        System.out.println(check);
        assertEquals(4, 2 + 2);
    }
}