package com.example.tasteteaser.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "CategoryItems")
public class CategoryItems {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "idcategory")
    @Expose
    @SerializedName("idCategory")
    private String idcategory;

    @ColumnInfo(name = "strcategory")
    @Expose
    @SerializedName("strCategory")
    private String strcategory;

    @ColumnInfo(name = "strcategorythumb")
    @Expose
    @SerializedName("strCategoryThumb")
    private String strcategorythumb;

    @ColumnInfo(name = "strcategorydescription")
    @Expose
    @SerializedName("strCategoryDescription")
    private String strcategorydescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getStrcategory() {
        return strcategory;
    }

    public void setStrcategory(String strcategory) {
        this.strcategory = strcategory;
    }

    public String getStrcategorythumb() {
        return strcategorythumb;
    }

    public void setStrcategorythumb(String strcategorythumb) {
        this.strcategorythumb = strcategorythumb;
    }

    public String getStrcategorydescription() {
        return strcategorydescription;
    }

    public void setStrcategorydescription(String strcategorydescription) {
        this.strcategorydescription = strcategorydescription;
    }
}
