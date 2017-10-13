package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemContract.ItemEntry;

/**
 * {@link ItemCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of item data as its data source. This adapter knows
 * how to create list items for each row of item data in the {@link Cursor}.
 */
public class ItemCursorAdapter extends CursorAdapter {

    final static int[] photoId = {R.drawable.photo00, R.drawable.photo01, R.drawable.photo02, R.drawable.photo03, R.drawable.photo04, R.drawable.photo05, R.drawable.photo06, R.drawable.photo07, R.drawable.photo08, R.drawable.photo09, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12, R.drawable.photo13, R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17, R.drawable.photo18, R.drawable.photo19, R.drawable.photo20};
    final static int[] stringId = {R.string.photo00, R.string.photo01, R.string.photo02, R.string.photo03, R.string.photo04, R.string.photo05, R.string.photo06, R.string.photo07, R.string.photo08, R.string.photo09, R.string.photo10, R.string.photo11, R.string.photo12, R.string.photo13, R.string.photo14, R.string.photo15, R.string.photo16, R.string.photo17, R.string.photo18, R.string.photo19, R.string.photo20};

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView quantityTextView;
    private TextView itemsTextView;
    private ImageView imageView1;
    private ImageView imageView2;

    private int i;
    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    private MainActivity activity = new MainActivity();

    public ItemCursorAdapter(MainActivity context, Cursor c) {
        super(context, c, 0 /* flags */);
        this.activity = context;
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final long id;
        final int mQuantity;

        // Find individual views that we want to modify in the list item layout
        nameTextView = (TextView) view.findViewById(R.id.name);
        priceTextView = (TextView) view.findViewById(R.id.price);
        quantityTextView = (TextView) view.findViewById(R.id.quantity);
        itemsTextView = (TextView) view.findViewById(R.id.items);
        imageView1 = (ImageView) view.findViewById(R.id.product_image);
        imageView2 = (ImageView) view.findViewById(R.id.sale);

        // Find the columns of item attributes that we're interested in
        //int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        id = cursor.getLong(cursor.getColumnIndex(ItemEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
        int photoColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PHOTO);

        //int emailColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_EMAIL);
        // Read the item attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        int itemQuantity = cursor.getInt(quantityColumnIndex);
        int itemPhoto = cursor.getInt(photoColumnIndex);
        mQuantity = itemQuantity;
        i = itemPhoto;

        // If the item email is empty string or null, then use some default text
        // that says "Unknown email", so the TextView isn't blank.
        // Update the TextViews with the attributes for the current item
        nameTextView.setText(itemName);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(String.valueOf(mQuantity));
        imageView1.setImageResource(photoId[i]);
        itemsTextView.setText(stringId[i]);

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(id);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(id);
            }
        });
        itemsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(id);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantity > 0) {
                    activity.onSaleClick(id, mQuantity);
                } else {
                    Toast.makeText(activity, R.string.quantity_unavailable, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
