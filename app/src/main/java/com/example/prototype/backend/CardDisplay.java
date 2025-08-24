package com.example.prototype.backend;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.prototype.DataManager;
import com.example.prototype.R;

/**
 * CardDisplay is a custom CardView component that displays language learning cards
 * with English and Russian words along with an associated image.
 */
public class CardDisplay extends CardView {
    
    /** TextView displaying the Russian word */
    private TextView rus_word;
    
    /** TextView displaying the English word */
    private TextView eng_word;
    
    /** ImageView displaying the associated image */
    private ImageView image;
    
    /** Current data item being displayed */
    private DataManager.Item currentItem;

    /**
     * Constructor for programmatic creation
     * @param context The context
     */
    public CardDisplay(@NonNull Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor for XML inflation
     * @param context The context
     * @param attrs The attribute set from XML
     */
    public CardDisplay(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Constructor for XML inflation with style
     * @param context The context
     * @param attrs The attribute set from XML
     * @param defStyleAttr The default style attribute
     */
    public CardDisplay(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initializes the CardDisplay by inflating the layout and finding views
     * @param context The context used for inflation
     */
    private void init(Context context) {
        try {
            // Inflate the card layout
            LayoutInflater.from(context).inflate(R.layout.cardview, this, true);

            // Find and store references to the views
            rus_word = findViewById(R.id.russianWordTxt);
            eng_word = findViewById(R.id.englishWordTxt);
            image = findViewById(R.id.imageView);
            
            // Validate that all views were found
            if (rus_word == null || eng_word == null || image == null) {
                throw new IllegalStateException("Failed to find required views in cardview layout");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize CardDisplay", e);
        }
    }

    /**
     * Updates the card display with data from DataManager
     * @param context The context used to access DataManager
     * @param englishWord The English word to display and search for
     */
    public void update(Context context, String englishWord) {
        if (context == null || englishWord == null || englishWord.trim().isEmpty()) {
            return;
        }
        
        try {
            DataManager dataManager = DataManager.getInstance(context);
            DataManager.Item item = dataManager.getItemByName(englishWord);
            
            if (item != null) {
                updateWithItem(item);
            } else {
                // Handle case where item is not found
                setDefaultContent(englishWord);
            }
        } catch (Exception e) {
            // Fallback to showing the provided word
            setDefaultContent(englishWord);
        }
    }

    /**
     * Updates the card display with a specific DataManager Item
     * @param item The item containing the word data
     */
    public void updateWithItem(@NonNull DataManager.Item item) {
        this.currentItem = item;
        
        if (eng_word != null) {
            eng_word.setText(item.getEng_word());
        }
        
        if (rus_word != null) {
            rus_word.setText(item.getRus_word());
        }
        
        // Keep the default image for now, could be extended to load specific images
    }

    /**
     * Sets the English word text
     * @param englishWord The English word to display
     */
    public void setEnglishWord(String englishWord) {
        if (eng_word != null && englishWord != null) {
            eng_word.setText(englishWord);
        }
    }

    /**
     * Sets the Russian word text
     * @param russianWord The Russian word to display
     */
    public void setRussianWord(String russianWord) {
        if (rus_word != null && russianWord != null) {
            rus_word.setText(russianWord);
        }
    }

    /**
     * Sets the image from a drawable resource
     * @param drawableResId The drawable resource ID
     */
    public void setImageResource(int drawableResId) {
        if (image != null) {
            try {
                image.setImageResource(drawableResId);
            } catch (Resources.NotFoundException e) {
                // Keep the current image if resource not found
            }
        }
    }

    /**
     * Sets the image from a drawable
     * @param drawable The drawable to set
     */
    public void setImageDrawable(Drawable drawable) {
        if (image != null && drawable != null) {
            image.setImageDrawable(drawable);
        }
    }

    /**
     * Gets the current English word
     * @return The current English word or null if not set
     */
    public String getEnglishWord() {
        return eng_word != null ? eng_word.getText().toString() : null;
    }

    /**
     * Gets the current Russian word
     * @return The current Russian word or null if not set
     */
    public String getRussianWord() {
        return rus_word != null ? rus_word.getText().toString() : null;
    }

    /**
     * Gets the current data item
     * @return The current DataManager.Item or null if not set
     */
    public DataManager.Item getCurrentItem() {
        return currentItem;
    }

    /**
     * Sets default content when no data is found
     * @param englishWord The English word to display
     */
    private void setDefaultContent(String englishWord) {
        setEnglishWord(englishWord);
        setRussianWord("Translation not found");
    }

    /**
     * Clears all text content from the card
     */
    public void clearContent() {
        if (eng_word != null) {
            eng_word.setText("");
        }
        if (rus_word != null) {
            rus_word.setText("");
        }
        currentItem = null;
    }
}
