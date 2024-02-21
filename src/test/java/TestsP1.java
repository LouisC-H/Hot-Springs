import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestsP1 {

    @Test
    void cutDownExample(){
        Assertions.assertEquals(1, Main.runDay12Code("src/main/resources/cutDownExample.txt"));
    }

    @Test
    void fullExample(){
        Assertions.assertEquals(525152, Main.runDay12Code("src/main/resources/exampleP1.txt"));
    }

}
