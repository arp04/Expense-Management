package com.example.expensemaster.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.expensemaster.data.AppDatabase
import com.example.expensemaster.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.expensemaster.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private lateinit var etAmount: EditText
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSaveExpense: Button
    private var userId: Int = 0

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Int): AddExpenseFragment {
            val fragment = AddExpenseFragment()
            val args = Bundle()
            args.putInt(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(ARG_USER_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)
        etAmount = view.findViewById(R.id.etAmount)
        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)
        btnSaveExpense = view.findViewById(R.id.btnSaveExpense)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSaveExpense.setOnClickListener {
            val amount = etAmount.text.toString().toDouble()
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            val expense = Expense(
                amount = amount,
                title = title,
                description = description,
                date = date,
                time = time,
                userId = userId
            )

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getDatabase(requireContext()).expenseDao().insert(expense)
                requireActivity().runOnUiThread {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }
}
