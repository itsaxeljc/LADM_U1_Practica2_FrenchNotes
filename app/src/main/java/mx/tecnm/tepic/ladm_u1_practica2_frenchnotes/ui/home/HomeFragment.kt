package mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.CustomAdapter
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.CustomAdapter.Companion.details
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.CustomAdapter.Companion.titles
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.R
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var titres = ArrayList<String>()
    private var descrips = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView = binding.notas
        val adapter = CustomAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        leerArchivo(requireContext())

        binding.button.setOnClickListener{
            guardarArchivo(requireContext())
            leerArchivo(requireContext())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun guardarArchivo(context:Context){
        try {
            var archivo = OutputStreamWriter(context.openFileOutput("archivo.txt", 0))

            titles.add(binding.titre.text.toString())
            details.add(binding.desc.text.toString())
            var cadena = ""
            for(i in 0 until titles.size){
                val ti = titles[i]
                val de = details[i]
                cadena += ti + "&&" + de + "\n"
            }
            archivo.write(cadena)
            archivo.flush()
            archivo.close()

            binding.titre.setText("")
            binding.desc.setText("")
            AlertDialog.Builder(context).setMessage("SE GUARDO").show()
        } catch (e:Exception){
            AlertDialog.Builder(context).setMessage(e.message).show()
        }
    }

    fun leerArchivo(context:Context){ //requiereContext
        try {
            var archivo = BufferedReader(InputStreamReader(context.openFileInput("archivo.txt")))
            var cadena = archivo.readLines()
            titles.clear()
            details.clear()
            for(i in cadena){
                val info = i.split("&&")
                titles.add(info[0])
                details.add(info[1])
            }
        } catch (e:Exception){
            AlertDialog.Builder(context).setMessage(e.message).show()
        }
    }
}