package lt.app.news.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class ArticleDetails(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: Date?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readSerializable() as Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeSerializable(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleDetails> {
        override fun createFromParcel(parcel: Parcel): ArticleDetails {
            return ArticleDetails(parcel)
        }

        override fun newArray(p0: Int): Array<ArticleDetails?> {
            return arrayOfNulls(p0)
        }
    }
}