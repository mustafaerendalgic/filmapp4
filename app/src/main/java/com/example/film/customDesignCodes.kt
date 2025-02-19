package com.example.film
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class customDesignCodes (val spancount : Int, val spacing : Int, val includeedge : Boolean)
    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (includeedge) {
            if (position == 0) {
                outRect.left = spacing
                outRect.right = spacing / 2
            }
            else if(position%2 == 0){
                outRect.left = spacing / 2
                outRect.right = spacing / 2
            }
            else{
                outRect.left = spacing/2
                outRect.right = spacing/2
            }
        }

            if(position<spancount){
                outRect.top = spacing/2
            }
            outRect.bottom = spacing/2

        }
}

class customDesignCodesfor2 (val spancount : Int, val spacing : Int, val includeedge : Boolean)
    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (includeedge) {
            if(position%2 == 0){
                outRect.left = spacing
                outRect.right = spacing/2
            }
            else{
                outRect.right = spacing
                outRect.left = spacing/2
            }
        }
        if(position<spancount){
            outRect.top = spacing/2
        }
        outRect.bottom = spacing
    }
}

class itemdecfortriple(val space : Int) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val totalColumns = 3
        val column = position % totalColumns

        outRect.left = space * (totalColumns - column) / totalColumns
        outRect.right = space * (column + 1) / totalColumns
        outRect.bottom = space * 3 / 2
    }
}

class LeftSnapHelper(val spacing: Int) : LinearSnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View

    ): IntArray? {
        if (layoutManager !is LinearLayoutManager) return null

        val out = IntArray(2)
        out[0] = targetView.left - spacing
        out[1] = 0
        return out

    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager !is LinearLayoutManager) return super.findSnapView(layoutManager)

        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val firstVisibleView = layoutManager.findViewByPosition(firstVisiblePosition)
        val secondVisibleView = layoutManager.findViewByPosition(firstVisiblePosition + 1)

        if (firstVisibleView != null) {

            val firstVisibleWidth = firstVisibleView.width
            val offset = firstVisibleView.right

            return if (offset > firstVisibleWidth / 2 ) {
                firstVisibleView
            } else {
                secondVisibleView
            }

        }
        return null
    }
}


class rvs(rv : RecyclerView, listesi : List<film>, spacing: Int){
    val adapter = Adaptermain()
    val leftSnapHelper = LeftSnapHelper(spacing)
    init{
        listofrv.add(this)
        if(rv.onFlingListener == null){
            rv.addItemDecoration(customDesignCodes(listesi.size, spacing, true))
            leftSnapHelper.attachToRecyclerView(rv)
        }
        adapter.submitList(listesi)
        val lay_mang = LinearLayoutManager(rv.context)
        lay_mang.orientation = LinearLayoutManager.HORIZONTAL
        rv.adapter = adapter
        rv.layoutManager = lay_mang
    }
}








