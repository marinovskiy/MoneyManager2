package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.adapters.MembersAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.presenters.OrganizationMembersPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.OrganizationMembersView;
import butterknife.BindView;
import butterknife.OnClick;

public class OrganizationMembersActivity extends BaseActivity implements OrganizationMembersView {

    public static final String INTENT_KEY_ORGANIZATION_ID = "intent_key_organization_id";

    public static final int REQUEST_CODE_ADD_MEMBERS = 4590;

    @BindView(R.id.toolbar_new_organization)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_members)
    RecyclerView rvMembers;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    OrganizationMembersPresenter presenter;

    private ProgressDialog progressDialog;

    private MembersAdapter rvMembersAdapter;

    private int organizationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_members);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        organizationId = getIntent().getIntExtra(INTENT_KEY_ORGANIZATION_ID, 0);

        loadMembers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_MEMBERS:
                    loadMembers();
                    break;
            }
        }
    }

    @OnClick(R.id.fab_new_member)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddMembersActivity.class);
        intent.putExtra(AddMembersActivity.INTENT_KEY_ORGANIZATION_ID, organizationId);
        startActivityForResult(intent, REQUEST_CODE_ADD_MEMBERS);
    }

    @Override
    public void setMembers(Organization organization) {
        if (organization != null && organization.getMembers() != null) {
            tvTitle.setText("Учасники групи " + organization.getName());
            if (rvMembersAdapter == null) {
                rvMembers.setLayoutManager(new LinearLayoutManager(this));
                rvMembersAdapter = new MembersAdapter(organization.getMembers());
                rvMembersAdapter.setOnItemClickListener((view, position) -> {
                    //TODO popup
                });
                rvMembers.setAdapter(rvMembersAdapter);
            } else {
                rvMembersAdapter.updateMembers(organization.getMembers());
            }
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.progress_bar_registration));
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorDialog() {
        String errorMessage;
        if (systemUtils.isConnected()) {
            errorMessage = getString(R.string.msg_error_while_making_request);
        } else {
            errorMessage = getString(R.string.msg_error_no_internet);
        }

        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                errorMessage,
                getString(R.string.dialog_btn_ok),
                getString(R.string.dialog_btn_retry),
                new ErrorDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        finish();
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        loadMembers();
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void loadMembers() {
        if (organizationId != 0) {
            if (systemUtils.isConnected()) {
                presenter.loadMembersApi(organizationId);
            } else {
                presenter.loadMembersDb(organizationId);
            }
        }
    }
}