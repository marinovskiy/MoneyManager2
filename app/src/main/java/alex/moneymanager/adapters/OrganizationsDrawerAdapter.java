package alex.moneymanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.db.Organization;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationsDrawerAdapter extends RecyclerView.Adapter<OrganizationsDrawerAdapter.OrganizationViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<Organization> organizations;

    public OrganizationsDrawerAdapter(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void updateOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
        notifyDataSetChanged();
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrganizationViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrganizationViewHolder holder, int position) {
        holder.bindOrganization(organizations.get(position));
    }

    @Override
    public int getItemCount() {
        return organizations != null ? organizations.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_info)
        TextView tvInfo;

        OrganizationViewHolder(View itemView) {
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

        void bindOrganization(Organization organization) {
            tvInfo.setText(organization.getName());
        }
    }
}