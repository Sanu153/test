package com.poditivity.onboarding.screens


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.poditivity.common.BaseFragment
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentCollegeDetailsBinding
import com.poditivity.onboarding.network.models.CollegeList
import com.poditivity.onboarding.network.models.CourseList
import com.poditivity.onboarding.network.models.SignUpRequest
import com.poditivity.onboarding.viewmodels.OnboardingViewmodel
import com.poditivity.social.SocialActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollegeDetailsFragment : BaseFragment<FragmentCollegeDetailsBinding>() {

    private val viewModel by viewModels<OnboardingViewmodel>()
    private lateinit var sem:String
    private lateinit var collegeCourseId:String
    private lateinit var yoj:String
    private val collegeMap = hashMapOf<String,String>()
    private val courseMap = hashMapOf<String,String>()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCollegeDetailsBinding {
        return FragmentCollegeDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpClickListeners()
        lifecycleScope.launch {
            viewModel.getCollegeList().observeResourceState(
                this@CollegeDetailsFragment,
                {
                    (activity as OnboardingActivity).hideLoading()

                    populateViews(it)
                },
                {(activity as OnboardingActivity).hideLoading()
                    showSnackbar(it)},
                {(activity as OnboardingActivity).showLoading()},
            )
        }
    }

    private fun populateViews(collegeData: CollegeList) {
        createMap(collegeData)
        val collegeAdapter = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, getColleges(collegeData))
        binding.college.autoCompleteTextView.setText("Select college")
        binding.college.autoCompleteTextView.setAdapter(collegeAdapter)
        binding.college.autoCompleteTextView.setOnItemClickListener { _, _, i, _ ->
            hideViews()

            lifecycleScope.launch {
                viewModel.getCollegeStreams(collegeMap[collegeData[i].collegeName]!!)
                    .observeResourceState(
                    this@CollegeDetailsFragment,
                    {
                        (activity as OnboardingActivity).hideLoading()
                        populateCourses(it)
                    },
                    {(activity as OnboardingActivity).hideLoading()
                        showSnackbar(it)},
                    {(activity as OnboardingActivity).showLoading()},
                )
            }
        }



        val text = "Already have an account? Login"

        val spannableString = SpannableString(text)

        spannableString.setSpan(UnderlineSpan(), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(com.poditivity.common.R.color.grey)), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.login.text = spannableString


    }

    private fun getColleges(collegeData: CollegeList): List<String>{
        val collegeList = arrayListOf<String>()

        collegeData.forEach {
            collegeList.add(it.collegeName)
        }

        return collegeList
    }

    private fun createMap(collegeData: CollegeList) {
        collegeData.forEach {
            collegeMap[it.collegeName] = it.collegeId
        }
    }

    private fun createCourseMap(courseList: CourseList) {
        courseList.forEach {
            it.courses.forEach {course->
                courseMap[course.courseName] = course.collegeCourseId
            }
        }
    }

    private fun populateCourses(courseList: CourseList){

        createCourseMap(courseList)
        val degree = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, getDegree(courseList))
        binding.degree.root.isVisible = true
        binding.degree.autoCompleteTextView.setText("Select degree")
        binding.degree.autoCompleteTextView.setAdapter(degree)
        binding.degree.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->



            val stream = ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                getStreams(courseList, courseList[i].streamName)
            )
            binding.stream.root.isVisible = true
            binding.stream.autoCompleteTextView.setText("Select streams")
            binding.stream.autoCompleteTextView.setAdapter(stream)
            binding.stream.autoCompleteTextView.setOnItemClickListener { _, _, i, _ ->
                this.collegeCourseId = courseMap[binding.stream.autoCompleteTextView.text.toString()]?:""
                showViews()
            }
        }




        val semList = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val years = arrayListOf("2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024")
        years.reverse()


        val sem = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            semList
        )
        binding.sem.autoCompleteTextView.setText("Current semester")
        binding.sem.autoCompleteTextView.setAdapter(sem)
        binding.sem.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            this.sem = semList[i]
        }

        val yoj = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, years)
        binding.yoj.autoCompleteTextView.setText("Year of Joining")
        binding.yoj.autoCompleteTextView.setAdapter(yoj)
        binding.yoj.autoCompleteTextView.setOnItemClickListener { _, _, i, _ ->
            this.yoj = years[i]
        }


        with(binding){
            username.textField.hint = "Username"
            password.textField.hint = "Password"
            cnf.textField.hint = "Confirm password"
            signup.button.text = "Step 3/3"
        }
    }

    private fun getDegree(courseList: CourseList): List<String> {
        val degree = arrayListOf<String>()

        courseList.forEach {
            degree.add(it.streamName)
        }
        return degree
    }

    private fun getStreams(courseList: CourseList, selectedDegree: String): List<String>{
        val streams = arrayListOf<String>()

        courseList.forEach {
            if(it.streamName == selectedDegree){
                it.courses.forEach { stream->
                    streams.add(stream.courseName)
                }
            }
        }
        return streams
    }

    override fun setUpViews() {

    }

    override fun setUpClickListeners() {
        with(binding){
            signup.button.setOnClickListener {
                if(!validateFields()){
                    return@setOnClickListener
                }
                lifecycleScope.launch {
                    viewModel.signUp(SignUpRequest(
                        collegeCourseId = this@CollegeDetailsFragment.collegeCourseId,
                        yearOfJoining = yoj.autoCompleteTextView.text.toString(),
                        currentSemester = sem.autoCompleteTextView.text.toString().toInt(),
                        userName = username.inputField.text.toString(),
                        password = password.inputField.text.toString()
                    )).observeResourceState(
                        this@CollegeDetailsFragment,
                        {(activity as OnboardingActivity).hideLoading()
                            openHomeActivity()
                        },
                        {(activity as OnboardingActivity).hideLoading()
                            showSnackbar(it)
                        },
                        {
                            (activity as OnboardingActivity).showLoading()
                        }
                    )
                }

            }

            login.setOnClickListener {
                findNavController().navigate(R.id.action_collegeDetailsFragment_to_loginFragment)
            }

            back.root.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }
    private fun openHomeActivity(){
        val prefs = context?.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        prefs?.edit()?.apply{
            putBoolean("IS_LOGGED_IN",true)
            apply()
        }

        val intent = Intent(requireActivity(), SocialActivity::class.java)
        activity?.finish()
        startActivity(intent)
    }

    override fun setUpObservers() {

    }

    private fun validateFields():Boolean{
        with(binding){
            if(yoj.autoCompleteTextView.text.toString() == "Year of joining") {
                showSnackbar("Please select your Year of joining")
                return false
            }
            if(sem.autoCompleteTextView.text.toString() == "Current semester") {
                showSnackbar("Please select your Current semester")
                return false
            }
            if(username.inputField.text.isNullOrEmpty() ||
                password.inputField.text.isNullOrEmpty() ||
                cnf.inputField.text.isNullOrEmpty()
                ){
                showSnackbar("Username or password can't be empty")
                return false
            }
        }

        return true
    }

    private fun showViews(){
        with(binding){
            username.root.isVisible = true
            password.root.isVisible = true
            cnf.root.isVisible = true
            yoj.root.isVisible = true
            sem.root.isVisible = true
            signup.root.isVisible = true
        }
    }

    private fun hideViews(){
        with(binding){
            degree.root.isVisible = false
            stream.root.isVisible = false
            username.root.isVisible = false
            password.root.isVisible = false
            cnf.root.isVisible = false
            yoj.root.isVisible = false
            sem.root.isVisible = false
            signup.root.isVisible = false
        }
    }




}