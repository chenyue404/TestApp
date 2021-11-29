package com.cy.testapp.Activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.cy.testapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var itemModels: List<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        itemModels = listOf(
            ItemModel(
                Intent(
                    this,
                    AnimPopActivity::class.java
                ), "AnimPopActivity", "小气泡弹窗动画"
            ),
            ItemModel(
                Intent(
                    this,
                    DialogActivity2::class.java
                ), "DialogActivity2", "小气泡弹窗动画2"
            ),
            ItemModel(
                Intent(
                    this,
                    ScrollActivity::class.java
                ), "ScrollActivity", "滑动demo"
            ),
            ItemModel(
                Intent(
                    this,
                    FrescoActivity::class.java
                ), "FrescoActivity", "Fresco demo"
            ),
            ItemModel(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("weixin://dl/moments")
                ), "scheme test", "scheme test"
            ),
            ItemModel(
                Intent(
                    this,
                    LocationActivity::class.java
                ), "LocationActivity", "Location demo"
            ),
            ItemModel(
                Intent(
                    this,
                    AnimerActivity::class.java
                ), "AnimerActivity", "Animer demo"
            ),
            ItemModel(
                Intent(
                    this,
                    AnimerActivityJava::class.java
                ), "AnimerActivityJava", "Animer Java demo"
            ),
            ItemModel(
                Intent(
                    this,
                    FlowActivity::class.java
                ), "FlowActivity", "Kotlin flow demo"
            )
        )

        rv_list.adapter =
            MyAdapter(itemModels)
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
                try {
                    it.context.startActivity(dataList[position].intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv_title: TextView = itemView.findViewById(R.id.tv_title)
            val tv_description: TextView = itemView.findViewById(R.id.tv_content)
        }
    }
}
