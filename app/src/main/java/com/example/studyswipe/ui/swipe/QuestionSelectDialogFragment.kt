import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.databinding.FragmentQuestionSelectBinding
import com.example.studyswipe.ui.home.HomeFragmentDirections
import com.example.studyswipe.ui.home.HomeViewModel

class QuestionSelectDialogFragment : DialogFragment() {

    private var _binding: FragmentQuestionSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            _binding = FragmentQuestionSelectBinding.inflate(LayoutInflater.from(context))

            val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

            binding.buttonAll.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationSwipeCard(homeViewModel.getLastTopic(), "positive")
                findNavController().navigate(action)
            }
            binding.buttonRetryNegative.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationSwipeCard(homeViewModel.getLastTopic(), "retry")
                findNavController().navigate(action)
            }
            binding.buttonNegative.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationSwipeCard(homeViewModel.getLastTopic(), "negative")
                findNavController().navigate(action)
            }

            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}