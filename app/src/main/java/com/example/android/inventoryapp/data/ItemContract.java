package com.example.android.inventoryapp.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * API Contract for the Items app.
 */
public final class ItemContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.items";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.items/items/ is a valid path for
     * looking at item data. content://com.example.android.items/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_ITEMS = "items";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */
    public static final class ItemEntry implements BaseColumns {

        /**
         * The content URI to access the item data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * Name of database table for items
         */
        public final static String TABLE_NAME = "items";

        /**
         * Unique ID number for the item (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_NAME = "product";

        /**
         * email of item's suppliers.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_EMAIL = "email";

        /**
         * Photo of the item.
         * <p>
         * The only possible values are {@link #PHOTO00}, {@link #PHOTO01},
         * or {@link #PHOTO02}.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_PHOTO = "photo";

        /**
         * Photo of the items.
         * <p>
         * Type: INTEGER
         */

        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        /**
         * Price of the item.
         * <p>
         * Type: REAL
         */
        public final static String COLUMN_ITEM_PRICE = "price";

        /**
         * Possible values for the photo of the item.
         */
        public static final int PHOTO00 = 0;
        public static final int PHOTO01 = 1;
        public static final int PHOTO02 = 2;
        public static final int PHOTO03 = 3;
        public static final int PHOTO04 = 4;
        public static final int PHOTO05 = 5;
        public static final int PHOTO06 = 6;
        public static final int PHOTO07 = 7;
        public static final int PHOTO08 = 8;
        public static final int PHOTO09 = 9;
        public static final int PHOTO10 = 10;
        public static final int PHOTO11 = 11;
        public static final int PHOTO12 = 12;
        public static final int PHOTO13 = 13;
        public static final int PHOTO14 = 14;
        public static final int PHOTO15 = 15;
        public static final int PHOTO16 = 16;
        public static final int PHOTO17 = 17;
        public static final int PHOTO18 = 18;
        public static final int PHOTO19 = 19;
        public static final int PHOTO20 = 20;

        /**
         * Returns whether or not the given photo is {@link #PHOTO00}, {@link #PHOTO01},
         * or {@link #PHOTO02}.
         */
        public static boolean isValidPhoto(int photo) {
            if (photo == PHOTO00 || photo == PHOTO01 || photo == PHOTO02 || photo == PHOTO03 || photo == PHOTO04 || photo == PHOTO05 || photo == PHOTO06 || photo == PHOTO07 || photo == PHOTO08 || photo == PHOTO09 || photo == PHOTO10 || photo == PHOTO11 || photo == PHOTO12 || photo == PHOTO13 || photo == PHOTO14 || photo == PHOTO15 || photo == PHOTO16 || photo == PHOTO17 || photo == PHOTO18 || photo == PHOTO19 || photo == PHOTO20) {
                return true;
            }
            return false;
        }
    }
}

