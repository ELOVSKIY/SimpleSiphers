
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val plainTExt = "ааоооякооэнибблритаортаатпрк"
        val key = "КРИПТОГРАФИЯ"

        print(RotatingSquare.encode(plainTExt))

    }
}
