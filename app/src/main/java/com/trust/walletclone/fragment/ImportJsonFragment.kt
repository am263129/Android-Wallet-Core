import com.trust.walletclone.databinding.FragmentImportJsonBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trust.walletclone.util.Coin


class ImportJsonFragment : Fragment() {

    private lateinit var binding: FragmentImportJsonBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportJsonBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView(){
    }
}