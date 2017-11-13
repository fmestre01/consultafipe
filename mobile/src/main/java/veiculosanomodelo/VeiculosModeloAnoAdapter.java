package veiculosanomodelo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.com.consultafipe.R;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.ui.veiculosmodeloano.VeiculosModeloAnoContract;

public class VeiculosModeloAnoAdapter extends RecyclerView.Adapter<VeiculosModeloAnoAdapter.ViewHolder> {

    private List<VeiculoModeloAno> veiculosModeloAno;
    private VeiculosModeloAnoContract.OnItemClickListener mOnItemClickListener;

    public VeiculosModeloAnoAdapter(VeiculosModeloAnoContract.OnItemClickListener onItemClickListener) {
        veiculosModeloAno = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_veiculo_modelo_ano, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.veiculoModeloAno = veiculosModeloAno.get(position);
        holder.veiculo.setText(veiculosModeloAno.get(position).getVeiculo());
        holder.name.setText(veiculosModeloAno.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.clickItem(holder.veiculoModeloAno);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.clickLongItem(holder.veiculoModeloAno);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return veiculosModeloAno.size();
    }

    public void setValues(List<VeiculoModeloAno> values) {
        veiculosModeloAno = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView veiculo;
        public VeiculoModeloAno veiculoModeloAno;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            veiculo = (TextView) view.findViewById(R.id.veiculo);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
