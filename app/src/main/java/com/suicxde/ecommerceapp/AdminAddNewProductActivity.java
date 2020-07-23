package com.suicxde.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName,Description,Price,ProductName,saveCurrentDate, saveCurrentTime;
    private Button Addnewproductbutton;
    private EditText InputProductName,InputProductDescription,InputProdcutPrice;
    private ImageView InputProductImage;
    private static final  int GalleryPick =1;
    private Uri ImageUri;
    private String productRandomKey,downloadImageURL;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Addnewproductbutton = (Button) findViewById(R.id.add_new_product_btn);
        InputProductImage = (ImageView) findViewById(R.id.select_product_Image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_discription);
        InputProdcutPrice = (EditText) findViewById(R.id.product_price);
        loadingbar = new ProgressDialog(this);
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        Addnewproductbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidateProductData();
            }
        });


    }

    private void ValidateProductData()
    {
        Description = InputProductDescription.getText().toString();
        Price = InputProdcutPrice.getText().toString();
        ProductName = InputProductName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Bro need image bro", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            InputProductDescription.setError("need");
        }
        else if(TextUtils.isEmpty(Price))
        {
            InputProdcutPrice.setError("need");
        }
        else if(TextUtils.isEmpty(ProductName))
        {
            InputProductName.setError("need");
        }
        else
        {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation()
    {

        loadingbar.setTitle("adding new product");
        loadingbar.setMessage("Please wait bro why ");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //unique key per product to differentiate it from others - made by time+date
        Random rand = new Random();
        int n = rand.nextInt(100000);
        productRandomKey = saveCurrentDate+ saveCurrentTime+n;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey +".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String msg = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded bro", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {

                            throw  task.getException();

                        }
                        downloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();


                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {

                            downloadImageURL = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "got product image url successfully", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInfoToDatabase() {

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageURL);
        productMap.put("category",categoryName);
        productMap.put("price",Price);
        productMap.put("productname",ProductName);
        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                    startActivity(intent);
                    loadingbar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "Prodct s added succsesfuly", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingbar.dismiss();
                    String msg = task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "error:" +msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent,"Pick a picture"),GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==GalleryPick && data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }

    }
}