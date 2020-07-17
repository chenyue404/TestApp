package com.cy.testapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var itemModels: List<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        itemModels = listOf(
            ItemModel(Intent(this, AnimPopActivity::class.java), "AnimPopActivity", "小气泡弹窗动画"),
            ItemModel(Intent(this, DialogActivity2::class.java), "DialogActivity2", "小气泡弹窗动画2"),
            ItemModel(Intent(this, ScrollActivity::class.java), "ScrollActivity", "滑动demo")
        )

        rv_list.adapter = MyAdapter(itemModels)
        rv_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    class ItemModel(
        val intent: Intent,
        val title: String,
        val description: String
    ) {
    }

    class MyAdapter(private val dataList: List<ItemModel>) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return dataList.size
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv_title.text = dataList[position].title
            holder.tv_description.text = dataList[position].description
            holder.itemView.setOnClickListener {
                it.context.startActivity(dataList[position].intent)
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv_title: TextView = itemView.findViewById(R.id.tv_title)
            val tv_description: TextView = itemView.findViewById(R.id.tv_content)
        }
    }
}
