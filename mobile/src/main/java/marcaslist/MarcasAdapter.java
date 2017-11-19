package marcaslist;

import android.content.Context;
import android.graphics.Color;
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
    private MarcasContract.OnItemClickListener mOnItemClickListener;
    private Context context;
    String searchString = "";
    List<Marca> marcasPesquisa = new ArrayList<>();

    public MarcasAdapter(MarcasContract.OnItemClickListener onItemClickListener, Context context, List<Marca> marcasPesquisa) {
        marcasList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
        this.context = context;
        this.marcasList = marcasPesquisa;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.marca = marcasList.get(position);
        holder.nomeMarca.setText(marcasList.get(position).getName());

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
                mOnItemClickListener.clickItem(holder.marca);
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
        return marcasList.size();
    }

    public void setValues(List<Marca> values) {
        marcasList = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nomeMarca;
        public Marca marca;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nomeMarca = (TextView) view.findViewById(R.id.nomeMarca);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setFilter(List<Marca> marcas) {
        marcas = new ArrayList<>();
        marcas.addAll(marcas);
        notifyDataSetChanged();
    }
}
