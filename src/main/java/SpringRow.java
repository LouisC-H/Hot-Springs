import java.util.ArrayList;

public class SpringRow {

    private final char[] foldedArray;
    private char[] springArray;
    private final ArrayList<Integer> foldedContigData = new ArrayList<>();
    private final ArrayList<Integer> contiguousData = new ArrayList<>();

    private int totalBSprings;
    private int unacBSprings;
    private int totalUSprings;

    private long validNumberDistributions = 0;

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
        this.springArray = new char[foldedArray.length * 5 + 6];
        //repeat five times:
        for (int i = 0; i < 5; i++) {
            //Copy each character in the folded array
            System.arraycopy(foldedArray, 0, springArray, i * (foldedArray.length + 1), foldedArray.length);
            // Then add a joining '?'
            if (i != 0){
                springArray[(i) * (foldedArray.length+1) - 1] = '?';
            }
        }
        // Add a final two working springs to make later calculations simpler
        springArray[foldedArray.length * 5 + 4] = '.';
        springArray[foldedArray.length * 5 + 5] = '.';
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
    }

    public long analyseLine() {

        treeValidPossibilities(0,  0, 0, 0, totalUSprings);

        return validNumberDistributions;
    }

    /** 
     * Recursive tree method (no idea if that's the technical term) iterating through the spring array from left to right
     * @param cDIndex The index of the contiguous array that we're currently solving for. Starts at 0 and increments when the end of a series of broken springs is reached (as long as the length of that series is what we're looking for)
     * @param contiguousCount The running count of the number of broken strings encountered in a row. Will be compared with the this.contiguousData[cDIndex]
     * @param sAIndex the index used to keep track of the code's progress down the springArray
     * @param brokenUStrings the number of unknown springs assigned to be broken
     * @param remainingUSprings the number of remaining unknown springs
     */
    private void treeValidPossibilities(int cDIndex, int contiguousCount, int sAIndex, int brokenUStrings, int remainingUSprings) {
        char nextChar = springArray[sAIndex];
        switch (nextChar) {
            case '#' -> brokenSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings, remainingUSprings);
            case '.' -> workingSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings, remainingUSprings);
            case '?' -> unknownSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings, remainingUSprings);
        }
    }

    private void brokenSpringLogic(int cDIndex, int contiguousCount, int sAIndex, int brokenUStrings, int remainingUSprings) {
        // If it's the first contiguous broken spring and there aren't any remaining contigs, discard the whole branch
        if (contiguousCount == 0 && cDIndex == contiguousData.size()){
            return;
        } else {
            // Else increment the current contiguous count then move on to the next character
            treeValidPossibilities(cDIndex,  contiguousCount + 1,sAIndex + 1,  brokenUStrings, remainingUSprings);
        }
    }

    private void workingSpringLogic(int cDIndex, int contiguousCount, int sAIndex, int brokenUStrings, int remainingUSprings) {
        // If we're at the end of the line, check if we've also used every contiguous instruction. If so, this is a valid branch
        // The final character is always '.'
        if (sAIndex == springArray.length - 1){
            if (cDIndex == this.contiguousData.size()){
                validNumberDistributions++;
            }
            return;
        }
        // If the current contiguous count is 0, move to the next character
        if (contiguousCount == 0){
            treeValidPossibilities(cDIndex, contiguousCount,sAIndex + 1, brokenUStrings, remainingUSprings);
        }
        // Else we're right at the end of a block of broken springs, so check their validity
        else {
            // If the contig count is right, move onto the next character and next contig, resetting the contig count
            if (contiguousCount == contiguousData.get(cDIndex)){
                treeValidPossibilities(cDIndex + 1,  0, sAIndex + 1, brokenUStrings, remainingUSprings);
            } else {
                return;
            }
        }
    }
    
    private void unknownSpringLogic(int cDIndex, int contiguousCount, int sAIndex, int brokenUStrings, int remainingUSprings) {
        // If every subsequent unknown spring needs to be broken to hit the quota of broken springs, this spring must be broken
        if (remainingUSprings + brokenUStrings == unacBSprings){
            brokenSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings + 1, remainingUSprings -1);
        }
        // If every broken spring is accounted for, this spring must be working
        else if (brokenUStrings == unacBSprings){
            workingSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings, remainingUSprings -1);
        }
        // Else, branch tree off with both options
        else {
            brokenSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings + 1, remainingUSprings -1);
            workingSpringLogic(cDIndex, contiguousCount, sAIndex, brokenUStrings, remainingUSprings -1);
        }
    }
}


