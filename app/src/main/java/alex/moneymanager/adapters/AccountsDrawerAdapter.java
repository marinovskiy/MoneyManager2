package alex.moneymanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.network.Account;
import alex.moneymanager.entities.network.Operation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsDrawerAdapter extends RecyclerView.Adapter<AccountsDrawerAdapter.AccountViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<Account> accounts;

    public AccountsDrawerAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void updateAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        holder.bindAccount(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        return accounts != null ? accounts.size() : 0;
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_account_name)
        TextView tvAccountName;

        AccountViewHolder(View itemView) {
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

        void bindAccount(Account account) {
            tvAccountName.setText(
                    String.format(
                            "%s %s %s",
                            account.getName(),
                            account.getBalance(),
                            account.getCurrency().getSymbol()
                    )
            );
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}