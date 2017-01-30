package com.example.akmere.agenda;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.akmere.agenda.model.Aluno;

/**
 * Created by akmere on 12/24/16.
 */

public class FormHelper {
    private final String[] sexo;
    private final String[] situacao;
    private final EditText site;
    private final EditText phone;
    private final EditText address;
    private RadioGroup campoSexo;
    private RadioGroup campoSituacao;
    private Aluno aluno;
    
    private final EditText nome;
    private final DatePicker nascimento;

    public FormHelper(FormularioActivity activity){
        nome = (EditText) activity.findViewById(R.id.formulario_nome);
        site = (EditText) activity.findViewById(R.id.formulario_site);
        phone = (EditText) activity.findViewById(R.id.formulario_phone);
        address = (EditText) activity.findViewById(R.id.formulario_address);
        sexo = radioSexo(activity);
        situacao = radioSituacao(activity);
        nascimento = (DatePicker) activity.findViewById(R.id.formulario_nascimento);
        aluno = new Aluno();
    }


    private String[] radioSexo(FormularioActivity activity) {
        campoSexo = (RadioGroup) activity.findViewById(R.id.formulario_sexo);
        final String[] sexo = {""};
        campoSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case (R.id.sexo_masc):
                         sexo[0] = "Masculino";
                        break;
                    case (R.id.sexo_fem):
                         sexo[0] = "Feminino";
                        break;
                }
            }

        });
        return sexo;
    }

    private String[] radioSituacao(FormularioActivity activity) {
        campoSituacao = (RadioGroup) activity.findViewById(R.id.formulario_situacao);
        final String[] situacao = {""};
        campoSituacao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case (R.id.situacao_ativo):
                        situacao[0] = "Ativo";
                        break;
                    case (R.id.situacao_inativo):
                        situacao[0] = "Inativo";
                        break;
                }
            }

        });
        return situacao;
    }





    public Aluno pegaAluno() {
        aluno.setNome(nome.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setPhone(phone.getText().toString());
        aluno.setAddress(address.getText().toString());
        aluno.setSexo(sexo[0].toString());
        aluno.setSituacao(situacao[0].toString());
        aluno.setNascimento(nascimento.getDayOfMonth() + "/" + nascimento.getMonth() + "/" + nascimento.getYear());
        return aluno;
    }

    public void preencheForm(Aluno aluno) {
        nome.setText(aluno.getNome());
        site.setText(aluno.getSite());
        phone.setText(aluno.getPhone());
        address.setText(aluno.getAddress());
        this.aluno = aluno;
    }
}
