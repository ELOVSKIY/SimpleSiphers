import java.lang.StringBuilder
import java.util.ArrayList

class ColumnMethod   {

    companion object {

        fun encode(plainText: String, key: String): String {

            val table = getTable(plainText, key)
            val columnOrder = getColumnOrder(key)

            val cipherText = StringBuilder("")
            val height: Int = table.size
            for (i in columnOrder) {
                for (j in 0 until height) {
                    val tableChar = table[j][i]
                    if (tableChar != '\u0000')
                        cipherText.append(tableChar)
                }
            }

            return cipherText.toString()
        }

        fun decode(cipherText: String, key: String): String {
            val table = getTable(cipherText, key)
            val columnOrder = getColumnOrder(key)
            val plainText = StringBuilder("")

            val width = table[0].size
            val height = table.size
            var maxHeightCount = cipherText.length % width

            if (maxHeightCount == 0) {
                maxHeightCount = width
            }
            var pos = 0
            for (i in columnOrder) {
                if (i < maxHeightCount) {
                    for (j in 0 until height) {
                        table[j][i] = cipherText[pos]
                        pos++
                    }
                } else {
                    for (j in 0 until height - 1) {
                        table[j][i] = cipherText[pos]
                        pos++
                    }
                }
            }
            for (i in table) {
                for (c in i) {
                    if (c != '\u0000') {
                        plainText.append(c)
                    }
                }
            }



            return plainText.toString()
        }

        fun getTable(str: String, key: String): Array<CharArray> {
            val charCount = str.length
            val width = key.length
            val height = if (charCount % width == 0) charCount / width else charCount / width + 1
            val table = Array(height) { CharArray(width) }

            for (i in 0 until charCount) {
                table[i / width][i % width] = str[i]
            }

            return table
        }

        fun getColumnOrder(cipherKey: String): List<Int> {
            val orderArray = ArrayList<Int>()
            for (i in cipherKey.indices) {
                var smallestChar = Char.MAX_VALUE
                var position = 0
                for (j in cipherKey.indices) {
                    val keyChar = cipherKey[j]
                    if (keyChar < smallestChar && !orderArray.contains(j)) {
                        smallestChar = keyChar
                        position = j
                    }
                }
                orderArray.add(position)
            }
            return orderArray

        }
    }
}

