import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BloomFilterTest {

    BloomFilter filter;
    BloomFilter.BloomFilterHook hook;

    @Before
    public void setUp() throws Exception {
        /* Set up the object to be tested */
        filter = new BloomFilter(3);
        hook = filter.new BloomFilterHook();
    }

    //All conditions are true, Boundary Coverage size > 0
    @Test
    public void test_constructor_positiveSize() {
        BloomFilter filter = new BloomFilter(4);
        assertNotNull(filter.getTable());
    }

    //Boundary Coverage size == 0
    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_zeroSize(){
        BloomFilter filter = new BloomFilter(0);
    }

    //Branch Coverage 1 false, Boundary Coverage size < 0, Bad Data (negative size table)
    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_LessThanZeroSize()  {
        BloomFilter filter = new BloomFilter(-1);
    }

    //Good Data (minimum normal configuration)
    @Test
    public void test_constructor_normalSize() {
        BloomFilter filter = new BloomFilter(1);
        assertNotNull(filter.getTable());
    }

    //All conditions true
    @Test
    public void test_increment_normal() {
        assertTrue(filter.increment("test"));
    }


    //Bad Data 1(String is null)
    @Test
    public void test_increment_null() {
        String str = null;
        assertFalse(filter.increment(str));
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_increment_min() {
        assertTrue(filter.increment(""));
    }


    //all conditions true
    @Test
    public void test_reversedString_normal() {
        assertEquals(hook.reversedString("test"), "tset");
    }


    //Bad Data 1(input is null)
    @Test
    public void test_reversedString_null() {
        String str = null;
        assertEquals(hook.reversedString(str), null);
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_reversedString_min() {
        assertEquals(hook.reversedString(""), "");
    }


    //all conditions true
    @Test
    public void test_firstIndex_normal() {
        int first = hook.firstIndex(filter.getTable(), "test");
        assertEquals(first, 1);
    }



    //Bad Data  (null)
    @Test
    public void test_firstIndex_null() throws NullPointerException {
        String str = null;
        int first = hook.firstIndex(filter.getTable(), str);
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_firstIndex_min() {
        BloomFilter filter = new BloomFilter(3);
        String str = "";
        int first = hook.firstIndex(filter.getTable(), str);
        assertEquals(first, 0);
    }



    //all conditions true
    @Test
    public void test_secondIndex_normal() {
        BloomFilter filter = new BloomFilter(3);
        int second = hook.secondIndex(filter.getTable(), "test");
        assertEquals(second, 1);
    }


    //Bad Data 1 (null)
    @Test
    public void test_secondIndex_null() throws NullPointerException {
        BloomFilter filter = new BloomFilter(3);
        String str = null;
        int second = hook.secondIndex(filter.getTable(), str);
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_secondIndex_min() {
        BloomFilter filter = new BloomFilter(3);
        String str = "";
        int second = hook.secondIndex(filter.getTable(), str);
        assertEquals(second, 0);
    }


    //all conditions true
    @Test
    public void test_thirdIndex_normal() {
        BloomFilter filter = new BloomFilter(3);
        int third = hook.thirdIndex(filter.getTable(), "test");
        assertEquals(third, 0);
    }

    //Branch Coverage 1 (1 false)
    @Test
    public void test_thirdIndex_empty() {
        BloomFilter filter = new BloomFilter(3);
        int third = hook.thirdIndex(filter.getTable(), "");
        assertEquals(third, 0);
    }

    //Branch Coverage 2 (address is 1 letter long)
    @Test
    public void test_thirdIndex_oneLetter() {
        BloomFilter filter = new BloomFilter(3);
        int third = hook.thirdIndex(filter.getTable(), "i");
        assertEquals(third, 0);
    }

    //Bad Data 1 (null)
    @Test
    public void test_thirdIndex_null() throws NullPointerException {
        BloomFilter filter = new BloomFilter(3);
        String str = null;
        int third = hook.thirdIndex(filter.getTable(), str);
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_thirdIndex_min() {
        BloomFilter filter = new BloomFilter(3);
        String str = "id";
        int third = hook.thirdIndex(filter.getTable(), str);
        assertEquals(third, 0);
    }


    //All conditions true, Boundary first > 0, second > 0, third > 0
    @Test
    public void test_isAnyZero_positive() {
        assertFalse(hook.isAnyZero(1, 1, 1));
    }

    //Branch Coverage 1, 2, 3 Boundary 2, 5, 8 (first == 0, second == 0, third == 0)
    @Test
    public void test_isAnyZero_zero() {
        assertTrue(hook.isAnyZero(0, 0, 0));
    }

    //Boundary 1, 4, 7 first < 0, second < 0, third < 0
    @Test
    public void test_isAnyZero_negative() {
        assertFalse(hook.isAnyZero(-1, -1, -1));
    }

    //all conditions true
    @Test
    public void test_count_normal() {
        String str = "test";
        filter.increment(str);
        assertEquals(1, filter.count(str));
    }


    //Bad Data 1 (null input)
    @Test(expected = NullPointerException.class)
    public void test_count_null() {
        String str = null;
        filter.increment(str);
        assertEquals(1, filter.count(str));
    }

    //Good Data 1 (minimum normal configuration)
    @Test
    public void test_count_min() {
        String str = "";
        filter.increment(str);
        assertEquals(1, filter.count(str));
    }



    //all conditions met
    @Test
    public void test_clear_normal() {
        filter.increment("test");
        filter.clear();
        int[] arr = new int[3];
        assertArrayEquals(arr, filter.getTable());
    }

    //Branch Coverage 1 (table is empty)
    @Test(expected = IlzzzzzzzzzzzzzzzzlegalArgumentException.class)
    public void test_clear_null() {
        BloomFilter filter = new BloomFilter(0);
        filter.clear();
    }
}
