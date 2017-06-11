package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.entities.enums.OrganizationType;
import alex.moneymanager.entities.network.NetworkOrganization;
import alex.moneymanager.presenters.NewOrganizationPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewOrganizationView;
import butterknife.BindView;

public class NewOrganizationActivity extends BaseActivity implements NewOrganizationView {

    public static final String INTENT_KEY_ORGANIZATION_ID = "intent_key_organization_id";

    public static final int ERROR_CASE_LOAD_ORGANIZATION = 0;
    public static final int ERROR_CASE_NEW_ORGANIZATION = 1;
    public static final int ERROR_CASE_EDIT_ORGANIZATION = 2;

    @BindView(R.id.toolbar_new_organization)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.spinner_type)
    Spinner spinnerType;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    NewOrganizationPresenter presenter;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    private ArrayAdapter<String> spinnerAdapterType;

    private int organizationId;

    private List<String> organizationTypes = Arrays.asList(
            OrganizationType.FAMILY,
            OrganizationType.COMPANY,
            OrganizationType.ORGANIZATION
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_organization);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        organizationId = getIntent().getIntExtra(INTENT_KEY_ORGANIZATION_ID, 0);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        spinnerAdapterType = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                organizationTypes
        );
        spinnerAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(spinnerAdapterType);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (organizationId != 0) {
            presenter.loadOrganizationDb(organizationId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (organizationId == 0) {
            getMenuInflater().inflate(R.menu.menu_add, menu);

            View.OnClickListener menuOnClickListener = v -> {
                switch (v.getId()) {
                    case R.id.action_add:
                        addNewOrganization();
                        break;
                }
            };

            menu.findItem(R.id.action_add)
                    .getActionView()
                    .setOnClickListener(menuOnClickListener);
        } else {
            getMenuInflater().inflate(R.menu.menu_update, menu);

            View.OnClickListener menuOnClickListener = v -> {
                switch (v.getId()) {
                    case R.id.action_update:
                        updateOrganization();
                        break;
                }
            };

            menu.findItem(R.id.action_update)
                    .getActionView()
                    .setOnClickListener(menuOnClickListener);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void organizationAddedSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setOrganization(Organization organization) {
        etName.setText(organization.getName());
        etDescription.setText(organization.getDescription());

        int index = organizationTypes.indexOf(
                Stream.of(organizationTypes)
                        .filter(value -> value.equals(organization.getType()))
                        .findFirst()
                        .get()
        );
        spinnerType.setSelection(index);
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
                            case ERROR_CASE_LOAD_ORGANIZATION:
                                //TODO
                                break;
                            case ERROR_CASE_NEW_ORGANIZATION:
                                //TODO
                                break;
                            case ERROR_CASE_EDIT_ORGANIZATION:
                                //TODO
                                break;
                        }
                    }
                }
        );

        fragment.setCancelable(false);

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private boolean isValid() {
        if (getName().isEmpty()) {
            validationErrorMessage = "Вкажіть назву групи";
            return false;
        }
        if (getDescription().isEmpty()) {
            validationErrorMessage = "Вкажіть опис групи";
            return false;
        }

        return true;
    }

    private void addNewOrganization() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.addNewOrganization(
                        new NetworkOrganization(
                                getName(),
                                getDescription(),
                                getType(),
                                false
                        )
                );
            } else {
                Toast.makeText(this, validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateOrganization() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.editOrganization(
                        organizationId,
                        new NetworkOrganization(
                                getName(),
                                getDescription(),
                                getType(),
                                false
                        )
                );
            } else {
                Toast.makeText(this, validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private String getName() {
        return etName.getText().toString();
    }

    private String getDescription() {
        return etDescription.getText().toString();
    }

    private String getType() {
        return organizationTypes.get(spinnerType.getSelectedItemPosition());
    }
}