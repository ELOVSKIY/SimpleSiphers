import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JRadioButton

class MainFrame : JFrame {
    val radioTableMethod = JRadioButton("Table method")
    val radioSwivelGril = JRadioButton("")
    val radioVigenere = JRadioButton("Vigenere")

    constructor() : super("Лаба Еловского") {
        val toolkit = Toolkit.getDefaultToolkit()

        this.isVisible = true
        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.title = "Elovsky"

        val dimension = toolkit.screenSize

        this.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
        this.minimumSize = Dimension(400, 400)
    }

}