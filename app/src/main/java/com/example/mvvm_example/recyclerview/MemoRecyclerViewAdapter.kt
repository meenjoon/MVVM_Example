package com.example.mvvm_example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_example.databinding.MemoHolderBinding
import com.example.mvvm_example.database.Memo
import com.example.mvvm_example.viewmodel.MemoViewModel


class MemoRecyclerViewAdapter (val context: Context, val itemList: MutableList<Memo>,
                               val memoViewModel: MemoViewModel) : RecyclerView.Adapter<MemoRecyclerViewAdapter.Holder>() {

    var lastEditIdx: Int = -1

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int
    ): Holder {
        val binding = MemoHolderBinding.inflate(LayoutInflater.from(context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(val binding: MemoHolderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Memo) {
            binding.memo = item
            binding.input = item.memo
            //input을 따로 만든 이유는 양방향 데이터 바인딩을 사용할 때
            //memo 모델의 memo 변수를 쓰면 리사이클러뷰 내에서 자동으로 업데이트 됨


            binding.editBtn.setOnClickListener { //수정 버튼을 누르면
                memoViewModel.changeMode(item,true)
            }

            binding.removeBtn.setOnClickListener { //지우기 버튼을 누르면
                memoViewModel.deleteMemoById(item)
            }

            binding.completeBtn.setOnClickListener { //완료 버튼을 누르면
                memoViewModel.changeMode(item,false)
                memoViewModel.modifyMemo(item,binding.input.toString())
            }
        }


    }

}
