package com.example.sehatyuk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etBeratBadan: EditText
    private lateinit var etTinggiBadan: EditText
    private lateinit var etUmur: EditText
    private lateinit var btnHitungBMI: Button
    private lateinit var btnHitungBMR: Button
    private lateinit var tvHasil: TextView
    private lateinit var layoutHasil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama        = findViewById(R.id.etNama)
        etBeratBadan  = findViewById(R.id.etBeratBadan)
        etTinggiBadan = findViewById(R.id.etTinggiBadan)
        etUmur        = findViewById(R.id.etUmur)
        btnHitungBMI  = findViewById(R.id.btnHitungBMI)
        btnHitungBMR  = findViewById(R.id.btnHitungBMR)
        tvHasil       = findViewById(R.id.tvHasil)
        layoutHasil   = findViewById(R.id.layoutHasil)

        btnHitungBMI.setOnClickListener {
            if (!validateInput()) {
                Toast.makeText(this,
                    "Harap isi semua kolom terlebih dahulu!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val nama   = etNama.text.toString().trim()
            val berat  = etBeratBadan.text.toString().toDouble()
            val tinggi = etTinggiBadan.text.toString().toDouble()

            val hasil = calculateBMI(berat, tinggi)
            val output = "Nama  : $nama\n" +
                    "Berat : $berat kg\n" +
                    "Tinggi: $tinggi cm\n\n" +
                    hasil
            tampilkanHasil(output)
        }

        btnHitungBMR.setOnClickListener {
            if (!validateInput()) {
                Toast.makeText(this,
                    "Harap isi semua kolom terlebih dahulu!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val nama   = etNama.text.toString().trim()
            val berat  = etBeratBadan.text.toString().toDouble()
            val tinggi = etTinggiBadan.text.toString().toDouble()
            val umur   = etUmur.text.toString().toInt()

            val kalori = calculateBMR(berat, tinggi, umur)
            val output = "Nama  : $nama\n" +
                    "Berat : $berat kg\n" +
                    "Tinggi: $tinggi cm\n" +
                    "Umur  : $umur tahun\n\n" +
                    "Kebutuhan Kalori Basal (BMR):\n" +
                    "${String.format("%.2f", kalori)} kkal/hari\n\n" +
                    "Dihitung dengan formula Mifflin-St Jeor."
            tampilkanHasil(output)
        }
    }

    // FUNCTION A: Validasi Input
    private fun validateInput(): Boolean {
        val nama   = etNama.text.toString().trim()
        val berat  = etBeratBadan.text.toString().trim()
        val tinggi = etTinggiBadan.text.toString().trim()
        val umur   = etUmur.text.toString().trim()
        return nama.isNotEmpty() &&
                berat.isNotEmpty() &&
                tinggi.isNotEmpty() &&
                umur.isNotEmpty()
    }

    // FUNCTION B: Hitung BMI dan Kategori
    private fun calculateBMI(weight: Double, height: Double): String {
        val tinggiMeter = height / 100.0
        val bmi = weight / (tinggiMeter * tinggiMeter)
        val kategori = when {
            bmi < 18.5          -> "Kurus (Underweight)"
            bmi in 18.5..24.9   -> "Normal"
            bmi in 25.0..29.9   -> "Kelebihan Berat Badan (Overweight)"
            else                -> "Obesitas"
        }
        return "BMI Anda : ${String.format("%.2f", bmi)}\n" +
                "Kategori : $kategori"
    }

    // FUNCTION C: Hitung BMR (Mifflin-St Jeor)
    private fun calculateBMR(weight: Double, height: Double, age: Int): Double {
        return (10 * weight) + (6.25 * height) - (5 * age) + 5
    }

    // Helper: tampilkan kotak hasil
    private fun tampilkanHasil(teks: String) {
        tvHasil.text = teks
        layoutHasil.visibility = View.VISIBLE
    }
}