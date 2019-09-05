
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val plainText = "ШCСОТТDИAЕФEFРКB"
        val key = "КРИПТОГРАФИЯ"

        print(RotatingSquare.decode(plainText))

    }
}
