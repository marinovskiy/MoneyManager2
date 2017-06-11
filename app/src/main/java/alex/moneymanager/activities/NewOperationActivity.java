package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.DecimalDigitsInputFilter;
import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Category;
import alex.moneymanager.entities.enums.Type;
import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.presenters.NewOperationPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewOperationView;
import butterknife.BindView;

public class NewOperationActivity extends BaseActivity implements NewOperationView {

    public static final int ERROR_CASE_CATEGORIES = 0;
    public static final int ERROR_CASE_NEW_OPERATION = 1;

    public static final String INTENT_KEY_ACCOUNT_ID = "intent_key_account_id";

    @BindView(R.id.toolbar_new_operation)
    Toolbar toolbar;
    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.et_sum)
    EditText etSum;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.rb_type_income)
    RadioButton rbTypeIncome;
    @BindView(R.id.rb_type_expense)
    RadioButton rbTypeExpense;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    NewOperationPresenter presenter;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    private int accountId;

    private ArrayAdapter<String> spinnerAdapterIncome;
    private ArrayAdapter<String> spinnerAdapterExpense;

    private List<Category> categoriesAll = new ArrayList<>();
    private List<Category> categoriesIncome = new ArrayList<>();
    private List<Category> categoriesExpense = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        rbTypeIncome.setChecked(true);
        etSum.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});

        accountId = getIntent().getIntExtra(INTENT_KEY_ACCOUNT_ID, 0);

        presenter.loadCategories();
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
                    addNewOperation();
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
    public void setCategories(List<Category> categories) {
        if (categories != null) {
            categoriesAll = categories;
            categoriesIncome = Stream.of(categoriesAll)
                    .filter(value -> value.getType().equals(Type.INCOME))
                    .toList();
            categoriesExpense = Stream.of(categoriesAll)
                    .filter(value -> value.getType().equals(Type.EXPENSE))
                    .toList();

            spinnerAdapterIncome = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Stream.of(categoriesIncome)
                            .map(Category::getName)
                            .toList()
            );
            spinnerAdapterExpense = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Stream.of(categoriesExpense)
                            .map(Category::getName)
                            .toList()
            );
            spinnerAdapterIncome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAdapterExpense.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCategory.setAdapter(spinnerAdapterIncome);
            spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            rgType.setOnCheckedChangeListener((group, checkedId) -> {
                switch (checkedId) {
                    case R.id.rb_type_income:
                        spinnerCategory.setAdapter(spinnerAdapterIncome);
                        break;
                    case R.id.rb_type_expense:
                        spinnerCategory.setAdapter(spinnerAdapterExpense);
                        break;
                }
            });
        }
    }

    @Override
    public void operationAddedSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
                            case ERROR_CASE_CATEGORIES:
                                presenter.loadCategories();
                                break;
                            case ERROR_CASE_NEW_OPERATION:
                                addNewOperation();
                                break;
                        }
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private boolean isValid() {
        if (getSum().isEmpty()) {
            validationErrorMessage = "Вкажіть суму операції";
            return false;
        }

        return true;
    }

    private void addNewOperation() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.addNewOperation(
                        accountId,
                        new NetworkOperation(
                                getType(),
                                getDescription(),
                                Float.parseFloat(getSum()),
                                getCategory()
                        )
                );
            } else {
                Toast.makeText(this, validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private String getType() {
        return ((RadioButton) findViewById(rgType.getCheckedRadioButtonId()))
                .getText()
                .toString()
                .toLowerCase();
    }

    private String getSum() {
        return etSum.getText().toString();
    }

    private String getDescription() {
        return etDescription.getText().toString();
    }

    private Integer getCategory() {
        if (getType().equals(Type.INCOME)) {
            return categoriesIncome.get(spinnerCategory.getSelectedItemPosition()).getId();
        } else if (getType().equals(Type.EXPENSE)) {
            return categoriesExpense.get(spinnerCategory.getSelectedItemPosition()).getId();
        }
        return null;
    }
}