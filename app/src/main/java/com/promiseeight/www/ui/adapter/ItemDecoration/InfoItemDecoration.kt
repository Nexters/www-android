package com.promiseeight.www.ui.adapter.ItemDecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.ui.common.util.dpToPx

// Input 화면 중 날짜,시간대 와 장소 목록에서 아이템 마진을 위한 ItemDecoration
class InfoItemDecoration(
    val context: Context
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val spacing = 4.dpToPx(context)

        val idx = parent.getChildAdapterPosition(view)
        if(idx < 0) return

        val lastIdx = (parent.adapter?.itemCount ?: 1) - 1
        if(lastIdx < 0) return


        if(idx != 0) outRect.left = spacing
        if(idx != lastIdx) outRect.right = spacing
    }
}