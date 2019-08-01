package com.example.sk_android.mvp.view.activity.resume

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
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
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.*
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UpLoadApi
import com.leon.lfilepickerlibrary.LFilePicker
import com.leon.lfilepickerlibrary.utils.Constant
import com.umeng.message.PushAgent
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import com.zhihu.matisse.listener.OnSelectedListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import withTrigger
import java.io.*

class ResumeListActivity : AppCompatActivity(), RlMainBodyFragment.Tool, RlOpeartListFragment.CancelTool,
    ShadowFragment.ShadowClick {

    override fun shadowClicked() {

    }

    private lateinit var myDialog: MyDialog
    lateinit var rlActionBarFragment: RlActionBarFragment
    var rlBackgroundFragment: RlBackgroundFragment? = null
    lateinit var rlMainBodyFragment: RlMainBodyFragment
    lateinit var baseFragment: FrameLayout
    var rlOpeartListFragment: RlOpeartListFragment? = null
    val REQUESTCODE_FROM_ACTIVITY = 1000
    lateinit var path: String
    // 简历格式
//    var typeArray:Array<String> = arrayOf(".word", ".jpg",".pdf",".doc",".docx",".xls",".xlsx")

    private val REQUEST_CODE_CHOOSE = 23

    private val REQUEST_PERMISSION = 10
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var tool = BaseTool()

    val arrayOfString: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE
    )

    private var mAdapter: UriAdapter? = null


    var shadowFragment: ShadowFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId = 1

        PushAgent.getInstance(this).onAppStart();

        baseFragment = frameLayout {
            id = mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    rlActionBarFragment = RlActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, rlActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    rlMainBodyFragment = RlMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, rlMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent) {
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            var hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            var hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            var permissions: MutableList<String> = arrayListOf()
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

            permissions.add(Manifest.permission.CAMERA);



            permissions.add(Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT)
            permissions.add(Manifest.permission.CAPTURE_VIDEO_OUTPUT)



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
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        rlActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun addList(resume: Resume) {
        addListFragment(resume)
    }


    @SuppressLint("ResourceType")
    fun addListFragment(resume: Resume) {

        var mTransaction = supportFragmentManager.beginTransaction()

        rlBackgroundFragment = RlBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, rlBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in, R.anim.bottom_in
        )


        rlOpeartListFragment = RlOpeartListFragment.newInstance(resume)
        mTransaction.add(baseFragment.id, rlOpeartListFragment!!)

        mTransaction.commit()
    }

    override fun cancelList() {
        var mTransaction = supportFragmentManager.beginTransaction()

        if (rlOpeartListFragment != null) {

            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )

            mTransaction.remove(rlOpeartListFragment!!)
            rlOpeartListFragment = null
        }

        if (rlBackgroundFragment != null) {

            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )

            mTransaction.remove(rlBackgroundFragment!!)
            rlBackgroundFragment = null

        }


        mTransaction.commit()
    }


    fun cancelList_withBG() {
        var mTransaction = supportFragmentManager.beginTransaction()

        if (rlOpeartListFragment != null) {

            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )

            mTransaction.remove(rlOpeartListFragment!!)
            rlOpeartListFragment = null
        }




        mTransaction.commit()
    }

    override fun sendEmail(resume: Resume) {
        cancelList()
        var intent = Intent(this, SendResumeActivity::class.java)
        var bundle = Bundle()
        bundle.putParcelable("resume", resume)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    override fun reName(resume: Resume) {
        cancelList_withBG()
        afterShowLoading(resume)
    }

    override fun delete(resume: Resume) {
        cancelList_withBG()
        var id = resume.id
        deleteShowLoading(id)
    }

    //弹出更新窗口
    fun afterShowLoading(resume: Resume) {
        println(resume)
        var id = resume.id
        var mediaId = resume.mediaId
        var mediaUrl = resume.mediaUrl
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_rename, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var updateText = view.findViewById<EditText>(R.id.update_id)
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.withTrigger().click {
            closeShadow()
            myDialog.dismiss()
        }
        determineBtn.withTrigger().click {
            println("---------------1")
            var name = tool.getEditText(updateText)
            if (name != "") {
                println(name)
                println(id)
                //构造HashMap(个人信息完善)
                val resumeParams = mapOf(
                    "type" to "ATTACHMENT",
                    "name" to name,
                    "mediaId" to mediaId,
                    "mediaUrl" to mediaUrl
                )
                val resumeJson = JSON.toJSONString(resumeParams)
                val resumeBody = RequestBody.create(json, resumeJson)
                var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

                retrofitUils.create(RegisterApi::class.java)
                    .updateInformation(resumeBody, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        println("123456789")
                        println(it)
                        if (it.code() in 200..299) {
                            startActivity<ResumeListActivity>()
                            finish()//返回
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    }, {
                        toast(this.getString(R.string.resumeReNameError))
                    })
            } else {
                println("2--------2")
                println(it)
                closeShadow()
                myDialog.dismiss()
            }
        }
    }


    fun showShadow() {
        rlBackgroundFragment = RlBackgroundFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(baseFragment.id, rlBackgroundFragment!!).commit()
    }


    fun closeShadow() {
        supportFragmentManager.beginTransaction().remove(rlBackgroundFragment!!).commit()
    }

    //弹出更新窗口
    fun deleteShowLoading(id: String) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_delete, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2

        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.withTrigger().click {
            closeShadow()
            myDialog.dismiss()
        }
        determineBtn.withTrigger().click {

            var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

            retrofitUils.create(RegisterApi::class.java)
                .deleteInformation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if (it.code() in 200..299) {
                        startActivity<ResumeListActivity>()
                        finish()//返回
                        overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    } else {
                        toast("削除が失敗しました")
                    }
                }, {})
            closeShadow()
            myDialog.dismiss()
        }
    }

    // https://blog.csdn.net/Px01Ih8/article/details/79767487
    override fun addVideo(number: Int) {
        println(number)
        if (number >= 3) {
            toast(this.getString(R.string.rlMaxNumber))
            return
        } else {
            val supportedMimeTypes = arrayOf(
                "image/jpeg",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            )
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.type = if (supportedMimeTypes.size == 1) supportedMimeTypes[0] else "*/*"
                if (supportedMimeTypes.size > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, supportedMimeTypes)
                }
            } else {
                var mimeTypes = ""
                for (mimeType in supportedMimeTypes) {
                    mimeTypes += "$mimeType|"
                }
                intent.type = mimeTypes.substring(0, mimeTypes.length - 1)
            }
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1)
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

    @SuppressLint("CheckResult")
    override fun dResume(id: String) {
        var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

        retrofitUils.create(RegisterApi::class.java)
            .deleteInformation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if (it.code() in 200..299) {
                    toast(this.getString(R.string.errorResumeDeleteFail))
                    refresh()
                } else {
                    toast(this.getString(R.string.errorResumeDeleteSuccess))
                }
            }, {})
    }

    //刷新当前页面及其数据
    fun refresh() {
        onCreate(null)
    }

    @SuppressLint("ResourceType")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            println(uri)
            println("12345")

            if ("file".equals(uri.scheme!!, ignoreCase = true)) {//使用第三方应用打开
                path = uri.path!!.toString()

            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri)!!.toString()

            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri)!!

            }
            rlMainBodyFragment.getPath(path)
        }
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        if (null != cursor && cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
            cursor.close()
        }
        return res
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {


        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT


        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]


                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {


                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )


                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]


                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }


                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])


                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }// File
        // MediaStore (and general)
        return null
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {


        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)


        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor!!.moveToFirst()) {
                val column_index = cursor!!.getColumnIndexOrThrow(column)
                return cursor!!.getString(column_index)
            }
        } finally {
            if (cursor != null)
                cursor!!.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }
}
