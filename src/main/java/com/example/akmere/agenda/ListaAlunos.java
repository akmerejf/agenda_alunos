package com.example.akmere.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.akmere.agenda.dao.AlunoDAO;
import com.example.akmere.agenda.model.Aluno;

import java.util.List;

public class ListaAlunos extends AppCompatActivity {

    private ListView listaAlunos;
    private ListView listaProdutos;
    private String site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clickCurto();

        btnNovoAluno();

        registerForContextMenu(listaAlunos);

    }

    private void btnNovoAluno() {
        Button novoAlunoButton = (Button) findViewById(R.id.novo_aluno_button);
        novoAlunoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent vaiProformulario = new Intent(ListaAlunos.this, FormularioActivity.class);
                startActivity(vaiProformulario);
            }
        });
    }

    private void clickCurto() {
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunos.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });
    }



    private void exibeAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAluno();
        dao.close();
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunos.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunos.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:"+aluno.getPhone()));
                    startActivity(intentLigar);
                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:"+aluno.getPhone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });



        MenuItem itemSms = menu.add("SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+aluno.getPhone()));
        itemSms.setIntent(intentSms);

        MenuItem itemMapa = menu.add("Endereço");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getAddress()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        site = aluno.getSite();
        if (!site.startsWith("https://")) {
            site = "https://".concat(site);
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem remover = menu.add("Remover");

        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
                dao.deleta(aluno);
                dao.close();
                Toast.makeText(ListaAlunos.this, "Aluno " + aluno.getNome() + " Removido!", Toast.LENGTH_SHORT).show();

                exibeAlunos();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        exibeAlunos();
    }
}
