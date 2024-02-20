import java.util.ArrayList;

public class SpringRow {

    private char[] springArray;
    private final char[] foldedArray;
    private final ArrayList<Integer> foldedContigData = new ArrayList<>();
    private final ArrayList<Integer> contiguousData = new ArrayList<>();

    private int totalBSprings;
    private int unacBSprings;
    private int totalUSprings;

    private final ArrayList<String> binaryOptions = new ArrayList<>();

    public SpringRow(String charString) {
        // parse out data into a character array of the springs' statuses and an arraylist of the contiguous data
        String[] dataAndContiguous = charString.split(" ");
        this.foldedArray = dataAndContiguous[0].toCharArray();
        String[] contiguousDataString = dataAndContiguous[1].split(",");
        for (String s :
                contiguousDataString) {
            foldedContigData.add(Integer.parseInt(s));
        }
        unfoldSpringArray();
        unfoldContiguousList();
        countSprings();
    }

    private void unfoldSpringArray() {
        this.springArray = new char[foldedArray.length * 5 + 4];
        //repeat five times:
        for (int i = 0; i < 5; i++) {
            //Copy each character in the folded array
            System.arraycopy(foldedArray, 0, springArray, i * (foldedArray.length + 1), foldedArray.length);
            // Then add a joining '?'
            if (i != 0){
                springArray[(i) * (foldedArray.length+1) - 1] = '?';
            }
        }
    }

    private void unfoldContiguousList() {
        for (int i = 0; i < 5; i++) {
            contiguousData.addAll(foldedContigData);
        }
    }

    private void countSprings() {
        for (int i :
                contiguousData) {
            this.totalBSprings += i;
        }
        int accountedForBSprings = 0;
        for (char c :
                springArray) {
            if (c == '#') {
                accountedForBSprings++;
            } else if (c == '?'){
                this.totalUSprings++;
            }
        }
        unacBSprings = totalBSprings - accountedForBSprings;

        binaryIterator(totalUSprings, springArray.length, "");
    }

    public void binaryIterator(int remaining1s, int remainingTot, String str){
        if (remainingTot == 0 ) {
            binaryOptions.add(str);
        }
        // try adding a 1 to the string
        if (remaining1s != 0){
            binaryIterator(remaining1s - 1, remainingTot -1, str + 1);
        }
        if (remainingTot > remaining1s){
            binaryIterator(remaining1s, remainingTot -1, str + 0);
        }
    }

//    public int analyseLine() {
//        int numValidDistributions = 0;
//        //
//        for (Integer i = 0; i < Math.pow(2, totalUSprings); i++) {
//            String binaryString = Integer.toBinaryString(i);
//            if (rightNumberOnes(binaryString)){
//                if (testPotentialDistribution(checkBinaryStringLength(binaryString))){
//                    System.out.println(++numValidDistributions);
//                }
//            }
//        }
//        return numValidDistributions;
//    }

    public int analyseLine() {
        int numValidDistributions = 0;
        for (String binaryOption :
                binaryOptions) {
            if (testPotentialDistribution(binaryOption)){
                ++numValidDistributions;
            }
        }
        return numValidDistributions;
    }

//    private String checkBinaryStringLength(String binaryString) {
//        while (binaryString.length() < totalUSprings){
//            binaryString = "0" + binaryString;
//        }
//        return binaryString;
//    }

    // Replace the ? in the dataArray with defined springs according to the binaryInput: 0 = working spring and 1 = broken
    private boolean testPotentialDistribution(String binaryInput) {
        char[] binaryChars = binaryInput.toCharArray();
        char[] filledDataArray = new char[springArray.length + 1];
        int unknownPosition = 0;
        for (int i = 0; i < springArray.length; i++) {
            char c = springArray[i];
            if (c != '?'){
                filledDataArray[i] = c;
            } else {
                if (binaryChars[unknownPosition++] == '0'){
                    filledDataArray[i] = '.';
                } else {
                    filledDataArray[i] = '#';
                }
            }
        }
        filledDataArray[springArray.length] = '.';
        ArrayList<Integer> contiguousCalculated = calculateContiguous(filledDataArray);
        return contiguousCalculated.equals(contiguousData);
    }

    // input should only contain '.' and '#'
    public ArrayList<Integer> calculateContiguous(char[] dataArray) {
        int contiguousBSprings = 0;
        ArrayList<Integer> contiguousOutput = new ArrayList<>();
        for (char c : dataArray) {
            if (c == '#') {
                contiguousBSprings++;
            } else if (contiguousBSprings != 0) {
                // IE if we hit the first . after one or more #
                contiguousOutput.add(contiguousBSprings);
                contiguousBSprings = 0;
            }
        }
        return contiguousOutput;
    }

//    // Pretend 0 = working spring and 1 = broken. Do we have the right number of unaccounted for broken springs
//    private boolean rightNumberOnes(String binaryString) {
//        int numOnes = 0;
//        for (char c :
//                binaryString.toCharArray()) {
//            if (c == '1'){
//                numOnes++;
//            }
//        }
//        return numOnes == unacBSprings;
//    }
}


