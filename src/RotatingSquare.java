import java.util.ArrayList;
import java.util.List;

public class RotatingSquare {
    private static boolean[][] grid = {
            {true, false, false, false},
            {false, false, false, true},
            {false, false, true, false},
            {false, true, false, false}};

    public static String encode(String plainText) {
        StringBuffer cipherText = new StringBuffer("");
        List<String> blockList = parseStringToBlocks(plainText);
        for (int i = 0; i < blockList.size(); i++) {
            cipherText.append(encodeBlock(blockList.get(i)));
        }
        return cipherText.toString();
    }

    public static String decode(String cipherText) {
        StringBuffer plainText = new StringBuffer("");
        List<String> blockList = parseStringToBlocks(cipherText);
        for (int i = 0; i < blockList.size(); i++){
            plainText.append(decodeBlock(blockList.get(i)));
        }
        return plainText.toString();

    }

    private static void rotateGrid() {
        int size = RotatingSquare.grid.length;
        for (int i = 0; i < size / 2; i++) {
            for (int j = i; j < size - i - 1; j++) {
                boolean temp = grid[i][j];
                grid[i][j] = grid[size - 1 - j][i];
                grid[size - 1 - j][i] = grid[size - 1 - i][size - 1 - j];
                grid[size - 1 - i][size - 1 - j] = grid[j][size - 1 - i];
                grid[j][size - 1 - i] = temp;
            }
        }
    }

    private static String decodeBlock(String block) {
        char[][] charMatrix = new char[4][4];
        for (int i = 0; i < block.length(); i++) {
            charMatrix[i / 4][i % 4] = block.charAt(i);
        }

        StringBuffer decodedBlock = new StringBuffer("");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (grid[j][k]) {
                        decodedBlock.append(charMatrix[j][k]);
                    }
                }
            }
            rotateGrid();
        }
        return decodedBlock.toString();
    }

    private static String encodeBlock(String block) {
        char[][] charMatrix = new char[4][4];
        int position = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (grid[j][k]) {
                        charMatrix[j][k] = block.charAt(position);
                        position++;
                    }
                }
            }
            rotateGrid();
        }
        StringBuffer blockBuffer = new StringBuffer("");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                blockBuffer.append(charMatrix[i][j]);
            }
        }
        return blockBuffer.toString();
    }

    private static List<String> parseStringToBlocks(String text) {
        List<String> blockList = new ArrayList<>();
        text = expandString(text);

        int blockCount = text.length() / 16;
        for (int i = 0; i < blockCount; i++) {
            blockList.add(text.substring(i * 16, i * 16 + 16));
        }
        return blockList;
    }

    private static String expandString(String str) {
        char extraLetter = 'A';
        StringBuffer buffer = new StringBuffer(str);
        while (buffer.length() % 16 != 0) {
            buffer.append(extraLetter);
            extraLetter++;
            if (extraLetter > 'Z') {
                extraLetter = 'A';
            }
        }
        return buffer.toString();
    }
}
