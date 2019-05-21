package com.example.sk_android.mvp.view.activity.resume

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.fragment.person.RlActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.RlOpeartListFragment
import com.example.sk_android.mvp.view.fragment.resume.RlBackgroundFragment
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import android.content.Intent
import android.util.Log
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import com.zhihu.matisse.listener.OnSelectedListener


class ResumeListActivity:AppCompatActivity(),RlMainBodyFragment.Tool,RlOpeartListFragment.CancelTool {
    private lateinit var myDialog : MyDialog
    lateinit var rlActionBarFragment: RlActionBarFragment
    var rlBackgroundFragment:RlBackgroundFragment? = null
    lateinit var baseFragment:FrameLayout
    var rlOpeartListFragment:RlOpeartListFragment? = null

    private val REQUEST_CODE_CHOOSE = 23

    private val REQUEST_PERMISSION = 10

    val arrayOfString: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)



    private var mAdapter: UriAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1
        baseFragment = frameLayout {
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    rlActionBarFragment= RlActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,rlActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val rlMainBodyFragment = RlMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, rlMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent){
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            var hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            var hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            var permissions: MutableList<String> = arrayListOf();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(
                    this, arrayOfString, REQUEST_PERMISSION
                )
            }

        }
    }



    override fun onStart() {
        super.onStart()
        setActionBar(rlActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@ResumeListActivity, 0, rlActionBarFragment.TrpToolbar)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    override fun addList() {
        addListFragment()
    }


    @SuppressLint("ResourceType")
    fun addListFragment() {

        var mTransaction=supportFragmentManager.beginTransaction()

        rlBackgroundFragment = RlBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, rlBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)


        rlOpeartListFragment = RlOpeartListFragment.newInstance()
        mTransaction.add(baseFragment.id, rlOpeartListFragment!!)

        mTransaction.commit()
    }

    override fun cancelList() {
        var mTransaction=supportFragmentManager.beginTransaction()

        if (rlOpeartListFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)

            mTransaction.remove(rlOpeartListFragment!!)
            rlOpeartListFragment = null
        }

        if (rlBackgroundFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)

            mTransaction.remove(rlBackgroundFragment!!)
            rlBackgroundFragment = null

        }


        mTransaction.commit()
    }

    override fun sendEmail() {
        cancelList()
        startActivity<SendResumeActivity>()
    }

    override fun reName() {
        cancelList()
        afterShowLoading()
    }

    override fun delete() {
        cancelList()
        deleteShowLoading()
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_rename, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }

    //弹出更新窗口
    fun deleteShowLoading() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_delete, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }

    override fun addVideo() {

        Matisse.from(this@ResumeListActivity)
            .choose(MimeType.ofAll(), false)
            .countable(true)
            .capture(true)
            .captureStrategy(
                CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test")
            )
            .maxSelectable(9)
            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
            .gridExpectedSize(
                resources.getDimensionPixelSize(R.dimen.grid_expected_size)
            )
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.85f)
            //                                            .imageEngine(new GlideEngine())  // for glide-V3
            .imageEngine(Glide4Engine())    // for glide-V4
            .setOnSelectedListener(OnSelectedListener { uriList, pathList ->
                // DO SOMETHING IMMEDIATELY HERE
                Log.e("onSelected", "onSelected: pathList=$pathList")
            })
            .originalEnable(true)
            .maxOriginalSize(10)
            .autoHideToolbarOnSingleTap(true)
            .setOnCheckedListener(OnCheckedListener { isChecked ->
                // DO SOMETHING IMMEDIATELY HERE
                Log.e("isChecked", "onCheck: isChecked=$isChecked")
            })
            .forResult(REQUEST_CODE_CHOOSE)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            mAdapter!!.setData(Matisse.obtainResult(data!!), Matisse.obtainPathResult(data))
            Log.e("OnActivityResult ", Matisse.obtainOriginalState(data).toString())
        }
    }

    private class UriAdapter : RecyclerView.Adapter<UriAdapter.UriViewHolder>() {

        private var mUris: List<Uri>? = null
        private var mPaths: List<String>? = null

        internal fun setData(uris: List<Uri>?, paths: List<String>?) {
            mUris = uris
            mPaths = paths
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UriViewHolder {
            return UriViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.uri_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
            holder.mUri.text = mUris!![position].toString()
            holder.mPath.text = mPaths!![position]

            holder.mUri.alpha = if (position % 2 == 0) 1.0f else 0.54f
            holder.mPath.alpha = if (position % 2 == 0) 1.0f else 0.54f
        }

        override fun getItemCount(): Int {
            return if (mUris == null) 0 else mUris!!.size
        }

        internal class UriViewHolder(contentView: View) : RecyclerView.ViewHolder(contentView) {

            val mUri: TextView
            val mPath: TextView

            init {
                mUri = contentView.findViewById(R.id.uri) as TextView
                mPath = contentView.findViewById(R.id.ipath) as TextView
            }
        }
    }
}
