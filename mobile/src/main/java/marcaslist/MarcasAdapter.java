package marcaslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.com.consultafipe.R;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.marcas.MarcasContract;

public class MarcasAdapter extends RecyclerView.Adapter<MarcasAdapter.ViewHolder> {

    private List<Marca> marcasList;
    private MarcasContract.OnItemClickListener mOnItemClickListener;

    public MarcasAdapter(MarcasContract.OnItemClickListener onItemClickListener) {
        marcasList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
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
            nomeMarca = (TextView) view.findViewById(R.id.nameTextView);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
