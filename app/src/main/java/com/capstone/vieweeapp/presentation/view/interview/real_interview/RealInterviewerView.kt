package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import timber.log.Timber
import java.io.File


@Preview
@Composable
fun RealInterviewerViewPreview() {
    RealInterviewerView(
        modifier = Modifier.fillMaxSize(),
        filePath = "${Environment.getExternalStorageDirectory().absolutePath}/VieweeMedia/talking_1.mp4"
    )
}


@Composable
fun RealInterviewerView(
    modifier: Modifier = Modifier,
    filePath: String
) {
    val TAG = "VideoView"

    val mContext = LocalContext.current
//    val videoFilePath = "/storage/emulated/0/Download/talking.mp4" // 동영상 파일 경로
//    val videoFilePath = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4" // 동영상 파일 경로
    val videoFile = File(filePath)
    val videoUri = Uri.parse("file://$filePath") // 파일 경로를 Uri로 변환
    val videoUriFromFile = Uri.fromFile(videoFile)
    val mediaItem = MediaItem.fromUri(videoUriFromFile)

    LaunchedEffect(key1 = true) {
        Timber.tag(TAG).d(filePath)
        Timber.tag(TAG).d(videoUri.path)
        Timber.tag(TAG).d(File(filePath).exists().toString())
    }

    // 재생관련 설정
    val mExoPlayer = remember(mContext) {
        ExoPlayer.Builder(mContext).build().apply {
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
            volume = 0f
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent, RoundedCornerShape(15.dp))
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = mExoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM //화면을 완전히 채움,가로세로비율 유지x
                }.also { it.clipToOutline = true }
            },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)
        )
    }
}