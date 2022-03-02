package mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.CustomAdapter
import mx.tecnm.tepic.ladm_u1_practica2_frenchnotes.databinding.FragmentSlideshowBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.notasSlide
        val adapter = CustomAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        leerArchivo(requireContext())

        binding.buscar2.setOnClickListener{
            val id = binding.idItem2.text.toString().toInt()
            try{
                binding.tituloEdit2.setText(CustomAdapter.titles[id])
                binding.descEdit2.setText(CustomAdapter.details[id])
                binding.editar2.setOnClickListener{
                    CustomAdapter.titles.removeAt(id)
                    CustomAdapter.details.removeAt(id)
                    guardarArchivo(requireContext())
                }
            }catch (e:Exception){
                AlertDialog.Builder(requireContext()).setMessage("Revise que el indice exista").show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun guardarArchivo(context: Context){
        try {
            var archivo = OutputStreamWriter(context.openFileOutput("archivo.txt", 0))

            var cadena = ""
            for(i in 0 until CustomAdapter.titles.size){
                val ti = CustomAdapter.titles[i]
                val de = CustomAdapter.details[i]
                cadena += ti + "&&" + de + "\n"
            }
            archivo.write(cadena)
            archivo.flush()
            archivo.close()

            binding.tituloEdit2.setText("")
            binding.descEdit2.setText("")
            binding.idItem2.setText("")
            AlertDialog.Builder(context).setMessage("Eliminado").show()
            leerArchivo(context)
        } catch (e:Exception){
            AlertDialog.Builder(context).setMessage(e.message).show()
        }
    }

    fun leerArchivo(context: Context){ //requiereContext
        try {
            var archivo = BufferedReader(InputStreamReader(context.openFileInput("archivo.txt")))
            var cadena = archivo.readLines()
            CustomAdapter.titles.clear()
            CustomAdapter.details.clear()
            for(i in cadena){
                val info = i.split("&&")
                CustomAdapter.titles.add(info[0])
                CustomAdapter.details.add(info[1])
            }
        } catch (e:Exception){
            AlertDialog.Builder(context).setMessage(e.message).show()
        }
    }
}