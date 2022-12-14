package com.example.mvvm_example.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_example.databinding.ActivityMainBinding
import com.example.mvvm_example.database.Memo
import com.example.mvvm_example.recyclerview.MemoRecyclerViewAdapter
import com.example.mvvm_example.repository.MemoRepository
import com.example.mvvm_example.viewmodel.MemoViewModel
import com.example.mvvm_example.viewmodel.MemoViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var viewModelFactory: MemoViewModelFactory
    lateinit var memoViewModel: MemoViewModel

    lateinit var memoList: MutableList<Memo>
    lateinit var memoRecyclerViewAdapter: MemoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initActivity()
    }

    private fun initActivity() {
        initViewModel()
        setUpObserver()
        getAllMemo()
        setUpBtnListener()
    }

    private fun initViewModel() {
        viewModelFactory = MemoViewModelFactory(MemoRepository())
        memoViewModel = ViewModelProvider(this, viewModelFactory).get(MemoViewModel::class.java)
    }

    @SuppressLint("LongLogTag")
    private fun setUpObserver() {

        memoViewModel.isGetAllMemoComplete.observe(this) { //메모를 불러온 후
            memoList = it.toMutableList() //getAllMemo 메서드를 통해 메모를 가져오고 옵저버를 통해 이를 감지하였다. 그렇기에 it은 memo 클래스의 정보를 가지고 있고 이것은 mutableList로 변환하여 전역변수인 memoList에 넣어줬다.
            Log.d("getList::", "size is " + it.size.toString())
            setUpRecyclerView()
        }

        memoViewModel.isMemoDeleteComplete.observe(this) { //메모 삭제
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            memoList.removeAt(position)
            memoRecyclerViewAdapter.notifyItemRemoved(position)
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }

        memoViewModel.isMemoDeleteByIdComplete.observe(this) { //메모 삭제
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            if(position != -1) {
                memoList.removeAt(position)
                memoRecyclerViewAdapter.notifyItemRemoved(position)
                memoRecyclerViewAdapter.notifyItemChanged(position)
            }
        }

        memoViewModel.isMemoInsertComplete.observe(this) { //메모를 입력한 후
                id ->
            Log.d("insertComplete::", "memo id is $id")
            memoList.add(Memo(id, binding.input.toString(), false))
            binding.input = ""
            memoRecyclerViewAdapter.notifyItemInserted(memoList.size - 1)
        }

        memoViewModel.isEdit.observe(this) { // 메모 수정 모드에 진입하면
            binding.isEditing = it.isEdit
            val position = memoList.indexOf(it.memo)

            if(it.isEdit) {
                if(memoRecyclerViewAdapter.lastEditIdx != -1) {
                    memoList[memoRecyclerViewAdapter.lastEditIdx].editMode = false
                    memoRecyclerViewAdapter.notifyItemChanged(memoRecyclerViewAdapter.lastEditIdx)
                    Log.d("#완료 버튼을 누르지 않고 다른 위치의 아이템의 수정하기 클릭",memoRecyclerViewAdapter.lastEditIdx.toString())
                }
                else{
                    Log.d("#다른 위치의 아이템의 수정하기 이동 안함",memoRecyclerViewAdapter.lastEditIdx.toString())
                }

                memoRecyclerViewAdapter.lastEditIdx = position
                memoList[position].editMode = true
                memoRecyclerViewAdapter.notifyItemChanged(position)
                Log.d("#lastEditIdx의 값을 현재 아이템의 위치값으로 초기화",memoRecyclerViewAdapter.lastEditIdx.toString())
            }

        }

        memoViewModel.isMemoModifyComplete.observe(this) { //메모 수정 완료 했을때
            Log.d("modifyComplete::", "memo modified")

            val position = memoList.indexOf(it.memo)

            memoList[position].memo = it.editMemo
            memoList[position].editMode = false

            memoRecyclerViewAdapter.lastEditIdx = -1
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }
    }

    private fun getAllMemo() {//memoViewModel을 통해 모든 memo를 가져온다.
        memoViewModel.getAllMemo()
    }

    private fun insertMemo() { //binding.input에 값이 존재한다면 memo 객체를 생성해 memoViewModel을 통해 메모 삽입
        if(binding.input.toString().trim().isEmpty().not()) {
            val memo = Memo(0, binding.input.toString(), false)
            memoViewModel.insertMemo(memo)
        }
    }

    private fun setUpRecyclerView() { //리사이클러뷰 어댑터, 레이아웃 매니저 초기화
        memoRecyclerViewAdapter = MemoRecyclerViewAdapter(baseContext, memoList, memoViewModel)

        binding.mainRecyclerView.adapter = memoRecyclerViewAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    }

    private fun setUpBtnListener() { //완료 버튼을 클릭했을때
        binding.inputBtn.setOnClickListener { insertMemo() }
    }
}
