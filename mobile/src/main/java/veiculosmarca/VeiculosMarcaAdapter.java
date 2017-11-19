package veiculosmarca;

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
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaContract;

public class VeiculosMarcaAdapter extends RecyclerView.Adapter<VeiculosMarcaAdapter.ViewHolder> {

    private List<VeiculoMarca> veiculoMarcas;
    private VeiculosMarcaContract.OnItemClickListener mOnItemClickListener;
    private Context context;
    String searchString = "";
    List<VeiculoMarca> veiculosMarcaPesquisa = new ArrayList<>();

    public VeiculosMarcaAdapter(VeiculosMarcaContract.OnItemClickListener onItemClickListener, Context context, List<VeiculoMarca> veiculosMarcaPesquisa) {
        veiculoMarcas = new ArrayList<>();
        this.mOnItemClickListener = onItemClickListener;
        this.context = context;
        this.veiculoMarcas = veiculosMarcaPesquisa;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_veiculo_marca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.veiculoMarca = veiculoMarcas.get(position);
        holder.name.setText(veiculoMarcas.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.clickItem(holder.veiculoMarca);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.clickLongItem(holder.veiculoMarca);
                return false;
            }
        });

        VeiculoMarca veiculoMarca = veiculoMarcas.get(position);
        String name = veiculoMarca.getName().toLowerCase(Locale.getDefault());
        if (name.contains(searchString)) {

            int startPos = name.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.name.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.name.setText(spanString);
        }
    }

    @Override
    public int getItemCount() {
        return veiculoMarcas.size();
    }

    public void setValues(List<VeiculoMarca> values) {
        veiculoMarcas = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public VeiculoMarca veiculoMarca;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setFilter(List<VeiculoMarca> veiculosMarca) {
        veiculosMarca = new ArrayList<>();
        veiculosMarca.addAll(veiculosMarca);
        notifyDataSetChanged();
    }
}
