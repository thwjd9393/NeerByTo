package com.jscompany.neerbyto.trede

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.chat.ChatDetailActivity
import com.jscompany.neerbyto.databinding.ActivityTredeDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date


class TredeDetailActivity : AppCompatActivity() {

    val binding:ActivityTredeDetailBinding by lazy { ActivityTredeDetailBinding.inflate(layoutInflater) }

    var tredeNo : String = ""

    lateinit var imgList : MutableList<String>

    var isFirst : Boolean = true

    lateinit var count : String
    lateinit var otherImgUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tredeNo = intent.getStringExtra("tredeNo") ?: ""

        loadData() //데이터 로드

        Log.i("TAG","${tredeNo}")
        Log.i("TAG","${isFirst}")

        init()

    }

    private fun init() {
        //커스텀 액션바 등록
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setDisplayShowTitleEnabled(false) //타이틀

        //닉네임 layout누르면 프로필 화면으로 이동
        binding.nicnameWarp.setOnClickListener {  }

        //좋아요 버튼
        binding.btnLike.setOnClickListener {  }

        //채팅 버튼
        binding.btnChat.setOnClickListener {
            //프로필 저장
            if(binding.tvUserNo.text == Common.getUserNo(this)) {
                Common.makeToast(this,"내가 쓴 게시글입니다")
                return@setOnClickListener
            } else { //첫번째가 아닐때
                checkInRoomUser() //이미 참여한 채팅방일때 / 새로 참여
            }
        }

    }

    //파이어베이스에 유저 저장 - 이미지 있는 사람 없는 사람 분기처리
    private fun saveFireData() {
        //1. 관리 저장소 일단 소환
        val storage = FirebaseStorage.getInstance()

        //2. 참조 위치명이 중복되지않도록 날짜이용
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")

        val imgRef = storage.getReference("profileImg/IMG_" + sdf.format(Date())) //파일위치 참조객체

        //1. 서버의 firestore DB에 닉네임과 이미지 Url 저장
        val firestore = FirebaseFirestore.getInstance()
        val profileRef = firestore.collection("profiles")
        val profile: MutableMap<String, Any> = HashMap()

        if(Common.PROFILEURI == null) {
            //이미지 없으면 이미지 저장안하고 사용자 이름만 저장

            profile["profileUrl"] = ""

            profileRef.document(Common.getUserNo(this@TredeDetailActivity)).set(profile);

        } else {
            imgRef.putFile(Common.PROFILEURI!!).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot>{ //콜백의 콜백
                override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                    //profiles 라는 이름의 컬렉션 참조 객체 소환
                    profile["profileUrl"] = Common.PROFILEURL
                }

            })
        }

        profileRef.document(Common.getUserNo(this@TredeDetailActivity)).set(profile);

        //액티비티로 보내기 - 글쓰이가 아닐때만
        Log.i("TAG","확인 ${binding.tvUserNicname.text}")

        isFirst = false; //첫번째일때 만 등록
    }

    //화면이동
    private fun moveToChat(){
        startActivity(Intent(this@TredeDetailActivity,ChatDetailActivity::class.java)
            .putExtra("tredeNo",tredeNo) //글 번호
            )
    }

    private fun insertChat(){
        //파이어베이스에 채팅 방 만들기
        val firestore = FirebaseFirestore.getInstance();
        val chatRef = firestore.collection("Chat"); //컬렉션이름

        var writeUserNo = binding.tvUserNo.text.toString()
        var writeUserNic = binding.tvUserNicname.text.toString()
        var title = binding.tvTitle.text.toString()
        var joinTime = binding.tvHangOutTime.text.toString()
        var joinSpot = binding.tvHangOutSpot.text.toString()

        var users : MutableList<String> = mutableListOf()
        users.add(writeUserNo)
        users.add(Common.getUserNo(this))

        var chatRoom : MutableMap<String,Any> = mutableMapOf()

        chatRoom["tredeNo"] = tredeNo
        chatRoom["writeUserNo"] = writeUserNo
        chatRoom["writeUserNic"] = writeUserNic
        chatRoom["users"] = users
        chatRoom["title"] = title
        chatRoom["joinCount"] = count
        chatRoom["joinTime"] = joinTime
        chatRoom["joinSpot"] = joinSpot

        chatRef.document(tredeNo).set(chatRoom).addOnSuccessListener {
            Common.makeToast(this, "save")
        }

        Log.i("TAG","파이어베이스 ${chatRef}")
    }

    //접속인원 확인 후 추가
    private fun checkInRoomUser(){
        val firestore = FirebaseFirestore.getInstance();

        firestore.collection("Chat")
            .whereEqualTo("tredeNo",tredeNo)
            .get().addOnSuccessListener {
                if(it.documents.size <= 0){
                    //채팅이 없으면...
                    AlertDialog.Builder(this).setMessage("채팅에 참여하겠습니까?")
                        .setPositiveButton("참여하기"
                        ) { dialog, which ->
                            saveFireData()
                            insertChat()
                            moveToChat() }
                        .setNegativeButton("취소"
                        ) { dialog, which -> dialog.dismiss()}
                } else {
                    //채팅이 이미 있으면
                    var users : MutableList<String> = mutableListOf()
                    for (snapshot in it) {
                        //(요소 하나 임시 저장 변수: 값 가진 배열)
                        val data = snapshot.data
                        users = data["users"] as MutableList<String>
                    }

                    if (Common.getUserNo(this) in users) moveToChat()
                    else {
                        if(users.size > count.toInt()){
                            Log.i("TAG","${users.size}")
                            Log.i("TAG","유저 ${users}")
                            Common.makeToast(this,"인원이 다 찼습니다")
                            return@addOnSuccessListener
                        }else {
                            //리스트에 추가
                            updateChatUser(users)
                        }
                    }

                }
        }

    }
    
    //파이어베이스 - 채팅 참여자 변경
    private fun updateChatUser(users : MutableList<String>){

        var list : MutableList<String> = mutableListOf()

        for(i in users.indices){
            list.add(users[i])
        }
        list.add(Common.getUserNo(this))

        var userMap : MutableMap<String,Any> = mutableMapOf()
        userMap["users"] = list
        Log.i("TAG","유저 업뎃 ${userMap.get("users")}")

        FirebaseFirestore.getInstance().collection("Chat").document(tredeNo)
            .update(userMap)
            .addOnSuccessListener{
                AlertDialog.Builder(this).setMessage("채팅에 참여하겠습니까?")
                    .setPositiveButton("참여하기"
                    ) { dialog, which ->
                        saveFireData()
                        moveToChat() }
                    .setNegativeButton("취소"
                    ) { dialog, which -> dialog.dismiss()}

            }
    }

    //레트로핏 데이터 로드
    private fun loadData() {

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(TredeService::class.java)
            .loadTredeDetail(tredeNo).enqueue(object : Callback<MutableList<TredeDetail>>{
                override fun onResponse(
                    call: Call<MutableList<TredeDetail>>,
                    response: Response<MutableList<TredeDetail>>
                ) {
                    var items : MutableList<TredeDetail> = response.body()!!

                    //이미지 셋
                    imgList = mutableListOf()


                    if(items.get(0).img1 != null || items.get(0).img1 != "") imgList.add(items.get(0).img1)
                    if(items.get(0).img2 != null || items.get(0).img2 != "") imgList.add(items.get(0).img2)
                    if(items.get(0).img3 != null || items.get(0).img3 != "") imgList.add(items.get(0).img3)

                    if(imgList.size > 0) {
                        //페이저에 뷰 연결
                        binding.imgPager.adapter = TredeDetailImgAdapter(this@TredeDetailActivity,imgList)
                        binding.dotsIndcator.attachTo(binding.imgPager)
                    }

                    binding.tvUserNicname.text = items.get(0).userNic
                    binding.tvUserNo.text = items.get(0).userNo.toString() //글쓴이 번호
                    
                    Log.i("TAG","글쓴이 번호 ${items.get(0).userNo}")

                    //사용자 프로필 사진
                    var address = ""
                    if (items.get(0).profileImg != null) {
                        otherImgUrl = items.get(0).profileImg
                        address = Common.dotHomeImgUrl+items.get(0).profileImg
                    } else {
                        otherImgUrl = ""
                    }

                    Glide.with(this@TredeDetailActivity).load(address)
                        .error(R.drawable.user_full).fallback(R.drawable.user_full).into(binding.ivCircleImgUser)

                    binding.tvTitle.text = items.get(0).title
                    binding.tvCategri.text = items.get(0).tredCtyName
                    binding.tvDate.text = items.get(0).date
                    binding.tvJoinCount.text = " : ${items.get(0).joinCount}명"
                    count = items.get(0).joinCount.toString()
                    binding.tvLikeCnt.text = items.get(0).likeCnt.toString()
                    binding.tvContent.text = items.get(0).content

                    binding.tvHangOutTime.text = " : ${items.get(0).joinDate} ${items.get(0).joinTime}"
                    binding.tvHangOutSpot.text = " : ${items.get(0).joinPlace}"
                    binding.tvPrice.text = items.get(0).price.toString()
                    binding.tvOriPrice.text = items.get(0).oriPrice.toString()

                    //내가 쓴 글이고 첫번째일때만 디비에 저장
                    if((Common.getUserNo(this@TredeDetailActivity) == items.get(0).userNo.toString()) && isFirst == true) saveFireData()

                }

                override fun onFailure(call: Call<MutableList<TredeDetail>>, t: Throwable) {
                    Common.makeToast(this@TredeDetailActivity,"서버에 문제가 있습니다")
                }
            })

    }

    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) onBackPressed()
        else if(item.itemId == R.id.option_report) { //유저 신고하기
            val intent : Intent = Intent(this,ReportUserActivity::class.java)
                .putExtra("userNic","유저닉네임").putExtra("userNo","5")
            
            startActivity(intent)
        } else if (item.itemId == R.id.option_share) { //페이지 공유하기

        }


        return super.onOptionsItemSelected(item)
    }
}