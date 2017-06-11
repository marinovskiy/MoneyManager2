package alex.moneymanager.adapters;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.db.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<User> members;
    private boolean select;

    private int selectedItemPosition = -1;

    public MembersAdapter(List<User> members) {
        this.members = members;
    }

    public MembersAdapter(List<User> members, boolean select) {
        this.members = members;
        this.select = select;
    }

    public void updateMembers(List<User> members) {
        this.members = members;
        selectedItemPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_member, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.bindMember(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members != null ? members.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_member_number)
        TextView tvMemberNumber;
        @BindView(R.id.tv_member_name)
        TextView tvMemberName;
        @BindView(R.id.tv_member_email)
        TextView tvMemberEmail;

        MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());

                selectedItemPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }

        void bindMember(User member) {
            String number;
            if (getAdapterPosition() + 1 < 10) {
                number = String.format(" %s", getAdapterPosition() + 1);
            } else {
                number = String.valueOf(getAdapterPosition() + 1);
            }
            tvMemberNumber.setText(number);

            tvMemberName.setText(member.getFirstName() + " " + member.getLastName());
            tvMemberEmail.setText(member.getEmail());

            if (select && getAdapterPosition() == selectedItemPosition) {
                itemView.setBackgroundColor(Color.BLUE);
            } else {
                itemView.setBackgroundColor(
                        ContextCompat.getColor(
                                itemView.getContext(), R.color.bgMain
                        )
                );
            }
        }
    }
}