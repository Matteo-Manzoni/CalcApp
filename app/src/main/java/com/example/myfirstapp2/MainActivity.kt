package com.example.myfirstapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfirstapp2.databinding.ActivityMainBinding
import java.text.DecimalFormat

enum class CalculatorMode {
    None,Add,Subtract,Multiplication
}


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastButtonWasMode = false
    private var currentMode = CalculatorMode.None
    private var labelString = ""
    private var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCalculator()

    }

    private fun setupCalculator() {
        val allButtons = arrayOf(binding.button0, binding.button1, binding.button2, binding.button3, binding.button4, binding.button5,
        binding.button6, binding.button7, binding.button8, binding.button9)
        for (i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        binding.buttonAdd.setOnClickListener { changeMode(CalculatorMode.Add) }
        binding.buttonSub.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        binding.buttonTimes.setOnClickListener { changeMode(CalculatorMode.Multiplication) }
        binding.buttonEquals.setOnClickListener { didPressEquals() }
        binding.buttonC.setOnClickListener { didPressClear() }
    }

    private fun didPressEquals() {
        if (lastButtonWasMode) {
            return
        }

        val labelInt = labelString.toInt()

        when(currentMode) {
            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract -> savedNum -= labelInt
            CalculatorMode.Multiplication -> savedNum *= labelInt
            CalculatorMode.None -> return
        }

        currentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }

    private fun didPressClear() {
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString = ""
        savedNum = 0
        binding.textView.text ="0"
    }

    private fun updateText() {

        if(labelString.length > 8){
            didPressClear()
            binding.textView.text = "Too big"
            return
        }

        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if (currentMode == CalculatorMode.None){
            savedNum = labelInt
        }

        val df = DecimalFormat("#,##0")

        binding.textView.text = df.format(labelInt)
    }

    private fun changeMode(mode:CalculatorMode) {
        if (savedNum == 0){
            return
        }

        currentMode = mode
        lastButtonWasMode = true
    }

    private fun didPressNumber(num:Int) {
        val strVal = num.toString()

        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }
        labelString = "$labelString$strVal"
        updateText()
    }
}