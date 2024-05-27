package com.example.foodapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private String id;
    private String name;
    private String preparation;
    private List<Ingredient> ingredients;
    private boolean favorite;
    private String imageUrl;
    private String notes;
    private String creationDate;

    // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    public Recipe() {}

    public Recipe(String id, String name, String preparation, List<Ingredient> ingredients, boolean favorite, String imageUrl, String notes, String creationDate) {
        this.id = id;
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.notes = notes;
        this.creationDate = creationDate;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        preparation = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        favorite = in.readByte() != 0;
        imageUrl = in.readString();
        notes = in.readString();
        creationDate = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreparation() {
        return preparation;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(preparation);
        dest.writeTypedList(ingredients);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeString(imageUrl);
        dest.writeString(notes);
        dest.writeString(creationDate);
    }

    public static class Ingredient implements Parcelable {
        private String name;
        private String quantity;
        private String measurement;

        public Ingredient() {}

        public Ingredient(String name, String quantity, String measurement) {
            this.name = name;
            this.quantity = quantity;
            this.measurement = measurement;
        }

        protected Ingredient(Parcel in) {
            name = in.readString();
            quantity = in.readString();
            measurement = in.readString();
        }

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel in) {
                return new Ingredient(in);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };

        public String getName() {
            return name;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(quantity);
            dest.writeString(measurement);
        }
    }
}
