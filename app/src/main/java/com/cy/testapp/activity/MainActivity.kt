package com.cy.testapp.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.chenyue404.androidlib.extends.bind
import com.cy.testapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var itemModels: List<ItemModel>
    private val rvList by bind<RecyclerView>(R.id.rv_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        itemModels = listOf(
            ItemModel(
                Intent(
                    this,
                    ThemeActivity::class.java
                ), "主题测试", "主题测试"
            ),
            ItemModel(
                Intent(
                    this,
                    NotificationActivity::class.java
                ), "通知测试", "通知测试"
            ),
            ItemModel(
                Intent(
                    this,
                    AppListActivity::class.java
                ), "应用列表", "应用列表"
            ),
            ItemModel(
                Intent(
                    this,
                    VibratorActivity::class.java
                ), "震动", "震动"
            ),
            ItemModel(
                Intent(
                    this,
                    GestureActivity::class.java
                ), "手势", "手势"
            ),
            ItemModel(
                Intent(
                    this,
                    ThreeFingerActivity::class.java
                ), "三指检测", "三指检测"
            ),
            ItemModel(
                Intent(
                    this,
                    MotionLayoutActivity::class.java
                ), "MotionLayoutActivity", "MotionLayoutActivity"
            ),
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
                    FlowActivity::class.java
                ), "FlowActivity", "Kotlin flow demo"
            ),
            ItemModel(
                Intent(
                    this,
                    WorkMangerActivity::class.java
                ), "WorkMangerActivity", "WorkManager demo"
            ),
            ItemModel(
                Intent(
                    this,
                    FloatWindowActivity::class.java
                ), "FloatWindowActivity", "悬浮窗demo"
            ),
            ItemModel(
                Intent(
                    this,
                    InputActivity::class.java
                ), "InputActivity", "输入框demo"
            ),
            ItemModel(
                Intent(
                    this,
                    RotationAnimActivity::class.java
                ), "RotationAnimActivity", "旋转动画"
            )
        )

        rvList.adapter =
            MyAdapter(itemModels)
        rvList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        startActivity(itemModels.last().intent)
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
            val itemModel = dataList[position]
            holder.tv_title.text = itemModel.title
            holder.tv_description.text = itemModel.description
            holder.itemView.setOnClickListener {
                try {
                    it.context.startActivity(itemModel.intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.itemView.tooltipText = itemModel.description
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv_title: TextView = itemView.findViewById(R.id.tv_title)
            val tv_description: TextView = itemView.findViewById(R.id.tv_content)
        }
    }
}
