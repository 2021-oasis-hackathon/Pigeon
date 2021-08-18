package com.example.barcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class InsertRecycleAct extends AppCompatActivity {
    private DatabaseReference mDatabaseRef,reward_DatabaseRef; //실시간 데이터베이스
    private EditText name, material, recycle; // 분리수거 이름, 재질, 방식 입력 필드
    private Button mBtnregihowbunri;    // 분리수거 등록 버튼
    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private FirebaseAuth mAuth;
    private ImageView img1;
    public static int flag;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        PermissionListener permissionlistener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {
                Toast.makeText(InsertRecycleAct.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(InsertRecycleAct.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission] ")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        img1 = findViewById(R.id.img1);
        Button addphoto = findViewById(R.id.addphoto);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id"); //MainActivity로부터 전달받음
        final int[] reward = {intent.getIntExtra("reward", 0)};
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("trash");
        reward_DatabaseRef = FirebaseDatabase.getInstance().getReference("User");

        name = findViewById(R.id.et_pdId); //제품명
        material = findViewById(R.id.et_pdmat); //재질
        recycle = findViewById(R.id.et_howbunri); //방식
        mBtnregihowbunri = findViewById(R.id.btn_regibunri);

        mBtnregihowbunri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reward[0] +=100;
                reward_DatabaseRef.child(id).setValue(reward[0]);


                //등록하기 버튼 클릭 후 액션
                //text박스에 적힌 값을 문자열로 변환 후 변수에 할당
                String Name = name.getText().toString();
                String Material = material.getText().toString();
                String Recycle = recycle.getText().toString();

                addrecycle insertedthing = new addrecycle();
                if(takebarcode.scanedBarcode!=null) {
                    addrecycle.setBarcodenum(takebarcode.scanedBarcode);
                }
                else {
                    addrecycle.setBarcodenum(typebarcode.typebarcodenum);
                }
                addrecycle.setName(Name);
                addrecycle.setMaterial(Material);
                addrecycle.setRecycle(Recycle);
                // setValue : database에 insert (삽입)
                mDatabaseRef.child(insertedthing.getBarcodenum()).setValue(insertedthing);
                Intent intent = new Intent(getApplicationContext(), InsertCompleteAct.class);
                intent.putExtra("id",id);
                intent.putExtra("reward",reward[0]);
                Toast.makeText(getApplicationContext(), "등록되었습니다!", Toast.LENGTH_SHORT).show();
                // 아래부터 파이어베이스 스토리지에 저장하는 코드
                final String cu = id;
                String filename = cu + "_" + System.currentTimeMillis();
                StorageReference storageRef = storage.getReference();
                StorageReference riversRef = storageRef.child("profile_img/"+filename);
                UploadTask uploadTask;
                Uri file = null;
                if(flag==0){
                    //사진촬영
                    file = Uri.fromFile(new File(mCurrentPhotoPath));
                }else if(flag==1){
                    //앨범선택
                    file = photoURI;
                }
                uploadTask = riversRef.putFile(file);
                final ProgressDialog progressDialog = new ProgressDialog(InsertRecycleAct.this);
                progressDialog.setMessage("업로드중...");
                progressDialog.show();
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.v("알림", "사진 업로드 실패");
                        exception.printStackTrace();
                        Toast.makeText(getApplicationContext(),"업로드 실패",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"업로드 성공",Toast.LENGTH_SHORT).show();
                    }
                });
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish(); // 해당 클래스 끝
            }
        });
    }

    // 사진 첨부 누르면 뜨는 다이얼로그
    private void makeDialog() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(InsertRecycleAct.this);
        alt_bld.setTitle("사진 업로드").setCancelable(
                false).setPositiveButton("사진촬영",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 사진 촬영 클릭
                        Log.v("알림", "다이얼로그 > 사진촬영 선택");
                        flag = 0;
                        TakePhoto();
                    }
                }).setNeutralButton("앨범선택",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Log.v("알림", "다이얼로그 > 앨범선택 선택");
                        flag = 1;
                        ChooseAlbum();
                    }
                }).setNegativeButton("취소   ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 취소 선택");
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }


    private void TakePhoto() {
        //사진 찍기
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "에러", Toast.LENGTH_SHORT).show();
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }

        } else {
            Log.v("알림", "저장공간에 접근 불가능");
            return;
        }
    }

    private File createImageFile() throws IOException{
    //사진파일 생성
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");

        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.v("알림", "storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir, imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public void galleryAddPic() {
        //갤러리에 추가
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void ChooseAlbum() {
        //앨범에서 선택
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FROM_ALBUM: {
                if (data.getData() != null) {
                    try {
                        photoURI = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        img1.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case FROM_CAMERA: {
                //카메라 촬영
                try {
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                    img1.setImageURI(imgUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
