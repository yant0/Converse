package com.example.converse

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.converse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val conversionFactors = mapOf(
        "Cups" to mapOf("Tablespoons" to 16.0, "Ounces" to 10.0, "Milliliters" to 284.131, "Cups" to 1.0),
        "Tablespoons" to mapOf("Tablespoons" to 1.0, "Ounces" to 0.625, "Milliliters" to 17.7582, "Cups" to 0.0625),
        "Ounces" to mapOf("Tablespoons" to 1.6, "Ounces" to 1.0, "Milliliters" to 28.4131, "Cups" to 0.1),
        "Milliliters" to mapOf("Tablespoons" to 0.0563121, "Ounces" to 0.0351951, "Milliliters" to 1.0, "Cups" to 0.00351951))

    private lateinit var binding: ActivityMainBinding
    private lateinit var inputSpinner: Spinner
    private lateinit var outputSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputSpinner = findViewById(R.id.spinner_input)
        outputSpinner = findViewById(R.id.spinner_output)
        setupSpinners()

        binding.convertionResult.text = getString(R.string.placeholder_conversion_result)

        binding.converButton.setOnClickListener { calcConvertion() }
    }

    private fun calcConvertion() {

        // ===== chat gpt is scary bruh =====
        val inputUnit = inputSpinner.selectedItem.toString()
        val outputUnit = outputSpinner.selectedItem.toString()
        val stringInInput = binding.inputFieldEdit.text.toString()
        val input = stringInInput.toDoubleOrNull()

        if (input == null) {
            // Handle invalid input, e.g., show an error message
            binding.convertionResult.text = getString(R.string.null_input)
            return }

        // ===== check inputUnit's value to get which map
        // then outputUnit for specify which map inside that input unit
        val conversionFactor: Double = conversionFactors[inputUnit]?.get(outputUnit) ?: return
        var roundedConvertedValue: Double = kotlin.math.round(conversionFactor * input * 100)/100
        if  (binding.roundConvertionSwitch.isChecked) {
            roundedConvertedValue = kotlin.math.ceil(roundedConvertedValue) }

        val convertedResultValueToString = roundedConvertedValue.toString()
        binding.convertionResult.text = getString(R.string.convertion_result, convertedResultValueToString, outputUnit )

        /* ===== Original code by mostly me lol =====
        val stringInInput = binding.inputFieldEdit.text.toString()
        val input = stringInInput.toDoubleOrNull()
        if (input == null) {
            binding.convertionResult.text = getString(R.string.null_input)
            return }
        val convertionUnit: Double = when (binding.spinnerOutput.selectedItem) {
            "Ounces" -> 0.033814
            "Cups" -> 0.00422675
            "Milliliters" -> 1.0
            else -> 0.067628 }
        var roundedConvertionResult: Double = kotlin.math.round(convertionUnit * input * 100)/100
        if (binding.roundConvertionSwitch.isChecked) {
            roundedConvertionResult = kotlin.math.ceil(roundedConvertionResult) }
        val convertedOutputUnitToString = binding.spinnerOutput.selectedItem.toString()
        val convertResultToString = roundedConvertionResult.toString()
        binding.convertionResult.text = getString(R.string.convertion_result, convertResultToString, convertedOutputUnitToString)
         */
    }
    private fun setupSpinners() {
        val units = listOf("Ounces", "Milliliters", "Cups", "Tablespoons")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputSpinner.adapter = adapter
        outputSpinner.adapter = adapter
    }

    fun redirectToGitHubPage(view: View) {
        val githubPageUrl = "https://github.com/Yant0/Converse"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubPageUrl))
        startActivity(intent)
    }
}