package alex.moneymanager.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import alex.moneymanager.R;

public class ErrorDialogFragment extends DialogFragment {

    public static final String TAG = ErrorDialogFragment.class.getSimpleName();

    public static final int BUTTON_POSITIVE_CODE = -1;
    public static final int BUTTON_NEGATIVE_CODE = -2;

    private static final String BUNDLE_KEY_MESSAGE = "bundle_key_message";
    private static final String BUNDLE_KEY_POSITIVE_BUTTON = "bundle_key_positive_button";
    private static final String BUNDLE_KEY_NEGATIVE_BUTTON = "bundle_key_negative_button";

    private static OnClickListener listener;

    private String message;
    private String positiveBtnText;
    private String negativeBtnText;

    public static ErrorDialogFragment newInstance(String msg, String posBtnText, String negBtnText,
                                                  OnClickListener onClickListener) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_MESSAGE, msg);
        args.putString(BUNDLE_KEY_POSITIVE_BUTTON, posBtnText);
        args.putString(BUNDLE_KEY_NEGATIVE_BUTTON, negBtnText);
        fragment.setArguments(args);

        listener = onClickListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(BUNDLE_KEY_MESSAGE);
            positiveBtnText = getArguments().getString(BUNDLE_KEY_POSITIVE_BUTTON);
            negativeBtnText = getArguments().getString(BUNDLE_KEY_NEGATIVE_BUTTON);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            switch (which) {
                case BUTTON_NEGATIVE_CODE:
                    listener.onNegativeButtonClick();
                    break;
                case BUTTON_POSITIVE_CODE:
                    listener.onPositiveButtonClick();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setNegativeButton(negativeBtnText, onClickListener)
                .setPositiveButton(positiveBtnText, onClickListener)
                .setCancelable(false);
        return builder.create();
    }

    public interface OnClickListener /*extends Parcelable*/ {

        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }
}