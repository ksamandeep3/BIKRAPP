

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {


    var baseprogress: ProgressDialog? = null



    fun attachFragmentDashBoard(fragment: Fragment?) {
//        fragment?.let {
//            supportFragmentManager
//                ?.beginTransaction()
//                ?.add(R.id.container, it)
//                ?.commitAllowingStateLoss()
//        }
    }

    fun showProcessDialog(context: Context?, message: String?) {
        baseprogress = ProgressDialog(context).apply {
            setTitle("Loading..")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun hideProcessDialog() {
        baseprogress?.dismiss()
    }
}