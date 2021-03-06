package cn.zxd.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.zxd.app.R
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.ui.view.countdown.DigitCountDownView

/**
 * 封装一个有懒加载的 Fragment
 * @param T 传入泛型的 ViewBinding
 * @param layoutId 传入布局用来跳过在子类中初始化传入 inflater
 */
abstract class BaseFragment<T : ViewBinding>(private val layoutId: Int) : Fragment(layoutId) {
    private var isViewOK = false //是否完成 View 初始化
    private var isFirst = true //是否为第一次加载

    private var _binding: T? = null

    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        // 完成 initView 后改变view的初始化状态为完成
        isViewOK = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = initBinding(view)
        initView()
    }

    /**
     * 传入对应的 ViewBinding
     */
    abstract fun initBinding(view: View): T

    /**
     * fragment 初始化 view 的方法
     */
    abstract fun initView()

    override fun onResume() {
        super.onResume()
        //在 onResume 进行数据懒加载
        initLoadData()
        val view = _binding?.root?.findViewById(R.id.dcdv_countDown) as DigitCountDownView?
        if (view != null) {
            view.start()
            view.setCountDownTimerListener {
                if(activity != null) {
                    (activity as MainActivity).backToMain()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val view = _binding?.root?.findViewById(R.id.dcdv_countDown) as DigitCountDownView?
        view?.cancel()
    }

    private fun initLoadData() {
        if (isViewOK && isFirst) {
            //加载数据时判断是否完成view的初始化，以及是不是第一次加载此数据
            loadDate()
            //加载第一次数据后改变状态，后续不再重复加载
            isFirst = false
        }
    }

    /**
     * fragment 实现懒加载的方法，即在这里加载数据
     */
    abstract fun loadDate()

    //释放数据
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}