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
import android.widget.EditText
import android.widget.TextView
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
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import withTrigger
import java.io.*

class ResumeListActivity:AppCompatActivity(),RlMainBodyFragment.Tool,RlOpeartListFragment.CancelTool,
    ShadowFragment.ShadowClick {

    override fun shadowClicked() {

    }

    private lateinit var myDialog : MyDialog
    lateinit var rlActionBarFragment: RlActionBarFragment
    var rlBackgroundFragment:RlBackgroundFragment? = null
    lateinit var rlMainBodyFragment:RlMainBodyFragment
    lateinit var baseFragment:FrameLayout
    var rlOpeartListFragment:RlOpeartListFragment? = null
    val REQUESTCODE_FROM_ACTIVITY = 1000
    // 简历格式
    var typeArray:Array<String> = arrayOf(".word", ".jpg",".pdf")

    private val REQUEST_CODE_CHOOSE = 23

    private val REQUEST_PERMISSION = 10
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var tool = BaseTool()

    val arrayOfString: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE)

    private var mAdapter: UriAdapter? = null


    var shadowFragment:ShadowFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1

        PushAgent.getInstance(this).onAppStart();

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
                    rlMainBodyFragment = RlMainBodyFragment.newInstance()
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
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        rlActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun addList(resume: Resume) {
        addListFragment(resume)
    }


    @SuppressLint("ResourceType")
    fun addListFragment(resume:Resume) {

        var mTransaction=supportFragmentManager.beginTransaction()

        rlBackgroundFragment = RlBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, rlBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)


        rlOpeartListFragment = RlOpeartListFragment.newInstance(resume)
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


    fun cancelList_withBG() {
        var mTransaction=supportFragmentManager.beginTransaction()

        if (rlOpeartListFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)

            mTransaction.remove(rlOpeartListFragment!!)
            rlOpeartListFragment = null
        }




        mTransaction.commit()
    }

    override fun sendEmail(resume:Resume) {
        cancelList()
        var intent=Intent(this,SendResumeActivity::class.java)
        var bundle = Bundle()
        bundle.putParcelable("resume",resume)
        intent.putExtra("bundle",bundle)
        startActivity(intent)
                        overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    override fun reName(resume: Resume) {
        cancelList_withBG()
        afterShowLoading(resume)
    }

    override fun delete(resume:Resume) {
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
            if(name != ""){
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
                val resumeBody = RequestBody.create(json,resumeJson)
                var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

                retrofitUils.create(RegisterApi::class.java)
                    .updateInformation(resumeBody,id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        println("123456789")
                        println(it)
                        if(it.code() in 200..299){
                            startActivity<ResumeListActivity>()
                            finish()//返回
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    },{
                        toast(this.getString(R.string.resumeReNameError))
                    })
            }else{
                println("2--------2")
                println(it)
                closeShadow()
                myDialog.dismiss()
            }
        }
    }


    fun showShadow(){
        rlBackgroundFragment= RlBackgroundFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(baseFragment.id,rlBackgroundFragment!!).commit()
    }


    fun closeShadow(){
        supportFragmentManager.beginTransaction().remove(rlBackgroundFragment!!).commit()
    }

    //弹出更新窗口
    fun deleteShowLoading(id:String) {
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
                    if(it.code() in 200..299){
                        startActivity<ResumeListActivity>()
                    }else{
                        toast("删除简历失败了")
                    }
                },{})
            closeShadow()
            myDialog.dismiss()
        }
    }

    // https://blog.csdn.net/Px01Ih8/article/details/79767487
    override fun addVideo(number:Int) {
        println(number)
        if(number>=3){
            toast("简历已经达到上限,请自行删除之后再次创建！")
            return
        }else {
            LFilePicker()
                .withActivity(this@ResumeListActivity)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withTitle("選択を再開")
                .withMutilyMode(false)  //true:多选，false:单选
                .withFileFilter(typeArray) // 限制显示文件类型
                .start()
        }
    }




    @SuppressLint("CheckResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTCODE_FROM_ACTIVITY && resultCode == Activity.RESULT_OK) {

            var list = data!!.getStringArrayListExtra(Constant.RESULT_INFO);
            println("获得的文件路径")
            println(list)

            val file = File(list[0])

            var fileByte = getByteByVideo(list[0])

            val fileBody = FormBody.create(MediaType.parse("multipart/form-data"), fileByte)

            val multipart = MultipartBody.Builder()
                .setType(com.example.sk_android.utils.MimeType.MULTIPART_FORM_DATA)
                .addFormDataPart("bucket", "user-resume-attachment")
                .addFormDataPart("type", "AUDIO")
                .addFormDataPart("file",file.name, fileBody)
                .build()

            var retrofitUils = RetrofitUtils(this,this.getString(R.string.storageUrl))
            retrofitUils.create(UpLoadApi::class.java)
                .upLoadFile(multipart)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .subscribe({
                    var mediaUrl = it.body()!!.asJsonObject.get("url").toString().replace("\"","")
                    var mediaId = it.body()!!.asJsonObject.get("media_key").toString().replace("\"","")
                    rlMainBodyFragment.submitResume(mediaId,mediaUrl)
                },{
                    toast(this.getString(R.string.resumeUploadError))
                })
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

    private fun getByteByVideo(url: String): ByteArray? {
        val file = File(url)
        if(file.length() > 1024*1024*10){
            toast("文件过大,请重新选择！！")
            return null
        }
        var out: ByteArrayOutputStream? = null
        try {
            val inn = FileInputStream(file)
            out = ByteArrayOutputStream()
            val b = ByteArray(1024)
            while (inn.read(b) != -1) {
                out.write(b, 0, b.size)
            }
            out.close()
            inn.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out!!.toByteArray()
    }


    @SuppressLint("CheckResult")
    override fun dResume(id: String) {
        var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

        retrofitUils.create(RegisterApi::class.java)
            .deleteInformation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if(it.code() in 200..299){
                    toast("无效简历,已被删除")
                    refresh()
                }else{
                    toast("删除简历失败了,请重试")
                }
            },{})
    }

    //刷新当前页面及其数据
    fun refresh() {
        onCreate(null)
    }
}
