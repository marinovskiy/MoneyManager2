package alex.moneymanager.entities.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({OrganizationType.FAMILY, OrganizationType.COMPANY, OrganizationType.ORGANIZATION})
public @interface OrganizationType {
    String FAMILY = "family";
    String COMPANY = "company";
    String ORGANIZATION = "organization";
}