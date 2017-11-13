package veiculosmarca;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.com.consultafipe.R;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaContract;

public class VeiculosMarcaAdapter extends RecyclerView.Adapter<VeiculosMarcaAdapter.ViewHolder> {

    private List<VeiculoMarca> veiculoMarcas;
    private VeiculosMarcaContract.OnItemClickListener mOnItemClickListener;

    public VeiculosMarcaAdapter(VeiculosMarcaContract.OnItemClickListener onItemClickListener) {
        veiculoMarcas = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
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
}
