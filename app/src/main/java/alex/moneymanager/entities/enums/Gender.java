package alex.moneymanager.entities.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({Gender.MALE, Gender.FEMALE})
public @interface Gender {
    String MALE = "male";
    String FEMALE = "female";
}