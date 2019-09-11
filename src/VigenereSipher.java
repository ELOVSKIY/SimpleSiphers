import java.util.ArrayList;
import java.util.List;

public class VigenereSipher {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"; // TODO поменять алфавит

    public static String encode(String plainText, String key) {
        key = key + plainText;
        StringBuffer cipherText = new StringBuffer("");
        for (int i = 0; i < plainText.length(); i++) {
            char plainChar = plainText.charAt(i);
            char keyChar = key.charAt(i);
            int position = (alphabet.indexOf(keyChar) + alphabet.indexOf(plainChar)) % alphabet.length();
            char cipherChar = alphabet.charAt(position);
            cipherText.append(cipherChar);
        }
        return cipherText.toString();
    }

    public static String decode(String cipherText, String key) {
        StringBuffer plainText = new StringBuffer("");
        List<String> blockList = parseTextIntoBlocks(cipherText, key.length());
        for (int i = 0; i < blockList.size(); i++){
            String plainBlock = decodeBlockAndReturnKey(blockList.get(i), key);
            key = plainBlock;
            plainText.append(plainBlock);
        }
        return plainText.toString();

    }

    private static String decodeBlockAndReturnKey(String block, String key) {
        StringBuffer plainBlock = new StringBuffer("");
        for (int i = 0; i < block.length(); i++) {
            char cipherChar = block.charAt(i);
            int keyChar = key.charAt(i);
            int position = (alphabet.indexOf(cipherChar) - alphabet.indexOf(keyChar) + alphabet.length()) % alphabet.length();
            char plainChar = alphabet.charAt(position);
            plainBlock.append(plainChar);
        }
        return plainBlock.toString();
    }

    private static List<String> parseTextIntoBlocks(String text, int keyLength) {
        boolean isNotEndOfText = true;
        List<String> parsedBlocks = new ArrayList<>();
        for (int i = 0; isNotEndOfText; i++) {
            int beginIndex = i * keyLength;
            int endIndex = beginIndex + keyLength;
            if (endIndex > text.length()) {
                endIndex = text.length();
                isNotEndOfText = false;
            }
            parsedBlocks.add(text.substring(beginIndex, endIndex));
        }
        return parsedBlocks;
    }


}
