package com.example.akmere.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.akmere.agenda.dao.AlunoDAO;
import com.example.akmere.agenda.model.Aluno;

public class FormularioActivity extends AppCompatActivity {


    private FormHelper formHelper;
    private AlunoDAO alunoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        formHelper = new FormHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null){
            formHelper.preencheForm(aluno);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);

        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intentCamera);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.formulario_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = formHelper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if ( aluno.getId() != null ){
                    dao.alteraAluno(aluno);
                }else{
                    dao.insereAluno(aluno);
                }

                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " Salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }



        return super.onOptionsItemSelected(item);
    }
}
