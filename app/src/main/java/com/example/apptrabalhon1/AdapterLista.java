package com.example.apptrabalhon1;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterLista extends BaseAdapter {

    private List<Filme> filmeList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterLista(Context context, List<Filme> listaFilmes){
        this.filmeList = listaFilmes;
        this.context = context;
        this.inflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return filmeList.size();
    }

    @Override
    public Object getItem(int i) {
        return filmeList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return filmeList.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ItemSuporte item;

        if( convertView == null){
            convertView = inflater.inflate(R.layout.layout_list, null);

            item = new ItemSuporte();
            item.tvNome = convertView.findViewById(R.id.tvListaNome);
            item.tvAno = convertView.findViewById(R.id.tvListaAno);
            item.tvCategoria = convertView.findViewById(R.id.tvListaCategoria);
            item.layout = convertView.findViewById(R.id.llFundoLista);
            convertView.setTag( item );
        }else {
            item = (ItemSuporte) convertView.getTag();
        }

        Filme filme = filmeList.get(i);
        item.tvNome.setText(  filme.nome );
        item.tvCategoria.setText(String.valueOf(filme.getCategoria()));
        item.tvAno.setText(  String.valueOf( filme.getAno() ) );

        if( i % 2 == 0 ){
            item.layout.setBackgroundColor(Color.rgb(230, 230, 230));
        }else {
            item.layout.setBackgroundColor( Color.WHITE );
        }
        return convertView;
    }

    private class ItemSuporte{
        TextView tvNome, tvAno, tvCategoria;
        LinearLayout layout;
    }


}
