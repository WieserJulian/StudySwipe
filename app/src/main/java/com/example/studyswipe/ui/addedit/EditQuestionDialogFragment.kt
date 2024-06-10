import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentEditQuestionDialogBinding
import com.example.studyswipe.ui.addedit.EditQuestionViewModel
import com.example.studyswipe.ui.addedit.EditTopicFragment

class EditQuestionDialogFragment : DialogFragment() {

    private var _binding: FragmentEditQuestionDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            _binding = FragmentEditQuestionDialogBinding.inflate(LayoutInflater.from(context))
            val uuid = arguments?.getString("questionUID")!!
            val editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            val toEditQuestion = editViewModel.getQuestion(uuid)!!
            binding.questionInput.setText(toEditQuestion.question)
            binding.answerInput.setText(toEditQuestion.answer)
            binding.pointsInput.setText(toEditQuestion.points.toString())
            binding.pointsInput.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString()
                    if (input.isNotEmpty()) {
                        val number = input.toInt()
                        if (number < 1 || number > 10) {
                            binding.pointsInput.error = "Points must be between 1 and 10"
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No action needed here
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // No action needed here
                }
            })
            binding.safeQuestion.setOnClickListener {
                val question = Question(
                    binding.questionInput.text.toString(),
                    binding.answerInput.text.toString(),
                    binding.pointsInput.text.toString().toInt(),
                )
                editViewModel.updateQuestion(uuid, question)
                (parentFragment as EditTopicFragment).displayQuestions(editViewModel.getAllQuestionsUuid())
                dismiss()
            }

            binding.deleteQuestion.setOnClickListener {
                editViewModel.removeQuestion(uuid)
                (parentFragment as EditTopicFragment).displayQuestions(editViewModel.getAllQuestionsUuid())
                dismiss()
            }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}