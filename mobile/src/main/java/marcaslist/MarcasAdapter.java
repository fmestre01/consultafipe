package marcaslist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import udacity.com.consultafipe.R;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.marcas.MarcasContract;

public class MarcasAdapter extends RecyclerView.Adapter<MarcasAdapter.ViewHolder> {

    private List<Marca> marcasList;
    private final MarcasContract.OnItemClickListener mOnItemClickListener;
    private final Context context;
    List<Marca> marcasPesquisa = new ArrayList<>();
    private String tipoVeiculo;
    private String anoReferencia;
    private boolean canStart = true;

    public MarcasAdapter(MarcasContract.OnItemClickListener onItemClickListener, Context context, List<Marca> marcasPesquisa) {
        marcasList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
        this.context = context;
        this.marcasList = marcasPesquisa;
    }

    public MarcasAdapter(MarcasContract.OnItemClickListener onItemClickListener, Context context, List<Marca> marcasPesquisa, String tipoVeiculo, String anoReferencia) {
        marcasList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
        this.context = context;
        this.marcasList = marcasPesquisa;
        this.tipoVeiculo = tipoVeiculo;
        this.anoReferencia = anoReferencia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.marca = marcasList.get(position);
        holder.nomeMarca.setText(marcasList.get(position).getName());
        holder.tipoVeiculo.setText(tipoVeiculo);
        holder.anoReferencia.setText(anoReferencia);

        /*try {
            Glide.with(context)
                    .load("file:///android_asset/ic_launcher.png")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .crossFade()
                    .into(holder.imagemMarca);
        } catch (Exception e) {
        }*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canStart) {
                    canStart = false; // do canStart false
                    // Whatever you want to do and not have run twice due to double tap
                    mOnItemClickListener.clickItem(holder.marca);
                }

            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.clickLongItem(holder.marca);
                return false;
            }
        });

        Marca marca = marcasList.get(position);
        String name = marca.getName().toLowerCase(Locale.getDefault());
        String searchString = "";
        if (name.contains(searchString)) {

            int startPos = name.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.nomeMarca.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nomeMarca.setText(spanString);
        }
    }

    @Override
    public int getItemCount() {
        return marcasList == null ? 0 : marcasList.size();
    }

    public void setValues(List<Marca> values) {
        marcasList = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView nomeMarca;
        Marca marca;
        final TextView tipoVeiculo;
        final TextView anoReferencia;

        ViewHolder(View view) {
            super(view);
            mView = view;
            nomeMarca = view.findViewById(R.id.nomeMarca);
            tipoVeiculo = view.findViewById(R.id.tipoVeiculo);
            anoReferencia = view.findViewById(R.id.tabelaReferencia);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setFilter() {
        notifyDataSetChanged();
    }

    public void setCanStart(boolean can){
        canStart = can;
    }
}
