package com.adspitcher.dialogfragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ErrorDialog extends DialogFragment {

	private String title, errorMessage, positiveButton, negativeButton;

	public ErrorDialog(String title, String errorMessage, String positiveButton,
			String negativeButton) {
		super();
		this.title = title;
		this.errorMessage = errorMessage;
		this.positiveButton = positiveButton;
		this.negativeButton = negativeButton;
	}
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    
 // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);
		builder.setMessage(errorMessage);
		if (positiveButton != null && !positiveButton.equals("")) {
			builder.setPositiveButton(positiveButton,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Send the positive button event back to the host activity
		                       mListener.onDialogPositiveClick(ErrorDialog.this);
						}
					});
		}
		;
		if (positiveButton != null && !positiveButton.equals("")) {
			builder.setNegativeButton(negativeButton,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Send the negative button event back to the host activity
		                       mListener.onDialogNegativeClick(ErrorDialog.this);
						}
					});
		}
		// Create the AlertDialog object and return it
		return builder.create();
	}
}