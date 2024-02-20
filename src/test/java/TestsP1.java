import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestsP1 {

    @Test
    void singleRowTestA(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(1);
        arrayList.add(3);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous(".#...#....###..".toCharArray()));
    }

    @Test
    void singleRowTestB(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(1);
        arrayList.add(3);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous(".#...#....###..".toCharArray()));
    }

    @Test
    void singleRowTestC(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(6);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous(".#.###.#.######.".toCharArray()));
    }

    @Test
    void singleRowTestD(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(4);
        arrayList.add(1);
        arrayList.add(1);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous("####.#...#....".toCharArray()));
    }

    @Test
    void singleRowTestE(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(6);
        arrayList.add(5);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous("#....######..#####..".toCharArray()));
    }

    @Test
    void singleRowTestF(){
        SpringRow springRow = new SpringRow("# 1");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(2);
        arrayList.add(1);
        Assertions.assertEquals(arrayList, springRow.calculateContiguous(".###.##....#.".toCharArray()));
    }

    @Test
    void cutDownExample(){
        Assertions.assertEquals(4, Main.runDay12Code("src/main/resources/cutDownExample.txt"));
    }

    @Test
    void fullExample(){
        Assertions.assertEquals(21, Main.runDay12Code("src/main/resources/exampleP1.txt"));
    }

//    @Test
//    void binaryIteratorTest(){
//        SpringRow springRow = new SpringRow("# 1");
//        springRow.binaryIterator(2,4, "");
//    }
}
