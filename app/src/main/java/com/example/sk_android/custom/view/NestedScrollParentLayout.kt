package com.example.sk_android.custom.view

class NestedScrollParentLayout : RelativeLayout, NestedScrollingParent {
    private var mParentHelper: NestedScrollingParentHelper? = null
    private var mTitleHeight: Int = 0
    private var mTitleTabView: View? = null

    val nestedScrollAxes: Int
        get() = mParentHelper!!.getNestedScrollAxes()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        mParentHelper = NestedScrollingParentHelper(this)
    }

    //获取子view
    protected fun onFinishInflate() {
        mTitleTabView = this.findViewById(R.id.title_input_container)
    }

    protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mTitleHeight = mTitleTabView!!.getMeasuredHeight()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec + mTitleHeight)
    }

    //接口实现--------------------------------------------------

    //在此可以判断参数target是哪一个子view以及滚动的方向，然后决定是否要配合其进行嵌套滚动
    fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return if (target is NestedListView) {
            true
        } else false
    }


    fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        mParentHelper!!.onNestedScrollAccepted(child, target, nestedScrollAxes)
    }

    fun onStopNestedScroll(target: View) {
        mParentHelper!!.onStopNestedScroll(target)
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        if (dy > 0) {//手势向上滑动
            if (getScrollY() < mTitleHeight) {
                scrollBy(0, dy)//滚动
                consumed[1] = dy//告诉child我消费了多少
            }
        } else if (dy < 0) {//手势向下滑动
            if (getScrollY() > 0) {
                scrollBy(0, dy)//滚动
                consumed[1] = dy//告诉child我消费了多少
            }
        }
    }

    //后于child滚动
    fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

    }

    //返回值：是否消费了fling
    fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    //返回值：是否消费了fling
    fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        //        if (!consumed) {
        //            return true;
        //        }
        return false
    }


    //scrollBy内部会调用scrollTo
    //限制滚动范围
    fun scrollTo(x: Int, y: Int) {
        var y = y
        if (y < 0) {
            y = 0
        }
        if (y > mTitleHeight) {
            y = mTitleHeight
        }

        super.scrollTo(x, y)
    }
}
