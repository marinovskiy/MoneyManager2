package alex.moneymanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.activities.NewAccountActivity;
import alex.moneymanager.adapters.AccountsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.dialogs.MyDialogFragment;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.presenters.OrganizationsPresenter;
import alex.moneymanager.utils.DateTimeUtils;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.OrganizationsView;
import butterknife.BindView;
import butterknife.OnClick;

public class OrganizationsFragment extends BaseFragment implements OrganizationsView {

    public static final String TAG = "OrganizationsFragment";

    public static final int REQUEST_CODE_EDIT_ACCOUNT = 3321;

    public static final int ERROR_CASE_ORGANIZATION = 0;
    public static final int ERROR_CASE_EDIT_ACCOUNT = 1;
    public static final int ERROR_CASE_DELETE_ACCOUNT = 2;

    @BindView(R.id.ll_empty_view_organizations)
    LinearLayout llEmptyViewOrganization;
    @BindView(R.id.tv_organization_name)
    TextView tvOrganizationName;
    @BindView(R.id.tv_organization_type)
    TextView tvOrganizationType;
    @BindView(R.id.tv_organization_date)
    TextView tvOrganizationDate;
    @BindView(R.id.tv_organization_status)
    TextView tvOrganizationStatus;
    @BindView(R.id.tv_organization_description)
    TextView tvOrganizationDescription;
    @BindView(R.id.rv_accounts)
    RecyclerView rvAccounts;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    OrganizationsPresenter presenter;

    private ProgressDialog progressDialog;

    private LinearLayoutManager linearLayoutManager;
    private AccountsAdapter rvAccountsAdapter;

    private Organization organization;

    private Integer lastOrganizationId;
    private Integer lastAccountId;

    public OrganizationsFragment() {
    }

    public static OrganizationsFragment newInstance() {
        return new OrganizationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_organizations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
        presenter.attachView(this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvAccounts.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switchOrganization(lastOrganizationId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT_ACCOUNT:
                    if (organization != null) {
                        presenter.loadOrganization(organization.getId());
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick({R.id.btn_add_organization, R.id.btn_organization_members})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_organization:
                break;
            case R.id.btn_organization_members:
                break;
        }
    }

    @Override
    public void setOrganization(Organization organization) {
        if (organization != null) {
            this.organization = organization;

            tvOrganizationName.setText(organization.getName());
            tvOrganizationType.setText(organization.getType());
            tvOrganizationDate.setText(DateTimeUtils.covertDateStr(organization.getCreatedAt()));
            tvOrganizationStatus.setText(organization.isEnabled() ? "Розблоковано" : "Заблоковано");
            tvOrganizationDescription.setText(organization.getDescription());

            if (this.organization.getAccounts() != null) {

                if (rvAccountsAdapter == null) {
                    rvAccountsAdapter = new AccountsAdapter(this.organization.getAccounts());
                    rvAccountsAdapter.setOnItemClickListener((view, position) -> {
                        if (this.organization.getCreator().getId().equals(preferenceUtil.getUser().getId())
                                && this.organization.getAccounts() != null) {
                            showAccountPopupMenu(
                                    view,
                                    this.organization.getAccounts().get(position).getId()
                            );
                        }
                    });

                    rvAccounts.setAdapter(rvAccountsAdapter);
                } else {
                    rvAccountsAdapter.updateAccounts(this.organization.getAccounts());
                }

            }
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorDialog(int errorCase) {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                getString(R.string.msg_error_while_making_request),
                getString(R.string.dialog_btn_ok),
                getString(R.string.dialog_btn_retry),
                new ErrorDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        switch (errorCase) {
                            case ERROR_CASE_ORGANIZATION:
//                                prese
                                break;
                            case ERROR_CASE_EDIT_ACCOUNT:
                                editAccount();
                                break;
                            case ERROR_CASE_DELETE_ACCOUNT:
                                deleteAccount();
                                break;
                        }
                    }
                }
        );

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void showAccountPopupMenu(View view, int accountId) {
        lastAccountId = accountId;

        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.popup_menu_edit_delete);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    editAccount();
                    return true;
                case R.id.action_delete:
                    deleteAccount();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    private void editAccount() {
        Intent intent = new Intent(getActivity(), NewAccountActivity.class);
        intent.putExtra(NewAccountActivity.INTENT_KEY_ORGANIZATION_ID, lastOrganizationId);
        intent.putExtra(NewAccountActivity.INTENT_KEY_ACCOUNT_ID, lastAccountId);
        startActivityForResult(intent, REQUEST_CODE_EDIT_ACCOUNT);
    }

    private void deleteAccount() {
        MyDialogFragment fragment = MyDialogFragment.newInstance(
                "Ви впевнені, що хочете видалити дану операцію?",
                "Так",
                "Ні",
                new MyDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        if (systemUtils.isConnected()) {
                            if (lastAccountId != null) {
//                                presenter.deleteAccount(lastOrganizationId, lastAccountId);
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNegativeButtonClick() {
                    }
                });
        fragment.show(getChildFragmentManager(), MyDialogFragment.TAG);
    }

    public void switchOrganization(int organizationId) {
        lastOrganizationId = organizationId;

        if (presenter != null) {
            presenter.loadOrganization(organizationId);
        }
    }
}