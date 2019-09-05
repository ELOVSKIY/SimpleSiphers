

fun main(){
    val N = 4
    val a = Array(4) {IntArray(4)}
    for (i in 0 until N / 2) {
        for (j in i until N.toInt() - i - 1) {

            val temp = a[i][j]
            a[i][j] = a[N - 1 - j][i]
            a[N - 1 - j][i] = a[N - 1 - i][N - 1 - j]
            a[N - 1 - i][N - 1 - j] = a[j][N - 1 - i]
            a[j][N - 1 - i] = temp
        }
    }
}