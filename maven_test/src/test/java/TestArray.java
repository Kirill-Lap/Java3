import Lesson6.ArrayMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestArray {
    ArrayMethods am;
    Integer[] testData;

    @Before
    public void init(){
        am = new ArrayMethods();
    }

    @Test
    public void test1(){
        testData = new Integer[]{2,3,23,3,3};
        Assert.assertTrue( !am.method2(testData, 1,4));
    }
    @Test
    public void test2(){
        testData = new Integer[]{4,3,23,3,3};
        Assert.assertTrue(!am.method2(testData, 1,4));
    }
    @Test
    public void test3(){
        testData = new Integer[]{2,3,1,3,3};
        Assert.assertTrue(!am.method2(testData, 1,4));
    }
    @Test
    public void test4(){
        testData = new Integer[]{1,3,23,4,3};
        Assert.assertTrue(am.method2(testData, 1,4));
    }

    @Test
    public void test5(){
        testData = new Integer[]{1,4,1,4,4};
        Assert.assertTrue(am.method2(testData, 1,4));
    }

    @Test
    public void test6(){
        testData = new Integer[]{1,2,3,4,5,6,7,8,9};
        Assert.assertArrayEquals(new int[]{5,6,7,8,9}, am.method1(testData, 4));
    }

    @Test
    public void test7(){
        testData = new Integer[]{1,2,3,4,5,6,7,4,8,9};
        Assert.assertArrayEquals(new int[]{8,9}, am.method1(testData, 4));
    }

    @Test
    public void test8(){
        testData = new Integer[]{1,2,3,4,5,6,7,8,9,4};
        Assert.assertArrayEquals(new int[]{}, am.method1(testData, 4));
    }

    @Test (expected = RuntimeException.class)
    public void test9(){
        testData = new Integer[]{1,2,3,5,6,7,8,9};
        am.method1(testData, 4);
    }
}
