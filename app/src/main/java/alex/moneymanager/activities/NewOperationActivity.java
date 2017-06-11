package alex.moneymanager.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.DecimalDigitsInputFilter;
import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import butterknife.BindView;

public class NewOperationActivity extends BaseActivity {

    @BindView(R.id.toolbar_new_operation)
    Toolbar toolbar;
    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.et_sum)
    EditText etSum;
    @BindView(R.id.et_description)
    EditText etDescription;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private String validationErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        ((MoneyManagerApplication) getApplication()).component().inject(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        etSum.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        View.OnClickListener menuOnClickListener = v -> {
            switch (v.getId()) {
                case R.id.action_add:
                    if (systemUtils.isConnected()) {
                        if (isValid()) {
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(this, validationErrorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    }
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

    private boolean isValid() {
        if (getSum().isEmpty()) {
            validationErrorMessage = "Вкажіть суму операції";
            return false;
        }

        return true;
    }

    private String getSum() {
        return etSum.getText().toString();
    }

    private String getDescription() {
        return etDescription.getText().toString();
    }
}