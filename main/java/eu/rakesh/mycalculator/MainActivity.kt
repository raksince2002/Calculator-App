package eu.rakesh.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvInput:TextView?=null
    var lastNumeric:Boolean=false
    var lastDot:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false
    }

    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append((view as Button).text)
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/")||value.contains("*")||value.contains("+")||value.contains("-")
        }
    }


    fun onEqual(view: View){
        var d:String?=null
        var e1:String?=null
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            if (tvValue.startsWith("-")) {
                prefix = "-"
                tvValue = tvValue.substring(1)
                if (tvValue.contains("+")) {
                    d = "+"
                } else if (tvValue.contains("-")) {
                    d = "-"
                } else if (tvValue.contains("*")) {
                    d = "*"
                } else if (tvValue.contains("/")) {
                    d = "/"
                }
                try {
                    val splitValue = tvValue.split(d.toString())
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (d == "+") {
                        var result = one.toDouble() - two.toDouble()
                        if(result.toString()=="0.0") {
                            prefix = ""
                        }
                        tvInput?.text = removeZeroAfterDot(prefix+result.toString())
                    }
                    if (d == "-") {
                        var result = one.toDouble() + two.toDouble()
                        tvInput?.text = removeZeroAfterDot(prefix+result.toString())

                    }
                    if (d == "*") {
                        var result = one.toDouble() * two.toDouble()
                        tvInput?.text = removeZeroAfterDot(prefix+result.toString())
                    }
                    if (d == "/") {
                        var result = one.toDouble() / two.toDouble()
                        tvInput?.text = removeZeroAfterDot(prefix+result.toString())
                    }
                } catch (e: ArithmeticException) {
                    println("Infinity")
                }
            } else {
                if (tvValue.contains("+")) {
                    e1 = "+"
                } else if (tvValue.contains("-")) {
                    e1 = "-"
                } else if (tvValue.contains("*")) {
                    e1 = "*"
                } else if (tvValue.contains("/")) {
                    e1 = "/"
                }
                try {
                    val splitValue = tvValue.split(e1.toString())
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (e1 == "+") {
                        var result = one.toDouble() + two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                    if (e1 == "-") {
                        var result = one.toDouble() - two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                    if (e1 == "*") {
                        var result = one.toDouble() * two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                    if (e1 == "/") {
                        var result = one.toDouble() / two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                } catch (e: ArithmeticException) {
                    println("Infinity")
                }
            }
        }
    }

    private fun removeZeroAfterDot(result:String):String{
        var value=result
        if(result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return value
    }

}