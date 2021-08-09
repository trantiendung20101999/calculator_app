package com.example.calculatorapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    val LIST_ALL_DIGIT = listOf(
            CHAR_NUMBER_0,
            CHAR_NUMBER_1,
            CHAR_NUMBER_2,
            CHAR_NUMBER_3,
            CHAR_NUMBER_4,
            CHAR_NUMBER_5,
            CHAR_NUMBER_6,
            CHAR_NUMBER_7,
            CHAR_NUMBER_8,
            CHAR_NUMBER_9)

    private var listALlButton = listOf<Button>()

    companion object{
        const val CHAR_NUMBER_1 = '1'
        const val CHAR_NUMBER_2 = '2'
        const val CHAR_NUMBER_3 = '3'
        const val CHAR_NUMBER_4 = '4'
        const val CHAR_NUMBER_5 = '5'
        const val CHAR_NUMBER_6 = '6'
        const val CHAR_NUMBER_7 = '7'
        const val CHAR_NUMBER_8 = '8'
        const val CHAR_NUMBER_9 = '9'
        const val CHAR_NUMBER_0 = '0'
        const val DOUBLE_NUMBER_0 = 0.0
        const val EMPTY_STRING = ""
        const val DOT = '.'
        const val OPEN_BRACKET = '('
        const val CLOSE_BRACKET = ')'
        const val OPERATION_ADD = '+'
        const val OPERATION_SUB = '-'
        const val OPERATION_MUL = 'x'
        const val OPERATION_DIV = '/'
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setOnClickAll()
    }

    fun setOnClickAll(){
        with(binding){
            listALlButton = listOf(
                    buttonAllClear,
                    buttonAdd,
                    buttonSub,
                    buttonMul,
                    buttonDiv,
                    buttonOne,
                    buttonTwo,
                    buttonThree,
                    buttonFour,
                    buttonFive,
                    buttonSix,
                    buttonSeven,
                    buttonEight,
                    buttonNine,
                    buttonZero,
                    buttonBracketOpen,
                    buttonBracketClose,
                    buttonDot,
                    buttonEqual
            )
        }
        listALlButton.forEach {it.setOnClickListener(this)}

    }

    override fun onClick(v: View?) = with(binding) {
        when (v) {
            buttonAdd -> addTextToTextView(buttonAdd.text.toString(), textDisplay)
            buttonSub -> addTextToTextView(buttonSub.text.toString(), textDisplay)
            buttonMul -> addTextToTextView(buttonMul.text.toString(), textDisplay)
            buttonDiv -> addTextToTextView(buttonDiv.text.toString(), textDisplay)
            buttonAllClear -> textDisplay.text = EMPTY_STRING
            buttonBracketOpen -> addTextToTextView(buttonBracketOpen.text.toString(), textDisplay)
            buttonBracketClose -> addTextToTextView(buttonBracketClose.text.toString(), textDisplay)
            buttonDot -> addTextToTextView(buttonDot.text.toString(), textDisplay)
            buttonOne -> addTextToTextView(buttonOne.text.toString(), textDisplay)
            buttonTwo -> addTextToTextView(buttonTwo.text.toString(), textDisplay)
            buttonThree -> addTextToTextView(buttonThree.text.toString(), textDisplay)
            buttonFour -> addTextToTextView(buttonFour.text.toString(), textDisplay)
            buttonFive -> addTextToTextView(buttonFive.text.toString(), textDisplay)
            buttonSix -> addTextToTextView(buttonSix.text.toString(), textDisplay)
            buttonSeven -> addTextToTextView(buttonSeven.text.toString(), textDisplay)
            buttonEight -> addTextToTextView(buttonEight.text.toString(), textDisplay)
            buttonNine -> addTextToTextView(buttonNine.text.toString(), textDisplay)
            buttonZero -> addTextToTextView(buttonZero.text.toString(), textDisplay)
            buttonEqual -> textResult.text = evaluate(textDisplay.text.toString()).toString()

        }
    }


    private fun addTextToTextView(text: String, textView: TextView) {
        var textAfterChange = textView.text.toString()
        textAfterChange += text
        textView.text = textAfterChange
    }

    private fun evaluate(expression: String) : Double{
        var stackValues = Stack<Double>()
        var stackOperations = Stack<Char>()
        var num = EMPTY_STRING
        for (i in expression.indices){
            when (expression[i]) {
                in LIST_ALL_DIGIT -> num += expression[i]
                DOT -> num += expression[i]
                OPEN_BRACKET -> {
                    if (num != EMPTY_STRING) stackValues.push(num.toDouble())
                    num = EMPTY_STRING
                    stackOperations.push(expression[i])
                }
                CLOSE_BRACKET -> {
                    if (num != EMPTY_STRING) stackValues.push(num.toDouble())
                    num = EMPTY_STRING
                    while (stackOperations.peek() != OPEN_BRACKET)
                        stackValues.push(executeOperation(
                                stackOperations.pop(),
                                stackValues.pop(),
                                stackValues.pop())
                        )
                    stackOperations.pop()
                }
                else -> {
                    if (num != EMPTY_STRING) stackValues.push(num.toDouble())
                    num = EMPTY_STRING
                    while (!stackOperations.isEmpty() && checkPriority(expression[i],stackOperations.peek()))
                        stackValues.push(executeOperation(
                                stackOperations.pop(),
                                stackValues.pop(),
                                stackValues.pop())
                        )
                    stackOperations.push(expression[i])
                }
            }
            if (i == expression.length -1 && num != EMPTY_STRING) stackValues.push(num.toDouble())
        }
        while (stackOperations.isNotEmpty())
            stackValues.push(executeOperation(
                    stackOperations.pop(),
                    stackValues.pop(),
                    stackValues.pop())
            )
        return stackValues.pop()
    }

    private fun checkPriority(op1: Char, op2: Char): Boolean {
        if (op2 == OPEN_BRACKET || op2 == CLOSE_BRACKET) return false
        return if ((op1 == OPERATION_MUL || op1 == OPERATION_DIV) && (op2 == OPERATION_ADD || op2 == OPERATION_SUB)) false else true
    }

    private fun executeOperation(op: Char, b: Double, a: Double): Double {
        when (op) {
            OPERATION_ADD -> return a + b
            OPERATION_SUB -> return a - b
            OPERATION_MUL -> return a * b
            OPERATION_DIV -> return a / b
        }
        return DOUBLE_NUMBER_0
    }
}
