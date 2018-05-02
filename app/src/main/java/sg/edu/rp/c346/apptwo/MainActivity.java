package sg.edu.rp.c346.apptwo;

        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import static sg.edu.rp.c346.apptwo.DatabaseHelper.COL_1;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editMedicine;
    TextView textvName, textvMedicine;
    Button btnAddData;
    Button btnviewAll;
    Button btnupd;
    Button btnDel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_Name);
        editMedicine = (EditText) findViewById(R.id.editText_medicine);
        textvName = (TextView) findViewById(R.id.tvName);
        textvMedicine = (TextView) findViewById(R.id.tvMedicine);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnupd=(Button) findViewById(R.id.button_update);
        btnDel=(Button) findViewById(R.id.button_delete);
       // btnSetNot=(Button)findViewById(R.id.button_SetNoti);



        AddData();
        viewAll();
        UpdateData();
        DeleteData();



    }


    public void DeleteData(){
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editName.getText().toString(), editMedicine.getText().toString());
                if(deletedRows >0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void UpdateData(){
        btnupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editName.getText().toString(), editMedicine.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = myDb.check(editName.getText().toString());
                if( check == true){
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                }else{
                    boolean isInserted = myDb.insertData(editName.getText().toString(), editMedicine.getText().toString());
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }


              //  if (isInserted == true)
                //    if(editName.getText().toString() != myDb.COL_1.toString())
                  //      Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    //else



            }
        });

    }


    public void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res = myDb.getAllData();
                if(res.getCount()==0) {
                    showMessage("Error", "No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name: "+ res.getString(0)+ "\n");
                    buffer.append("Medicine: "+ res.getString(1)+"\n\n");
                }

               //show data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

