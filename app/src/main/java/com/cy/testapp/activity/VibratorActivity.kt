package com.cy.testapp.activity

import android.os.Bundle
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.cy.testapp.R
import com.cy.testapp.ext.bind

/**
 * Created by chenyue on 2022/9/18 0018.
 */
class VibratorActivity : AppCompatActivity() {
    private val rvList by bind<RecyclerView>(R.id.rv_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList.adapter = ItemAdapter(
            listOf(
                Pair("LONG_PRESS", HapticFeedbackConstants.LONG_PRESS),
                Pair("VIRTUAL_KEY", HapticFeedbackConstants.VIRTUAL_KEY),
                Pair("KEYBOARD_TAP", HapticFeedbackConstants.KEYBOARD_TAP),
                Pair("CLOCK_TICK", HapticFeedbackConstants.CLOCK_TICK),
//                Pair("CALENDAR_DATE", HapticFeedbackConstants.CALENDAR_DATE),
                Pair("CONTEXT_CLICK", HapticFeedbackConstants.CONTEXT_CLICK),
                Pair("KEYBOARD_RELEASE", HapticFeedbackConstants.KEYBOARD_RELEASE),
                Pair("VIRTUAL_KEY_RELEASE", HapticFeedbackConstants.VIRTUAL_KEY_RELEASE),
                Pair("TEXT_HANDLE_MOVE", HapticFeedbackConstants.TEXT_HANDLE_MOVE),
//                Pair("ENTRY_BUMP", HapticFeedbackConstants.ENTRY_BUMP),
//                Pair("DRAG_CROSSING", HapticFeedbackConstants.DRAG_CROSSING),
                Pair("GESTURE_START", HapticFeedbackConstants.GESTURE_START),
                Pair("GESTURE_END", HapticFeedbackConstants.GESTURE_END),
//                Pair("EDGE_SQUEEZE", HapticFeedbackConstants.EDGE_SQUEEZE),
//                Pair("EDGE_RELEASE", HapticFeedbackConstants.EDGE_RELEASE),
                Pair("CONFIRM", HapticFeedbackConstants.CONFIRM),
                Pair("REJECT", HapticFeedbackConstants.REJECT),
            )
        )
    }

    class ItemAdapter(val dataList: List<Pair<String, Int>>) :
        RecyclerView.Adapter<ItemAdapter.VH>() {
        class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VH(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(50)
                gravity = Gravity.CENTER_VERTICAL
            })

        override fun onBindViewHolder(holder: VH, position: Int) {
            (holder.itemView as TextView).apply {
                text = dataList[position].first
                setOnClickListener {
                    it.performHapticFeedback(dataList[position].second)
                }
            }
        }

        override fun getItemCount() = dataList.size
    }
}