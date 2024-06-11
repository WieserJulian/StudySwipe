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
import com.example.studyswipe.databinding.FragmentQuestionSelectBinding
import com.example.studyswipe.ui.home.HomeFragmentDirections

class QuestionSelectDialogFragment : DialogFragment() {

    private var _binding: FragmentQuestionSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            _binding = FragmentQuestionSelectBinding.inflate(LayoutInflater.from(context))

            val topic = arguments?.getString("topic") ?: User.lastTopic
            if (topic == "") {
                return AlertDialog.Builder(it).setMessage("There was no correct Topic selected")
                    .create()
            }
            val title = resources.getString(R.string.selectQuestionTitle, topic)
            binding.selectDialogTitle.text = title
            val currentTopic = TopicLibrary.getTopic(topic)
            if (currentTopic.questions.none{ it.previousAttempt == PreviousAttempt.RETRY }) {
                binding.buttonRetryNegative.visibility = View.GONE
                binding.buttonRetry.visibility = View.GONE
            }
            if (currentTopic.questions.none { it.previousAttempt == PreviousAttempt.NEGATIVE }) {
                binding.buttonRetryNegative.visibility = View.GONE
                binding.buttonNegative.visibility = View.GONE
            }

            binding.buttonAll.setOnClickListener {
                openSwipeCards(topic, PreviousAttempt.POSITIVE)
            }
            binding.buttonRetryNegative.setOnClickListener {
                openSwipeCards(topic, PreviousAttempt.RETRY)
            }
            binding.buttonNegative.setOnClickListener {
                openSwipeCards(topic, PreviousAttempt.NEGATIVE)
            }
            binding.buttonRetry.setOnClickListener {
                openSwipeCards(topic, PreviousAttempt.RETRY)
            }

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