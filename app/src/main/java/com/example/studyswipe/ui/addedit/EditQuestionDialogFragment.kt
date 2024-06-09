import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.app.User
import com.example.studyswipe.databinding.FragmentEditQuestionDialogBinding
import com.example.studyswipe.databinding.FragmentQuestionSelectBinding
import com.example.studyswipe.ui.home.HomeFragmentDirections

class EditQuestionDialogFragment : DialogFragment() {

    private var _binding: FragmentEditQuestionDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            _binding = FragmentEditQuestionDialogBinding.inflate(LayoutInflater.from(context))

            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun openSwipeCards(topic: String, previousAttempt: PreviousAttempt) {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationSwipeCard(
            topic, previousAttempt
        )
        findNavController().navigate(action)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}