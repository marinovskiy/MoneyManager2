package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.adapters.MembersAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.User;
import alex.moneymanager.presenters.OrganizationAddMemberPresenter;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.OrganizationAddMemberView;
import butterknife.BindView;
import butterknife.OnClick;

public class AddMembersActivity extends BaseActivity implements OrganizationAddMemberView {

    public static final String INTENT_KEY_ORGANIZATION_ID = "intent_key_organization_id";

    public static final int ERROR_CASE_SEARCH = 0;
    public static final int ERROR_CASE_ADD_MEMBER = 1;

    @BindView(R.id.toolbar_add_members)
    Toolbar toolbar;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.rv_members)
    RecyclerView rvMembers;
    @BindView(R.id.btn_add_member)
    Button btnAddMember;

    @Inject
    SystemUtils systemUtils;
    @Inject
    OrganizationAddMemberPresenter presenter;

    private ProgressDialog progressDialog;

    private MembersAdapter rvMembersAdapter;

    private int organizationId;
    private int selectedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        organizationId = getIntent().getIntExtra(INTENT_KEY_ORGANIZATION_ID, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        finish();
//        super.onBackPressed();
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

    @OnClick({R.id.btn_search, R.id.btn_add_member})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                search();
                break;
            case R.id.btn_add_member:
                addMember();
                break;
        }
    }

    @Override
    public void setSearchResults(List<User> users) {
        if (users != null) {
            if (rvMembersAdapter == null) {
                rvMembers.setLayoutManager(new LinearLayoutManager(this));
                rvMembersAdapter = new MembersAdapter(users, true);
                rvMembersAdapter.setOnItemClickListener((view, position) -> {
                    selectedUserId = users.get(position).getId();
                });
                rvMembers.setAdapter(rvMembersAdapter);
            } else {
                selectedUserId = 0;
                rvMembersAdapter.updateMembers(users);
            }
        }
    }

    @Override
    public void memberAddedSuccessful() {
        setResult(RESULT_OK);
        Toast.makeText(this, "Користувач доданий до групи", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void alreadyMember() {
        Toast.makeText(this, "Користувач уже є учасником групи", Toast.LENGTH_SHORT).show();
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
    public void showErrorDialog(int errorCase) {
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
                        switch (errorCase) {
                            case ERROR_CASE_SEARCH:
                                search();
                                break;
                            case ERROR_CASE_ADD_MEMBER:
                                addMember();
                                break;
                        }
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void search() {
        if (systemUtils.isConnected()) {
            if (getSearchQuery().isEmpty()) {
                Toast.makeText(this, "Введіть електронну пошту для пошуку", Toast.LENGTH_SHORT).show();
            } else {
                presenter.searchUsers(getSearchQuery());
            }
        } else {
            Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void addMember() {
        if (systemUtils.isConnected()) {
            if (selectedUserId == 0) {
                Toast.makeText(this, "Оберіть користувача", Toast.LENGTH_SHORT).show();
            } else {
                presenter.addMember(organizationId, selectedUserId);
            }
        } else {
            Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private String getSearchQuery() {
        return etEmail.getText().toString();
    }
}