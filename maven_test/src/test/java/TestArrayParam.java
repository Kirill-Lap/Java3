import Lesson6.ArrayMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.stream.IntStream;

@RunWith(Parameterized.class)
public class TestArrayParam {
    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(new Object[][] {
                {false, new int[]{2,3,23,3,3}, new int[]{1,2,3,4,5,6,7,8,9}, new int[]{5,6,7,8,9}, null},
                {false, new int[]{4,3,23,3,3}, new int[]{1,2,3,4,5,6,7,4,8,9}, new int[]{8,9}, null},
                {false, new int[]{2,3,1,3,3}, new int[]{1,2,3,4,5,6,7,4,8,9,4}, new int[]{}, null},
                {true, new int[]{1,3,23,4,3}, new int[]{4,1,4,1,4,1,4,1}, new int[]{1}, null},
                {true, new int[]{1,4,1,4,4}, new int[]{1,2,3,5,6,7,8,9}, null, RuntimeException.class},
        });
    }


    private Integer[] testData1;
    private Integer[] testData2;
    private int[] resData;
    private boolean res;
    private Class<? extends Exception> rte;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public TestArrayParam(boolean resObj, int[] data, int[] data1, int[] resArr, Class<? extends Exception> e) {
        this.testData1 = IntStream.of(data).boxed().toArray(Integer[]::new);
        this.testData2 = IntStream.of(data1).boxed().toArray(Integer[]::new);
        this.resData = resArr;
        this.res = resObj;
        this.rte = e;
    }

    private ArrayMethods am;
    @Before
    public void init(){
        am = new ArrayMethods();
    }

    @Test
    public void massArrayTest(){
        System.out.println(1);
        Assert.assertTrue(  res == am.method2(testData1, 1,4));
    }
    @Test
    public void massArrayTest1() throws Exception{
        if (rte!= null){
            thrown.expect(rte);
        }
        System.out.println(2);
        Assert.assertArrayEquals(resData, am.method1(testData2, 4));
    }
}
