package alex.moneymanager.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.db.Operation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.OperationViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<Operation> operations;

    public OperationsAdapter(List<Operation> operations) {
        this.operations = operations;
    }

    public void updateOperations(List<Operation> operations) {
        this.operations = operations;
        notifyDataSetChanged();
    }

    @Override
    public OperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OperationViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_operation1, parent, false));
    }

    @Override
    public void onBindViewHolder(OperationViewHolder holder, int position) {
        holder.bindOperation(operations.get(position));
    }

    @Override
    public int getItemCount() {
        return operations != null ? operations.size() : 0;
    }

    class OperationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_operation_number)
        TextView tvOperationNumber;
        @BindView(R.id.tv_operation_description)
        TextView tvOperationDescription;
        @BindView(R.id.tv_operation_category)
        TextView tvOperationCategory;
        @BindView(R.id.tv_operation_sum)
        TextView tvOperationSum;
        @BindView(R.id.tv_operation_date)
        TextView tvOperationDate;

        OperationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }

        void bindOperation(Operation operation) {
            String number;

            if (getAdapterPosition() + 1 < 10) {
                number = String.format(" %s", getAdapterPosition() + 1);
            } else {
                number = String.valueOf(getAdapterPosition() + 1);
            }
            tvOperationNumber.setText(number);
            tvOperationDescription.setText(operation.getDescription());
            tvOperationCategory.setText(operation.getCategory().getName());

            if (operation.getSum() < 0) {
                tvOperationSum.setTextColor(
                        ContextCompat.getColor(itemView.getContext(), R.color.colorNegative)
                );
            } else {
                tvOperationSum.setTextColor(
                        ContextCompat.getColor(itemView.getContext(), R.color.colorPositive)
                );
            }
            tvOperationSum.setText(String.valueOf(operation.getSum()));
//            tvOperationDate.setText();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}