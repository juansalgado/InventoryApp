package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemContract.ItemEntry;

import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO00;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO01;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO02;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO03;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO04;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO05;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO06;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO07;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO08;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO09;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO10;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO11;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO12;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO13;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO14;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO15;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO16;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO17;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO18;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO19;
import static com.example.android.inventoryapp.data.ItemContract.ItemEntry.PHOTO20;
import static java.lang.Integer.parseInt;

/**
 * Allows user to create a new item or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements

        LoaderManager.LoaderCallbacks<Cursor> {

    final static int[] photoId = {R.drawable.photo00, R.drawable.photo01, R.drawable.photo02, R.drawable.photo03, R.drawable.photo04, R.drawable.photo05, R.drawable.photo06, R.drawable.photo07, R.drawable.photo08, R.drawable.photo09, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12, R.drawable.photo13, R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17, R.drawable.photo18, R.drawable.photo19, R.drawable.photo20};
    final static int[] stringId = {R.string.photo00, R.string.photo01, R.string.photo02, R.string.photo03, R.string.photo04, R.string.photo05, R.string.photo06, R.string.photo07, R.string.photo08, R.string.photo09, R.string.photo10, R.string.photo11, R.string.photo12, R.string.photo13, R.string.photo14, R.string.photo15, R.string.photo16, R.string.photo17, R.string.photo18, R.string.photo19, R.string.photo20};

    private ImageView imageView;

    /**
     * Identifier for the item data loader
     */
    private static final int EXISTING_ITEM_LOADER = 0;

    /**
     * Content URI for the existing item (null if it's a new item)
     */
    private Uri mCurrentItemUri;

    /**
     * EditText field to enter the item's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the item's email
     */
    private EditText mEmailEditText;

    /**
     * EditText field to enter the item's price
     */
    private EditText mPriceEditText;

    /**
     * TextView show the item's quantity
     */
    private TextView mQuantityEditText;

    /**
     * Button to add items
     */
    private Button myPlusButton;

    /**
     * Button to subtrack items
     */
    private Button myMinusButton;
    private Button sendEmail;
    /**
     * EditText field to enter the item's photo
     */
    private Spinner mPhotoSpinner;

    /**
     * Photo of the item. The possible valid values are in the ItemContract.java file:
     * {@link ItemEntry#PHOTO00}, {@link ItemEntry#PHOTO01}, or
     * {@link ItemEntry#PHOTO02}.
     */
    private int mPhoto = PHOTO00;
    private int mQuantity;

    /**
     * Boolean flag that keeps track of whether the item has been edited (true) or not (false)
     */
    private boolean mItemHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mItemHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new item or editing an existing one.
        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();

        // If the intent DOES NOT contain a item content URI, then we know that we are
        // creating a new item.
        if (mCurrentItemUri == null) {
            // This is a new item, so change the app bar to say "Add a Item"
            setTitle(getString(R.string.editor_activity_title_new_item));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a item that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing item, so change app bar to say "Edit Item"
            setTitle(getString(R.string.editor_activity_title_edit_item));

            // Initialize a loader to read the item data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_item_name);
        mEmailEditText = (EditText) findViewById(R.id.edit_item_email);
        mPriceEditText = (EditText) findViewById(R.id.edit_item_price);
        mQuantityEditText = (TextView) findViewById(R.id.edit_quantity_text_view);
        mPhotoSpinner = (Spinner) findViewById(R.id.spinner_photo);
        myPlusButton = (Button) findViewById(R.id.button_plus);
        myMinusButton = (Button) findViewById(R.id.button_minus);
        sendEmail = (Button) findViewById(R.id.button_send);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mNameEditText.setOnTouchListener(mTouchListener);
        mEmailEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mPhotoSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the photo of the item.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter photoSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.photo_spinner, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        photoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mPhotoSpinner.setAdapter(photoSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mPhotoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    for (int i = 0; i < stringId.length; i++) {
                        if (selection.equals(getString(stringId[i]))) {

                            imageView = (ImageView) findViewById(R.id.imageView1);
                            imageView.setImageResource(photoId[i]);
                            mPhoto = i;
                        }

                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPhoto = PHOTO00;
            }
        });
    }

    /**
     * Get user input from editor and save item into database.
     */
    private void saveItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String emailString = mEmailEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        // Check if this is supposed to be a new item
        // and check if all the fields in the editor are blank
        if (mCurrentItemUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(emailString) &&
                TextUtils.isEmpty(priceString) && TextUtils.isEmpty(quantityString)
                && mPhoto == PHOTO00) {
            // Since no fields were modified, we can return early without creating a new item.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and item attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, nameString);
        values.put(ItemEntry.COLUMN_EMAIL, emailString);
        values.put(ItemEntry.COLUMN_ITEM_PHOTO, mPhoto);

        float price = 0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Float.parseFloat(priceString);
        }
        values.put(ItemEntry.COLUMN_ITEM_PRICE, price);

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = parseInt(quantityString);
        }
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity);

        // Determine if this is a new or existing item by checking if mCurrentItemUri is null or not
        if (mCurrentItemUri == null) {
            // This is a NEW item, so insert a new item into the provider,
            // returning the content URI for the new item.
            Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING item, so update the item with content URI: mCurrentItemUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentItemUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentItemUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new item, hide the "Delete" menu item.
        if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                saveItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    public void plusButtonClicked(View view) {
        mQuantity++;
        displayQuantity();
    }

    public void minusButtonClicked(View view) {
        if (mQuantity == 0) {
            Toast.makeText(this, "Can't decrease quantity", Toast.LENGTH_SHORT).show();
        } else {
            mQuantity--;
            displayQuantity();
        }
    }

    public void displayQuantity() {
        mQuantityEditText.setText(String.valueOf(mQuantity));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all item attributes, define a projection that contains
        // all columns from the item table
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_EMAIL,
                ItemEntry.COLUMN_ITEM_PHOTO,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentItemUri,         // Query the content URI for the current item
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of item attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
            int emailColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_EMAIL);
            int photoColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PHOTO);
            int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            int photo = cursor.getInt(photoColumnIndex);
            float price = cursor.getFloat(priceColumnIndex);
            mQuantity = cursor.getInt(quantityColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mEmailEditText.setText(email);
            mPriceEditText.setText(Float.toString(price));
            mQuantityEditText.setText(Integer.toString(mQuantity));

            // Photo is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (photo) {
                case PHOTO01:
                    mPhotoSpinner.setSelection(1);
                    break;
                case PHOTO02:
                    mPhotoSpinner.setSelection(2);
                    break;
                case PHOTO03:
                    mPhotoSpinner.setSelection(3);
                    break;
                case PHOTO04:
                    mPhotoSpinner.setSelection(4);
                    break;
                case PHOTO05:
                    mPhotoSpinner.setSelection(5);
                    break;
                case PHOTO06:
                    mPhotoSpinner.setSelection(6);
                    break;
                case PHOTO07:
                    mPhotoSpinner.setSelection(7);
                    break;
                case PHOTO08:
                    mPhotoSpinner.setSelection(8);
                    break;
                case PHOTO09:
                    mPhotoSpinner.setSelection(9);
                    break;
                case PHOTO10:
                    mPhotoSpinner.setSelection(10);
                    break;
                case PHOTO11:
                    mPhotoSpinner.setSelection(11);
                    break;
                case PHOTO12:
                    mPhotoSpinner.setSelection(12);
                    break;
                case PHOTO13:
                    mPhotoSpinner.setSelection(13);
                    break;
                case PHOTO14:
                    mPhotoSpinner.setSelection(14);
                    break;
                case PHOTO15:
                    mPhotoSpinner.setSelection(15);
                    break;
                case PHOTO16:
                    mPhotoSpinner.setSelection(16);
                    break;
                case PHOTO17:
                    mPhotoSpinner.setSelection(17);
                    break;
                case PHOTO18:
                    mPhotoSpinner.setSelection(18);
                    break;
                case PHOTO19:
                    mPhotoSpinner.setSelection(19);
                    break;
                case PHOTO20:
                    mPhotoSpinner.setSelection(20);
                    break;


                default:
                    mPhotoSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mEmailEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mPhotoSpinner.setSelection(0); // Select "None" photo
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Prompt the user to confirm that they want to delete this item.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the item in the database.
     */
    private void deleteItem() {
        // Only perform the delete if this is an existing item.
        if (mCurrentItemUri != null) {
            // Call the ContentResolver to delete the item at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentItemUri
            // content URI already identifies the item that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    public void sendToEmail(View view) {

        Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + mEmailEditText.getText().toString().trim()));
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "For the Store");
        String message = "I need supplies of " + mNameEditText.getText().toString().trim();
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(intent);

    }
}