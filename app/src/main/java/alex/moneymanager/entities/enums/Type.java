package alex.moneymanager.entities.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({Type.EXPENSE, Type.INCOME})
public @interface Type {
    String EXPENSE = "expense";
    String INCOME = "income";
}