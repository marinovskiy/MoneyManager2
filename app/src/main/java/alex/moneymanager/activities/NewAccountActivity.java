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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Currency;
import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.presenters.NewAccountPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewAccountView;
import butterknife.BindView;

public class NewAccountActivity extends BaseActivity implements NewAccountView {

    public static final int ERROR_CASE_CURRENCIES = 0;
    public static final int ERROR_CASE_NEW_ACCOUNT = 1;

    @BindView(R.id.toolbar_new_account)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.spinner_currency)
    Spinner spinnerCurrency;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    NewAccountPresenter presenter;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    private List<Currency> currencies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        presenter.loadCurrencies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        View.OnClickListener menuOnClickListener = v -> {
            switch (v.getId()) {
                case R.id.action_add:
                    addNewAccount();
                    break;
            }
        };

        menu.findItem(R.id.action_add)
                .getActionView()
                .setOnClickListener(menuOnClickListener);

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
    public void setCurrencies(List<Currency> currencies) {
        if (currencies != null) {
            this.currencies = currencies;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Stream.of(currencies)
                            .map(Currency::getName)
                            .toList()
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCurrency.setAdapter(adapter);
            spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
    }

    @Override
    public void accountAddedSuccess() {
        setResult(RESULT_OK);
        finish();
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
                            case ERROR_CASE_CURRENCIES:
                                presenter.loadCurrencies();
                                break;
                            case ERROR_CASE_NEW_ACCOUNT:
                                addNewAccount();
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
            validationErrorMessage = "Вкажіть назву аккаунта";
            return false;
        }

        return true;
    }

    private void addNewAccount() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.addNewUserAccount(
                        new NetworkAccount(
                                getName(),
                                getDescription(),
                                getCurrency()
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

    private int getCurrency() {
        return currencies.get(spinnerCurrency.getSelectedItemPosition()).getId();
    }
}