package com.poditivity.common.views

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

class RemoteContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var imageView: ImageView? = null
    private var videoView: VideoView? = null
    lateinit var remoteLayoutParams: LayoutParams

    // Function to load remote content based on type
    fun loadContent(url: String, contentType: ContentType) {
        when (contentType) {
            ContentType.IMAGE -> loadImageView(url)
            ContentType.VIDEO -> loadVideoView(url)
        }
    }

    // Function to load remote image
    private fun loadImageView(url: String) {
        imageView?.let { removeView(it) }

        val newImageView = ImageView(context)
        newImageView.layoutParams = layoutParams
        newImageView.scaleType = ImageView.ScaleType.FIT_XY
        addView(newImageView)

        loadImageFunction(url, newImageView)

        imageView = newImageView
    }

    // Function to load remote video
    private fun loadVideoView(url: String) {
        videoView?.let { removeView(it) }

        val newVideoView = VideoView(context)
        newVideoView.layoutParams = layoutParams
        addView(newVideoView)

        loadVideoFunction(url, newVideoView)

        videoView = newVideoView
    }

    // Replace this function with your actual implementation to load remote images
    private fun loadImageFunction(url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    // Replace this function with your actual implementation to load remote videos
    private fun loadVideoFunction(url: String, videoView: VideoView) {
        // Example: Using VideoView
        videoView.setVideoURI(Uri.parse(url))
        videoView.setOnErrorListener(MediaPlayer.OnErrorListener { _, _, _ ->
            // Handle the error here. For example, you could display a toast or log the error.
            Toast.makeText(context, "Error playing video", Toast.LENGTH_SHORT).show()

            // Return true if the error has been handled, false if not.
            true
        })

        videoView.start()
    }
}

// Enum to represent content types
enum class ContentType {
    IMAGE,
    VIDEO
}
