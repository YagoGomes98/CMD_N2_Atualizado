package com.example.apptrabalhon1;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome;
    private Spinner spAno;
    private Spinner spCategorias;
    private EditText etAnalise;
    private Button btnSalvar;
    private Button btnExcluir;
    private String acao;
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById( R.id.etNome );
        spAno = findViewById( R.id.spAno );
        spCategorias = findViewById(R.id.spCategorias);
        etAnalise = findViewById(R.id.etAnalise);
        btnSalvar = findViewById( R.id.btnSalvar );
        btnExcluir = findViewById(R.id.btnExcluir);

        acao = getIntent().getStringExtra("acao");
        if( acao.equals("editar")){
            carregarFormulario();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

    }

    private void carregarFormulario(){
        int idFilme = getIntent().getIntExtra("idFilme", 0);
        if( idFilme != 0) {
            filme = FilmeDAO.getFilmeById(this, idFilme);

            etNome.setText( filme.nome );
            etAnalise.setText(filme.analise);
            String [] arrayCategorias = getResources().getStringArray(R.array.arrayCategorias);
            for (int i=1; i < arrayCategorias.length; i++){
                if (filme.getCategoria().equals(arrayCategorias[i])){
                    spCategorias.setSelection(i);
                    break;
                }
            }
            String[] arrayAno = getResources().getStringArray(R.array.arrayAno);
            for(int i = 1; i < arrayAno.length ; i++){
                if( Integer.valueOf( arrayAno[i] ) == filme.getAno()){
                    spAno.setSelection( i );
                    break;
                }
            }
        }
    }

    private void salvar(){
        if( spAno.getSelectedItemPosition() == 0 || etNome.getText().toString().isEmpty() || spCategorias.getSelectedItemPosition() == 0 || etAnalise.getText().toString().isEmpty() ) {

            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();

        }else{

            if (acao.equals("novo")) {
                filme = new Filme();
            }

            filme.nome = etNome.getText().toString();
            filme.setAno( Integer.valueOf( spAno.getSelectedItem().toString()  ) );
            filme.setCategoria(spCategorias.getSelectedItem().toString());
            filme.analise = etAnalise.getText().toString();

            if( acao.equals("editar")){
                FilmeDAO.editar (filme, this);
                finish();

            }else {
                FilmeDAO.inserir(filme, this);
                etNome.setText("");
                spAno.setSelection(0);
                spCategorias.setSelection(0, true);
                etAnalise.setText("");
            }
        }
    }

    private void excluir() {
        if (spAno.getSelectedItemPosition() == 0 || etNome.getText().toString().isEmpty() || spCategorias.getSelectedItemPosition() == 0 || etAnalise.getText().toString().isEmpty()) {

            Toast.makeText(this, "N??o ?? poss??vel excluir um item que n??o foi cadastrado.", Toast.LENGTH_SHORT).show();

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(android.R.drawable.ic_input_delete);
            alerta.setTitle(R.string.txtAtencao);
            alerta.setMessage("Confirma a exclus??o do filme " + filme.nome+"?");
            alerta.setNeutralButton("Cancelar", null);
            alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FilmeDAO.excluir( filme.id, FormularioActivity.this);
                    etNome.setText("");
                    spAno.setSelection(0);
                    spCategorias.setSelection(0, true);
                    etAnalise.setText("");
                }
            });
            alerta.show();
        }
  
        }
    }





